/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.jote;

import in.srisrisri.clinic.patient.PatientEntity;
import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ayush
 */

@RestController
@RequestMapping("/clinicPlus/api/jote")
public class JoteResource {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private final JoteRepo joteRepo;

    private static final String ENTITY_NAME = "Jote";
    String label = "Jote";
    
    public JoteResource(JoteRepo joteRepo) {
        this.joteRepo = joteRepo;
    }
    
    @GetMapping("")
    @ResponseBody
    public List<JoteEntity> get_all(){
    logger.warn("REST \t /all \t ",new Object[]{});
    //return joteService.findAll();
    return joteRepo.findByOrderByStatusAscRidAsc();
    }
    
    
      @GetMapping("pageNumber/{id}")
    @ResponseBody
    public Page<JoteEntity> allPageNumber(@PathVariable("id") int id) {
        logger.warn("REST getItems() , {} ", new Object[]{ENTITY_NAME});
        Pageable pageable = PageRequest.of(id - 1, 10, Sort.by("category").ascending().and(Sort.by("status").ascending()));
        Page<JoteEntity> list = joteRepo.findAll(pageable);

        return list;
    }
    
    
    @GetMapping("{id}")
    @ResponseBody
    public Optional<JoteEntity> get_byId(@PathVariable("id") Long id){
    logger.warn("REST \t \"/id/{id}\" {} ",new Object[]{id});
    return joteRepo.findById(id);
    
    }
    
  
    
    @PostMapping("")
    public ResponseEntity<JoteEntity> one(JoteEntity jotePassed){
    ResponseEntity<JoteEntity> responseEntity=null;
    logger.warn("REST-one-{} ",new Object[]{jotePassed.toString()});
    JoteEntity joteCreated=joteRepo.save(jotePassed);
    logger.warn("REST-one-saved -{} ",new Object[]{jotePassed.toString()});
        try {
            responseEntity=ResponseEntity.created(new URI("---"))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME,
                            joteCreated.getId() + ""))
                    .body(joteCreated);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(JoteResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responseEntity;
    }
    
       // delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
        logger.warn("DeleteMapping_id obj={},id= {}", new Object[]{label, id});
        joteRepo.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("Deleted Jote with id " + id);
        return deleteResponse;
    }
}
