package in.srisrisri.clinic.Vendor;

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
@RequestMapping("/clinicPlus/api/vendor")
public class VendorResource {
String label="vendor";
    private final Logger logger = LoggerFactory.getLogger(VendorResource.class);

    @Autowired
    VendorRepo repo;


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<VendorEntity>> gets(){
       logger.debug("GetMapping all ", new Object (){ });
        List<VendorEntity> list=repo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

  @GetMapping("pageNumber/{id}")
    @ResponseBody
    public Page<VendorEntity> allPageNumber(@PathVariable("id") int id) {    
        logger.warn("REST getItems() , {} ", new Object[]{label});
        Pageable  pageable=PageRequest.of(id-1, 5,Sort.by("id").ascending());
        Page<VendorEntity> list = repo.findAll(pageable);
        
        return list;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<VendorEntity>> gets(@PathVariable("id") Long id){
      Optional<VendorEntity> item=repo.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    
    
       // create
    @PostMapping("")
    public ResponseEntity<VendorEntity> PostMapping_one(VendorEntity entityBefore) {
         ResponseEntity<VendorEntity> body = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            VendorEntity entityAfter = null;
            if(entityBefore.getId()!=0){
            
            entityAfter=repo.findById(entityBefore.getId()).get();
             //entityAfter.setUpdationTime(new Date());
            }else{
            entityAfter=new VendorEntity();
            // entityAfter.setCreationTime(new Date());
            }
           
            BeanUtils.copyProperties(entityBefore, entityAfter);
            entityAfter = repo.save(entityAfter);

           body = ResponseEntity
                    .created(new URI("/api/vendor/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(entityAfter);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger("patient").log(Level.SEVERE, null, ex);
        }
        return body;
    }
   
       // delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
         DeleteResponse deleteResponse = new DeleteResponse();
        logger.warn("DeleteMapping_id obj={},id= {}", new Object[]{label, id});
        try{
       repo.deleteById(id);
       deleteResponse.setMessage("Deleted Jote with id " + id);
        }
        catch(Exception e){
                deleteResponse.setMessage(e.toString()); 
                deleteResponse.setStatus("fail");
                }
       
        
        return deleteResponse;
    }

  
  

   

}