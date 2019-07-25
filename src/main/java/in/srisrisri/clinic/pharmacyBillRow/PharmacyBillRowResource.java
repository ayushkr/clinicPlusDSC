package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.Exceptions.BadDataInputException;
import in.srisrisri.clinic.medicineBrandName.MedicineBrandNameEntity;
import in.srisrisri.clinic.medicineStock.MedicineStockEntity;
import in.srisrisri.clinic.medicineStock.MedicineStockRepo;
import in.srisrisri.clinic.pharmacyBill.PharmacyBillEntity;
import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import in.srisrisri.clinic.utils.PageCover;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/clinicPlus/api/pharmacyBillRow")
public class PharmacyBillRowResource {

    String label = "PharmacyBillRow";
    private final Logger logger = LoggerFactory.getLogger(PharmacyBillRowResource.class);

    @Autowired
    PharmacyBillRowRepo pharmacyBillRowRepo;

    @Autowired
    MedicineStockRepo medicineStockRepo;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<PharmacyBillRowEntity>> getList() {
        logger.debug("getList", new Object() {
        });
        List<PharmacyBillRowEntity> list = pharmacyBillRowRepo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//   @GetMapping("undefined")
//    @ResponseBody
//    public ResponseEntity<Optional<PharmacyBillEntity>> getM(){
//    
//      Optional<PharmacyBillEntity> item=Optional.of(new PharmacyBillEntity());
//        return new ResponseEntity<>(item, HttpStatus.OK);
//    }
    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<PharmacyBillRowEntity>> getById(@PathVariable("id") Long id) {
        Optional<PharmacyBillRowEntity> item;
        if (id > 0) {
            item = pharmacyBillRowRepo.findById(id);
        } else {

            item = Optional.of(PostMapping_one(new PharmacyBillRowEntity()).getBody());

        }
        return new ResponseEntity<>(item, HttpStatus.OK);

    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<PharmacyBillRowEntity> allPageNumber(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
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
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, 10, sort);
        Page<PharmacyBillRowEntity> pageList = pharmacyBillRowRepo.findAll(pageable);
        PageCover<PharmacyBillRowEntity> pageCover = new PageCover<>(pageList);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setModule(label);
        return pageCover;
    }

    @GetMapping("ByBillId/{id}")
    @ResponseBody
    public ResponseEntity<?> ByBillId_id(@PathVariable("id") Long id) {

        ResponseEntity<PharmacyBillRowEntity> body = null;
        PharmacyBillEntity pharmacyBillEntity = new PharmacyBillEntity();
        pharmacyBillEntity.setId(id);

        List<PharmacyBillRowEntity> list = pharmacyBillRowRepo.findByPharmacyBill(pharmacyBillEntity);
        SumDAO sumDAO = new SumDAO(list);
        sumDAO.setBillId(id);
        try {
            boolean calculateTotals = sumDAO.calculateTotals();
            return new ResponseEntity<>(sumDAO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.OK);
        }

    }

    // create
    @PostMapping("")
    public ResponseEntity<PharmacyBillRowEntity> PostMapping_one(PharmacyBillRowEntity entityBefore) {
        ResponseEntity<PharmacyBillRowEntity> response = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());

            PharmacyBillRowEntity entityAfter = null;

            Integer qty = entityBefore.getQty();
            long qtyRemaining = 0;
            try {
                if (entityBefore.getMedicineStock() != null) {
                    qtyRemaining = entityBefore.getMedicineStock().getQtyRemaining();
                }
            } catch (Exception e) {
                logger.warn("Exception PostMapping_one obj={} ,  error={}", new Object[]{entityBefore.toString(), e});
            }

            if (entityBefore.getQty() == -1) {

                entityAfter = pharmacyBillRowRepo.findById(entityBefore.getId()).get();
                pharmacyBillRowRepo.deleteById(entityBefore.getId());
                logger.warn("deleteById ={}", entityBefore.getId());

            } else {
                if (entityBefore.getId() == 0) {
                    entityAfter = new PharmacyBillRowEntity();
                    // entityAfter.setCreationTime(new Date());
                } else {
                    entityAfter = pharmacyBillRowRepo.findById(entityBefore.getId()).get();
                    //entityAfter.setUpdationTime(new Date());
                }

                BeanUtils.copyProperties(entityBefore, entityAfter);
                entityAfter = pharmacyBillRowRepo.save(entityAfter);

            }

            response = ResponseEntity
                    .status(HttpStatus.OK)
                    .header("ok", "yes")
                    .header("problem", "")
                    .body(entityAfter);
        } catch (Exception ex) {

            java.util.logging.Logger.getLogger(label).log(Level.SEVERE, null, ex);
            response = ResponseEntity
                    .status(HttpStatus.OK)
                    .header("ok", "no")
                    .header("problem", "" + ex.toString())
                    .body(entityBefore);
        }
        return response;
    }

// delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
        logger.warn("DeleteMapping_id obj={},id= {}", new Object[]{label, id});
        pharmacyBillRowRepo.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("Deleted " + label + " with id " + id);
        return deleteResponse;
    }

}
