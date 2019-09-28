/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.purchaseBill;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.Vendor.VendorEntity;
import in.srisrisri.clinic.Vendor.VendorRepo;
import in.srisrisri.clinic.medicineStock.MedicineStockRepo;

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
@RequestMapping("/clinicPlus/api/purchaseBill")
public class PurchaseBillResource {

    String label = "purchaseBill";
    private final Logger logger = LoggerFactory.getLogger(PurchaseBillResource.class);

    @Autowired
    PurchaseBillRepo purchaseBillRepo;

    @Autowired
    MedicineStockRepo medicineStockRepo;

    @Autowired
    VendorRepo vendorRepo;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<PurchaseBillEntity>> getList() {
        logger.debug("getList", new Object() {
        });
        List<PurchaseBillEntity> list = purchaseBillRepo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<PurchaseBillEntity> allPageNumber(
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

        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, 10, sort);
        Page<PurchaseBillEntity> page = null;

        if ("undefined".equals(filterColumn)) {
            page = purchaseBillRepo.findAll(pageable);
        } else {

            if (filterColumn.equals("vendor")) {
                VendorEntity vendorEntity = new VendorEntity();
                vendorEntity.setId(Long.parseLong(filter));
                page = purchaseBillRepo.findAllByVendor(vendorEntity, pageable);
            }

            if (filterColumn.equals("billNo")) {
                page = purchaseBillRepo.findAllByBillNoLike(filter, pageable);
            }
            
              if (filterColumn.equals("dateOfBill")) {
                  
                page = purchaseBillRepo.findAllByDateOfBill(  Date.valueOf(filter),pageable);
            }

        }

        PageCover<PurchaseBillEntity> pageCover = new PageCover<>(page);

        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setFilter(filter);
        pageCover.setFilterColumn(filterColumn);

        pageCover.setModule(label);

        return pageCover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public Optional<PurchaseBillEntity> getMedicineNames(@PathVariable("id") Long id) {
        PurchaseBillEntity purchaseBillEntitySaved;

        if (id >= 0) {
            purchaseBillEntitySaved = purchaseBillRepo.findById(id).get();
            purchaseBillEntitySaved.setUpdationTime(Date.valueOf(LocalDate.now()));
        } else {
            PurchaseBillEntity purchaseBillEntityNew = new PurchaseBillEntity();

            purchaseBillEntityNew.setCreationTime(Date.valueOf(LocalDate.now()));
            VendorEntity vendorEntity = vendorRepo.findById(0L).get();

            purchaseBillEntityNew.setVendor(vendorEntity);
            purchaseBillEntityNew.setDateOfBill(Date.valueOf(LocalDate.now()));
            purchaseBillEntitySaved = purchaseBillRepo.save(purchaseBillEntityNew);

        }
        return Optional.of(purchaseBillEntitySaved);
    }

    @GetMapping("betweenDates/{dateStart}/{dateEnd}")
    @ResponseBody
    public ResponseEntity<List<PurchaseBillEntity>> getdd(@PathVariable("dateStart") Date dateStart, @PathVariable("dateEnd") Date dateEnd) {

        List<PurchaseBillEntity> item = null;
        System.out.println("dateStart" + dateStart.toString());
        item = purchaseBillRepo.findByDateOfBillBetween(dateStart, dateEnd);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(PurchaseBillEntity entityBefore) {
        ResponseEntity<JsonResponse> body = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            PurchaseBillEntity entityAfter = null;
            if (entityBefore.getId() != 0) {

                entityAfter = purchaseBillRepo.findById(entityBefore.getId()).get();
                //entityAfter.setUpdationTime(new Date());
            } else {
                entityAfter = new PurchaseBillEntity();
                // entityAfter.setCreationTime(new Date());
            }

            BeanUtils.copyProperties(entityBefore, entityAfter);

            try {
                entityAfter = purchaseBillRepo.save(entityAfter);
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
            purchaseBillRepo.deleteById(id);
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
                    purchaseBillRepo.deleteById(n);
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
