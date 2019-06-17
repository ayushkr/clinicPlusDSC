package in.srisrisri.clinic.pharmacyBill;

import in.srisrisri.clinic.appointment.AppointmentEntity;
import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
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
@RequestMapping("/clinicPlus/api/pharmacyBill")
public class PharmacyBillResource {
String label="PharmacyBill";
    private final Logger logger = LoggerFactory.getLogger(PharmacyBillResource.class);

    @Autowired
    PharmacyBillRepo repo;


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<PharmacyBillEntity>> getList(){
       logger.debug("getList", new Object (){ });
        List<PharmacyBillEntity> list=repo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
//   @GetMapping("undefined")
//    @ResponseBody
//    public ResponseEntity<Optional<PharmacyBillEntity>> getM(){
//    
//      Optional<PharmacyBillEntity> item=Optional.of(new PharmacyBillEntity());
//        return new ResponseEntity<>(item, HttpStatus.OK);
//    }
      @GetMapping("pageNumber/{id}")
    @ResponseBody
    public Page<PharmacyBillEntity> allPageNumber(@PathVariable("id") int id) {    
        logger.warn("REST getItems() , {} ", new Object[]{label});
        Pageable  pageable=PageRequest.of(id-1, 5,Sort.by("id").ascending());
        Page<PharmacyBillEntity> list = repo.findAll(pageable);
        
        return list;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<PharmacyBillEntity>> getMedicineNames(@PathVariable("id") Long id){
      Optional<PharmacyBillEntity> item=repo.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    
    
     @GetMapping("betweenDates/{dateStart}/{dateEnd}")
    @ResponseBody
    public ResponseEntity<List<PharmacyBillEntity>> getdd(@PathVariable("dateStart") Date dateStart,@PathVariable("dateEnd") Date dateEnd){
        
        List<PharmacyBillEntity> item=null;
         System.out.println("dateStart"+dateStart.toString());
  item=repo.findByDateOfBillBetween(dateStart, dateEnd);
   
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    
    
   
    
       // create
    @PostMapping("")
    public ResponseEntity<PharmacyBillEntity> PostMapping_one(PharmacyBillEntity entityBefore) {
         ResponseEntity<PharmacyBillEntity> body = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            PharmacyBillEntity entityAfter = null;
            if(entityBefore.getId()!=0){
            
            entityAfter=repo.findById(entityBefore.getId()).get();
             //entityAfter.setUpdationTime(new Date());
            }else{
            entityAfter=new PharmacyBillEntity();
            // entityAfter.setCreationTime(new Date());
            }
           
            BeanUtils.copyProperties(entityBefore, entityAfter);
            entityAfter = repo.save(entityAfter);

           body = ResponseEntity
                    .created(new URI("/api/"+label + entityAfter.getId()))
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
        deleteResponse.setMessage("Deleted "+label+" with id " + id);
        return deleteResponse;
    }

  
  

   

}