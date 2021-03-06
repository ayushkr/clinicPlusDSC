package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.entities.PharmacyBillRowEntity;
import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.entities.DoctorEntity;
import in.srisrisri.clinic.entities.PatientEntity;
import in.srisrisri.clinic.entities.PharmacyBillEntity;
import in.srisrisri.clinic.responses.JsonResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import in.srisrisri.clinic.utils.PageCover;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/clinicPlus/api/pharmacyBillRow")
public class PharmacyBillRowResource {
    
    String label = "pharmacyBillRow";
    private final Logger logger = LoggerFactory.getLogger(PharmacyBillRowResource.class);
    
    @Autowired
    PharmacyBillRowRepo repo;
    
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<PharmacyBillRowEntity>> getList() {
        logger.debug("getList", new Object() {
        });
        List<PharmacyBillRowEntity> list = repo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<PharmacyBillRowEntity>> getById(@PathVariable("id") Long id) {
        Optional<PharmacyBillRowEntity> item;
        if (id >= 0) {
            item = repo.findById(id);
        } else {
            PharmacyBillRowEntity entityAfter = new PharmacyBillRowEntity();
            entityAfter.setCreationTime(Date.valueOf(LocalDate.now()));
            repo.save(entityAfter);
            item = Optional.of(entityAfter);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
        
    }
    
    @GetMapping("pageable")
    @ResponseBody
    public PageCover<PharmacyBillRowEntity> allPageNumber(
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam(value = "pageSize", required = false) Optional<Integer> pageSizeOb,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int pageSize = 10;
        if (pageSizeOb.isPresent()) {
            pageSize = pageSizeOb.get();
        } else {
            
        }
        
        logger.warn("REST getItems() , {} ", new Object[]{label});
        
        if (!sortColumn.equals("undefined")) {
            if (sortOrder.equals("d")) {
                sort = Sort.by(sortColumn).descending();
            } else {
                sort = Sort.by(sortColumn).ascending();
            }
            
        } else {
            sort = Sort.by("id").ascending();
        }
        if ("undefined".equals(pageNumber)) {
            pageNumber = "1";
        } else {
            if (Integer.parseInt(pageNumber) == 0) {
                pageSize = 10000;
                pageNumber = "1";
            }
        }
        
        Page<PharmacyBillRowEntity> page = null;
        
        Pageable pageable = PageRequest.of(
                Integer.parseInt(pageNumber) - 1, pageSize, sort);
        
        if (filterColumn.equals("undefined")) {
            page = repo.findAll(pageable);
        } else {
            
            if (filterColumn.equals("patient")) {
                PatientEntity patientEntity = new PatientEntity();
                patientEntity.setId(Long.parseLong(filter));
                page = repo.findAllByPatient(patientEntity, pageable);
                
            } else if (filterColumn.equals("doctor")) {
                DoctorEntity doctorEntity = new DoctorEntity();
                doctorEntity.setId(Long.parseLong(filter));
                page = repo.findAllByDoctor(doctorEntity, pageable);
                
            }
            
        }
        
        PageCover<PharmacyBillRowEntity> pageCover = new PageCover<>(page);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setFilter(filter);
        pageCover.setFilterColumn(filterColumn);
        pageCover.setModule(label);
        return pageCover;
    }
    
    @GetMapping("ByBillId/{id}")
    @ResponseBody
    public ResponseEntity<?> ByBillId_id(@PathVariable("id") Long id) {
        
        JsonResponse jsonResponse = new JsonResponse();
        PharmacyBillEntity pharmacyBillEntity = new PharmacyBillEntity();
        pharmacyBillEntity.setId(id);
        
        List<PharmacyBillRowEntity> list = repo.findByPharmacyBill(pharmacyBillEntity);
        SumDAOForPharmacyBill sumDAO = new SumDAOForPharmacyBill(list, repo);
        sumDAO.setBillId(id);
        try {
            sumDAO.calculateTotals();
            jsonResponse.setStatus(Constants1.SUCCESS);
            jsonResponse.setMessage("ok");
            jsonResponse.getMap().put("payload", sumDAO);
            
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("Exception= {} ", e);
            jsonResponse.setStatus(Constants1.FAILURE);
            jsonResponse.setMessage("In sumDAO.calculateTotals(); <br>&nbsp;<span>"
                    + e.toString() + "</span>");
            jsonResponse.getMap().put("payload", sumDAO);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        
    }
    
    @PostMapping("/json")
    public ResponseEntity<JsonResponse> PostMapping_one_json(
            @RequestBody PharmacyBillRowEntity entityBefore
    ) {
        return PostMapping_one(entityBefore);
    }

    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(PharmacyBillRowEntity entityBefore) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();
        long qtyRemaining = 0;
        PharmacyBillRowEntity entityAfter = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            if (entityBefore.getId() == 0) {
                entityAfter = new PharmacyBillRowEntity();
                entityAfter.setPharmacyBill(entityBefore.getPharmacyBill());
//                 entityAfter.setCreationTime(new Date());
            } else if (entityBefore.getQty() == -1) {
                entityAfter = repo.findById(entityBefore.getId()).get();
                repo.deleteById(entityBefore.getId());
                logger.warn("deleteById ={}", entityBefore.getId());
            } else {
                entityAfter = repo.findById(entityBefore.getId()).get();
                BeanUtils.copyProperties(entityBefore, entityAfter);
            }
            
            try {
                entityAfter = repo.save(entityAfter);
                jsonResponse.setMessage("Saved ID:" + entityAfter.getId());
                jsonResponse.setStatus(Constants1.SUCCESS);
            } catch (Exception e) {
                jsonResponse.setMessage(e.toString());
                jsonResponse.setStatus(Constants1.FAILURE);
            }
            
            responseEntity = ResponseEntity
                    .created(new URI("/api/" + label + "/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responseEntity;
    }

// delete
    @GetMapping("delete/id/{id}")
    public JsonResponse DeleteMapping_id(@PathVariable("id") Long id) {
        
        JsonResponse response = new JsonResponse();
        logger.warn("REST request to delete {} {}", new Object[]{label, id});
        try {
            repo.deleteById(id);
            response.setStatus(Constants1.SUCCESS);
            response.setMessage("Deleted " + label + " with id " + id);
            return response;
        } catch (ConstraintViolationException e) {
            logger.warn("DeleteMapping_id={} ,\n Exception={}", new Object[]{id, label});
            response.setStatus(Constants1.FAILURE);
            response.setMessage("This " + label + " is used in other ");
            return response;
            
        } catch (Exception e) {
            logger.warn("DeleteMapping_id={} ,\n Exception={}", new Object[]{id, label});
            response.setStatus(Constants1.FAILURE);
            if (e.getMessage().contains("ConstraintViolationException")) {
                response.setMessage("This " + label + " (ID: " + id
                        + ")  is used in other place <br>For eg: in pharmacyBill etc");
            } else {
                response.setMessage(e.getMessage());
            }
            return response;
        }
    }

    // deleteBulk
    @PostMapping("deleteBulk")
    public ResponseEntity<JsonResponse> deleteBulk(
            @RequestParam("n") List<Long> list) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();
        String failedIds = "";
        try {
            logger.warn("deleteBulk , got={} ", list.toString());
            for (Long n : list) {
                System.out.println(" n=" + n);
                try {
                    repo.deleteById(n);
                } catch (Exception e) {
                    
                    failedIds += "<hr><p>I Cannot delete " + label + " ID:" + n
                            + "<br>Because  "
                            + ((e.getMessage().contains("ConstraintViolationException"))
                            ? "It Used in Other place " : e.getMessage())
                            + "</p><hr>";
                    
                }
                
            }
            jsonResponse.setMessage(failedIds);
            jsonResponse.setStatus(Constants1.FAILURE);
            responseEntity = ResponseEntity
                    .created(new URI("/api/" + label + "/"))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            " "))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return responseEntity;
    }
    
}
