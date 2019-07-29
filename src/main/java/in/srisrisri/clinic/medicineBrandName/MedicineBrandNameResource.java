package in.srisrisri.clinic.medicineBrandName;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.doctor.DoctorEntity;
import in.srisrisri.clinic.responses.DeleteResponse;
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
@RequestMapping("/clinicPlus/api/medicineBrandName")
public class MedicineBrandNameResource {

    String label = "medicineBrandName";
    private final Logger logger = LoggerFactory.getLogger(MedicineBrandNameResource.class);

    @Autowired
    MedicineBrandNameRepo repo;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<MedicineBrandNameEntity>> getMedicineNames() {
        logger.debug("getMedicineNames", new Object() {
        });
        List<MedicineBrandNameEntity> list = repo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<MedicineBrandNameEntity> allPageNumber(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int pageSize = 30;
        Pageable pageable;
        logger.warn("pageable={} filter={}", new Object[]{label, filterColumn});

        if (!sortColumn.equals("undefined")) {
            if (sortOrder.equals("d")) {
                sort = Sort.by(sortColumn).descending();
            } else {
                sort = Sort.by(sortColumn).ascending();
            }

        } else {
            sort = Sort.by("brandName").ascending();
        }
        if ("undefined".equals(pageNumber)) {
            pageNumber = "1";
        } else {
            if (Integer.parseInt(pageNumber) == 0) {
                pageSize = 10000;
                pageNumber="1";
            }
        }

        pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, pageSize, sort);
        Page<MedicineBrandNameEntity> page = null;

        if (filterColumn.equals("undefined")) {

            page = repo.findAll(pageable);
        } else {

            if (filterColumn.equals("brandName")) {
                page = repo.findAllByBrandNameLike(filter, pageable);
            }
            if (filterColumn.equals("genericName")) {
                page = repo.findAllByGenericNameLike(filter, pageable);
            }
            if (filterColumn.equals("composition")) {
                page = repo.findAllByCompositionLike(filter, pageable);
            }

        }

        PageCover<MedicineBrandNameEntity> pageCover = new PageCover<>(page);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setModule(label);
        return pageCover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<MedicineBrandNameEntity>> getMedicineNames(@PathVariable("id") Long id) {
        
          Optional<MedicineBrandNameEntity> item ;
        if(id>0){
         item = repo.findById(id);}
        else{
        
            MedicineBrandNameEntity entityAfter = new MedicineBrandNameEntity();
            entityAfter.setCreationTime(Date.valueOf(LocalDate.now()));
            repo.save(entityAfter);
            item = Optional.of(entityAfter);
        }
        
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(MedicineBrandNameEntity entityBefore) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse=new JsonResponse();
        
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("entityBefore---- id ={}", entityBefore.getId());
            MedicineBrandNameEntity entityAfter = null;
            if (entityBefore.getId() != 0) {

                entityAfter = repo.findById(entityBefore.getId()).get();
                //entityAfter.setUpdationTime(new Date());
            } else {
                entityAfter = new MedicineBrandNameEntity();
                // entityAfter.setCreationTime(new Date());
            }

            BeanUtils.copyProperties(entityBefore, entityAfter);
            
            try {
                entityAfter = repo.save(entityAfter);
                jsonResponse.setMessage("Saved ID:" + entityAfter.getId());
                jsonResponse.setStatus(Constants1.SUCCESS);
            } catch (Exception e) {
                jsonResponse.setMessage(e.toString());
                jsonResponse.setStatus(Constants1.FAILURE);
            }

            responseEntity = ResponseEntity
                    .created(new URI("/api/MedicineBrandNameEntity/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(label).log(Level.SEVERE, null, ex);
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
            if(e.getMessage().contains("ConstraintViolationException")){
            response.setMessage("This " + label + " (ID: "+id+
                    ")  is used in other place <br>For eg: in pharmacyBill etc");
            }else{
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
                            +((e.getMessage().contains("ConstraintViolationException")) ? 
                            "It Used in Other place ":e.getMessage()) 
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
