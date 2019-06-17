package in.srisrisri.clinic.medicineBrandName;

import in.srisrisri.clinic.patient.PatientEntity;
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
@RequestMapping("/clinicPlus/api/medicineBrandName")
public class MedicineBrandNameResource {
String label="medicineBrandName";
    private final Logger logger = LoggerFactory.getLogger(MedicineBrandNameResource.class);

    @Autowired
    MedicineBrandNameRepo repo;


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<MedicineBrandNameEntity>> getMedicineNames(){
       logger.debug("getMedicineNames", new Object (){ });
        List<MedicineBrandNameEntity> list=repo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("pageNumber/{id}")
    @ResponseBody
    public Page<MedicineBrandNameEntity> allPageNumber(@PathVariable("id") int id) {
        logger.warn("REST getItems() , {} ", new Object[]{label});
        Pageable pageable = PageRequest.of(id - 1, 5, Sort.by("id").ascending());
        Page<MedicineBrandNameEntity> list = repo.findAll(pageable);

        return list;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<MedicineBrandNameEntity>> getMedicineNames(@PathVariable("id") Long id){
      Optional<MedicineBrandNameEntity> item=repo.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    
    
       // create
    @PostMapping("")
    public ResponseEntity<MedicineBrandNameEntity> PostMapping_one(MedicineBrandNameEntity entityBefore) {
         ResponseEntity<MedicineBrandNameEntity> body = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            MedicineBrandNameEntity entityAfter = null;
            if(entityBefore.getId()!=0){
            
            entityAfter=repo.findById(entityBefore.getId()).get();
             //entityAfter.setUpdationTime(new Date());
            }else{
            entityAfter=new MedicineBrandNameEntity();
            // entityAfter.setCreationTime(new Date());
            }
           
            BeanUtils.copyProperties(entityBefore, entityAfter);
            entityAfter = repo.save(entityAfter);

           body = ResponseEntity
                    .created(new URI("/api/MedicineBrandNameEntity/" + entityAfter.getId()))
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