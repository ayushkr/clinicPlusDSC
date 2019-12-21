/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.Helpers;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.ayushLogger.Logger;
import in.srisrisri.clinic.responses.JsonResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import org.hibernate.exception.ConstraintViolationException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author akr2
 */

@Component
public class ResourceHelper {

    private  String label;
    private  Logger logger;
    private  JpaRepository repo;

    public ResourceHelper() {
    }

    public void set(String label, Logger logger, JpaRepository repo) {
        this.label = label;
        this.repo = repo;
        this.logger = logger;
    }
    
    
 
    public JsonResponse deleteById(Long id) {

        JsonResponse response = new JsonResponse();
        logger.warn("REST request to delete {} {}", new Object[]{label, id});
        try {
            repo.deleteById(id);
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
                        + ")  is in hold by other entity");
            } else {
                response.setMessage(e.getMessage());
            }
            return response;
        }
    }
    
    
    

 
    public ResponseEntity<JsonResponse> deleteBulk(
             List<Long> list) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatus(Constants1.SUCCESS);
        String failedIds = "";
        try {
            logger.warn("deleteBulk , got={} ", new Object[]{list.toString()});
            
            for (Long n : list) {
                System.out.println(" n=" + n);
                try {
                    
                     if(n==-1){
                    repo.deleteAll();
                    break;
                    }
                    repo.deleteById(n);
                   
                   
                    failedIds += "<hr><p>Ok deleted " + label + " ID:" + n
                            + "</p><hr>";

                } catch (Exception e) {

                    failedIds += "<hr><p>I Cannot delete " + label + " ID:" + n
                            + "<br>Because  "
                            + ((e.getMessage().contains("ConstraintViolationException"))
                            ? "It Used in Other place " : e.getMessage())
                            + "</p><hr>";
                    jsonResponse.setStatus(Constants1.FAILURE);
                }

            }
            
            
            jsonResponse.setMessage(failedIds);

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
