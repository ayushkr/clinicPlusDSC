/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChat;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.responses.JsonResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import in.srisrisri.clinic.utils.PageCover;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akr2
 */
@RestController
@RequestMapping("/clinicPlus/api/smschat")
public class SMSChatResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
   
    private static final String label = "smschat";
   
      @Autowired
    private final SMSChatRepo repo;

    public SMSChatResource(SMSChatRepo repo) {
        this.repo = repo;
    }

   
    
    
    
    @GetMapping("")
    @ResponseBody
    public List<SMSChat> all() {
        logger.warn("REST getItems() , {} ", new Object[]{label});
       
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
       
        List<SMSChat> list = repo.findAll(sort);

        return list;
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<SMSChat> allPageNumber(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int pageSize = 10;
        logger.warn("pageable={},filter({} IN {})", new Object[]{label, filter,filterColumn});

        if (!sortColumn.equals("undefined")) {
            if (sortOrder.equals("d")) {
                sort = Sort.by(sortColumn).descending();
            } else {
                sort = Sort.by(sortColumn).ascending();
            }

        } else {
            sort = Sort.by("id").descending();
        }
        
        if ("undefined".equals(pageNumber)) {
            pageNumber = "1";
        } else {
            if (Integer.parseInt(pageNumber) == 0) {
                pageSize = 10000;
                pageNumber = "1";
            }
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, pageSize, sort);
        Page<SMSChat> page = null;
        if (filterColumn.equals("undefined")) {
            page = repo.findAll(pageable);

        } else {
            if (filterColumn.equals("title")) {
                page = repo.findAllByTitleLike(filter, pageable);

            }
            
             if (filterColumn.equals("body")) {
                page = repo.findAllByBodyLike(filter, pageable);

            }
           

        }

        PageCover<SMSChat> pageCover = new PageCover<>(page);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setFilter(filter);
        pageCover.setFilterColumn(filterColumn);
        pageCover.setModule(label);
        return pageCover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public Optional<SMSChat> id(@PathVariable("id") Long id) {
        logger.warn("id  No {}", new Object[]{id});

        Optional<SMSChat> item;
        if (id >= 0) {
            item = repo.findById(id);
        } else {

            SMSChat entityAfter = new SMSChat();
            entityAfter.setDraft(true);
            entityAfter.setCreationTime(Date.valueOf(LocalDate.now()));
            entityAfter.setDateOfSending(Date.valueOf(LocalDate.now()));
            repo.save(entityAfter);
            item = Optional.of(entityAfter);
        }
        return item;
    }

    
    
    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(SMSChat entityBefore) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            SMSChat entityAfter = null;

            entityAfter = repo.findById(entityBefore.getId()).get();
            entityAfter.setUpdationTime(Date.valueOf(LocalDate.now()));

            BeanUtils.copyProperties(entityBefore, entityAfter);
            try {
                entityAfter = repo.save(entityAfter);
                jsonResponse.setMessage("Saved ID:" + entityAfter.getId());
                jsonResponse.setStatus(Constants1.SUCCESS);
            } catch (Exception e) {
                jsonResponse.setMessage(e.toString());
                jsonResponse.setStatus(Constants1.FAILURE);
            }

            responseEntity = ResponseEntity
                    .created(new URI("/api/smschat/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger("smschat").log(Level.SEVERE, null, ex);
        }
        return responseEntity;
    }

    // delete
    @GetMapping("delete/id/{id}")
    public JsonResponse DeleteMapping_id(@PathVariable("id") Long id) {

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
                        + ")  is used in other place <br>For eg: in pharmacyBill etc");
            } else {
                response.setMessage(e.getMessage());
            }
            return response;
        }
    }
    
//     public void process(){
// 
//        if(to_i==PATIENTS){
//        listOfPhoneNumbers=patientResource.getPhoneNumbers();
//            smsManager.sendSms(phoneNumber, messageBody, true);
//        }
//    }
//    
}
