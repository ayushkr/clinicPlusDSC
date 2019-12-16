package in.srisrisri.clinic.bill;

import java.util.List;

import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/clinicPlus/api/bill")
public class BillResource {

    
String label="bill";
    private final Logger logger = LoggerFactory.getLogger(BillResource.class);
    @Autowired
    BillRepo repo;

@GetMapping("")
public List<BillEntity>  g(){

        List<BillEntity> findAll = repo.findAll();
        return findAll;
}
  @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<BillEntity>> get(@PathVariable("id") Long id){
      Optional<BillEntity> item=repo.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

@PostMapping("")
public  ResponseEntity<BillEntity> p(BillEntity entityBefore){
 ResponseEntity<BillEntity> body = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            BillEntity entityAfter = null;
            if(entityBefore.getId()!=0){
            
            entityAfter=repo.findById(entityBefore.getId()).get();
             //entityAfter.setUpdationTime(new Date());
            }else{
            entityAfter=new BillEntity();
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