package in.srisrisri.clinic.help;

import in.srisrisri.clinic.medicineStock.*;
import in.srisrisri.clinic.medicineBrandName.*;
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


@RestController
@RequestMapping("/clinicPlus/api/help")
public class HelpResource {
String label="help";
    private final Logger logger = LoggerFactory.getLogger(HelpResource.class);


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<MedicineStockEntity>> get(){
       
        return null;
    }



    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<MedicineStockEntity>> get2(@PathVariable("id") Long id){
      
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    
       // create
    @PostMapping("")
    public ResponseEntity<MedicineStockEntity> PostMapping_one(MedicineStockEntity entityBefore) {
         ResponseEntity<MedicineStockEntity> body = null;
        
        return body;
    }
   
       // delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
        
        return null;
    }

  
  

   

}