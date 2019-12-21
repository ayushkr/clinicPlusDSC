package in.srisrisri.clinic.pharmacyBill;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.appointment.AppointmentEntity;
import in.srisrisri.clinic.appointment.AppointmentRepo;
import in.srisrisri.clinic.pharmacyBillRow.PharmacyBillRowEntity;
import in.srisrisri.clinic.pharmacyBillRow.PharmacyBillRowRepo;
import in.srisrisri.clinic.responses.JsonResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import in.srisrisri.clinic.utils.PageCover;
import java.math.BigDecimal;
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
@RequestMapping("/clinicPlus/api/pharmacyBill")
public class PharmacyBillResource {

    String label = "pharmacyBill";
    private final Logger logger = LoggerFactory.getLogger(PharmacyBillResource.class);

    @Autowired
    PharmacyBillRepo pharmacyBillRepo;

    @Autowired
    PharmacyBillRowRepo pharmacyBillRowRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<PharmacyBillEntity>> getList() {
        logger.debug("getList", new Object() {
        });
        List<PharmacyBillEntity> list = pharmacyBillRepo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<PharmacyBillEntity> allPageNumber(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam(value = "pageSize", required = false) Optional<Integer> pageSizeOb,
            @RequestParam("filter") String filter,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int pageSize = 10;
        if (pageSizeOb.isPresent()) {
            pageSize = pageSizeOb.get();
        } else {

        }
        logger.warn("pageable , {} ", new Object[]{label});

        if (!sortColumn.equals("undefined")) {
            if (sortOrder.equals("d")) {
                sort = Sort.by(sortColumn).descending();
            } else {
                sort = Sort.by(sortColumn).ascending();
            }

        } else {
            sort = Sort.by("dateOfBill").descending();
        }
        if ("undefined".equals(pageNumber)) {
            pageNumber = "1";
        } else {
            if (Integer.parseInt(pageNumber) == 0) {
                pageSize = 10000;
                pageNumber = "1";
            }
        }

        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, pageSize, sort);
        Page<PharmacyBillEntity> page = null;

        if ("undefined".equals(filterColumn)) {
            page = pharmacyBillRepo.findAll(pageable);
        } else {

            if (filterColumn.equals("appointment")) {
                AppointmentEntity appointmentEntity = new AppointmentEntity();
                appointmentEntity.setId(Long.parseLong(filter));
                page = pharmacyBillRepo.findAllByAppointment(appointmentEntity, pageable);
            }
            if (filterColumn.equals("patientName")) {
                page = pharmacyBillRepo.findAllByPatientNameLike(filter, pageable);
            }

        }

        PageCover<PharmacyBillEntity> pageCover = new PageCover<>(page);

        pageCover.setSortColumn(sortColumn);

        pageCover.setSortOrder(sortOrder);

        pageCover.setModule(label);

        return pageCover;
    }
//ooo2
    @GetMapping("{id}")
    @ResponseBody
    public Optional<PharmacyBillEntity> getMedicineNames(@PathVariable("id") Long id) {
        PharmacyBillEntity pharmacyBillEntitySaved;
        logger.warn("id   = {} ", new Object[]{id});
        
        if (id >= 0) {
            
            logger.warn("id >= 0  ", new Object[]{id});
            pharmacyBillEntitySaved = pharmacyBillRepo.findById(id).get();
            pharmacyBillEntitySaved.setUpdationTime(Date.valueOf(LocalDate.now()));
        } else {
            logger.warn("else of id  0  ", new Object[]{id});
            PharmacyBillEntity pharmacyBillEntityNew = new PharmacyBillEntity();
             
         AppointmentEntity appointmentEntity = appointmentRepo.findById(0L).get();
            pharmacyBillEntityNew.setAppointment(appointmentEntity);
            pharmacyBillEntityNew.setDateOfBill(Date.valueOf(LocalDate.now()));
            pharmacyBillEntityNew.setCreationTime(Date.valueOf(LocalDate.now()));
            pharmacyBillEntitySaved = pharmacyBillRepo.save(pharmacyBillEntityNew);

// add rounder
            PharmacyBillRowEntity pharmacyBillRowEntityNew = new PharmacyBillRowEntity();
            pharmacyBillRowEntityNew.setIdSpecial(1);
            pharmacyBillRowEntityNew.setPharmacyBill(pharmacyBillEntitySaved);
            pharmacyBillRowEntityNew.setCreationTime(Date.valueOf(LocalDate.now()));
            pharmacyBillRowEntityNew.setAmount(BigDecimal.ZERO);
            pharmacyBillRowRepo.save(pharmacyBillRowEntityNew);
        }
        return Optional.of(pharmacyBillEntitySaved);
    }

    @GetMapping("betweenDates/{dateStart}/{dateEnd}")
    @ResponseBody
    public ResponseEntity<List<PharmacyBillEntity>> getdd(@PathVariable("dateStart") Date dateStart, @PathVariable("dateEnd") Date dateEnd) {

        List<PharmacyBillEntity> item = null;
        System.out.println("dateStart" + dateStart.toString());
        item = pharmacyBillRepo.findByDateOfBillBetween(dateStart, dateEnd);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(PharmacyBillEntity entityBefore) {
        ResponseEntity<JsonResponse> body = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            PharmacyBillEntity entityAfter = null;
            if (entityBefore.getId() != 0) {

                entityAfter = pharmacyBillRepo.findById(entityBefore.getId()).get();
                //entityAfter.setUpdationTime(new Date());
            } else {
                entityAfter = new PharmacyBillEntity();
                // entityAfter.setCreationTime(new Date());
            }

            BeanUtils.copyProperties(entityBefore, entityAfter);

            try {
                entityAfter = pharmacyBillRepo.save(entityAfter);
                jsonResponse.setMessage("Saved ID:" + entityAfter.getId());
                jsonResponse.setStatus(Constants1.SUCCESS);
            } catch (Exception e) {
                jsonResponse.setMessage(e.toString());
                jsonResponse.setStatus(Constants1.FAILURE);
            }

            body = ResponseEntity
                    .created(new URI("/api/" + label + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(label).log(Level.SEVERE, null, ex);
        }
        return body;
    }

    // delete
    @GetMapping("delete/id/{id}")
    public JsonResponse DeleteMapping_id(@PathVariable("id") Long id) {

        JsonResponse response = new JsonResponse();
        logger.warn("REST request to delete {} {}", new Object[]{label, id});
        try {
            pharmacyBillRepo.deleteById(id);
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
                    pharmacyBillRepo.deleteById(n);
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
