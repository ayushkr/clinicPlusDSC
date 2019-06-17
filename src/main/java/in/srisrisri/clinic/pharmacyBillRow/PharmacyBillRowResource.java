package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.pharmacyBill.PharmacyBillEntity;
import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
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
    PharmacyBillRowRepo repo;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<PharmacyBillRowEntity>> getList() {
        logger.debug("getList", new Object() {
        });
        List<PharmacyBillRowEntity> list = repo.findAll();
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
        Optional<PharmacyBillRowEntity> item = repo.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("pageNumber/{id}")
    @ResponseBody
    public Page<PharmacyBillRowEntity> pageNumberById(@PathVariable("id") int id) {
        logger.warn("REST getItems() , {} ", new Object[]{label});
        Pageable pageable = PageRequest.of(id - 1, 5, Sort.by("id").ascending());
        Page<PharmacyBillRowEntity> list = repo.findAll(pageable);

        return list;
    }

    @GetMapping("ByBillId/{id}")
    @ResponseBody
    public ResponseEntity<SumDAO> ByBillId_id(@PathVariable("id") Long id) {

        ResponseEntity<PharmacyBillRowEntity> body = null;
        PharmacyBillEntity pharmacyBillEntity = new PharmacyBillEntity();
        pharmacyBillEntity.setId(id);

        List<PharmacyBillRowEntity> list = repo.findByPharmacyBill(pharmacyBillEntity);
        SumDAO sumDAO = new SumDAO(list);
      //  return new ResponseEntity<>(list, HttpStatus.OK);
         return new ResponseEntity<>(sumDAO, HttpStatus.OK);

    }

    // create
    @PostMapping("")
    public ResponseEntity<PharmacyBillRowEntity> PostMapping_one(PharmacyBillRowEntity entityBefore) {
        ResponseEntity<PharmacyBillRowEntity> body = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());

            PharmacyBillRowEntity entityAfter = null;

            if (entityBefore.getQty() != -1) {
                if (entityBefore.getId() != 0) {
                    entityAfter = repo.findById(entityBefore.getId()).get();
                    //entityAfter.setUpdationTime(new Date());
                } else {
                    entityAfter = new PharmacyBillRowEntity();
                    // entityAfter.setCreationTime(new Date());
                }

                BeanUtils.copyProperties(entityBefore, entityAfter);
                entityAfter = repo.save(entityAfter);
            }else{
            repo.deleteById(entityBefore.getId());
             logger.warn("deleteById ={}", entityBefore.getId());
            entityAfter=entityBefore;
            }

            body = ResponseEntity
                    .created(new URI("/api/" + label + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(entityAfter);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(label).log(Level.SEVERE, null, ex);
        }
        return body;
    }

    // delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
        logger.warn("DeleteMapping_id obj={},id= {}", new Object[]{label, id});
        repo.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("Deleted " + label + " with id " + id);
        return deleteResponse;
    }

}
