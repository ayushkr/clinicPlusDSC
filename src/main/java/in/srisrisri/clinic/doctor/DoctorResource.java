package in.srisrisri.clinic.doctor;

import in.srisrisri.clinic.entities.DoctorEntity;
import in.srisrisri.clinic.titles.Titles;
import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.utils.*;
import in.srisrisri.clinic.FileStorage.FileStorageService;
import in.srisrisri.clinic.responses.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/clinicPlus/api/doctor")
public class DoctorResource extends ResourceAyush{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final DoctorRepo repo;

    @Autowired
    private final FileStorageService fileStorageService;
  

    @Autowired
    DoctorResource(DoctorRepo doctorRepo, FileStorageService fileStorageService_) {
        this.repo = doctorRepo;
        this.fileStorageService = fileStorageService_;
        module = "doctor";
    }


    @GetMapping("{id}")
    @ResponseBody
    public Optional<DoctorEntity> getDoctorById(@PathVariable("id") Long id) {
        logger.warn("getDoctorById ={} No= {}", new Object[]{module, id});
        Optional<DoctorEntity> item;
        if (id >= 0) {
            item = repo.findById(id);
        } else {

            DoctorEntity entityAfter = new DoctorEntity();
            entityAfter.setCreationTime(Date.valueOf(LocalDate.now()));
            entityAfter.setDateOfJoining(Date.valueOf(LocalDate.now()));
            repo.save(entityAfter);
            item = Optional.of(entityAfter);
        }
        return item;
    }

    @PostMapping("/json")
    public ResponseEntity<JsonResponse> PostMapping_one_json(
            @RequestBody DoctorEntity entityBefore
    ) {
        return PostMapping_one(entityBefore);

    }

//@ModelAttribute   @RequestPart
    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(
            DoctorEntity entityBefore
    ) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            DoctorEntity entityAfter = null;

            entityAfter = (DoctorEntity) repo.findById(entityBefore.getId()).get();
            entityAfter.setUpdationTime(Date.valueOf(LocalDate.now()));

            BeanUtils.copyProperties(entityBefore, entityAfter);
            try {
                entityAfter = (DoctorEntity) repo.save(entityAfter);
                jsonResponse.setMessage("Saved ID:" + entityAfter.getId());
                jsonResponse.setStatus(Constants1.SUCCESS);
            } catch (Exception e) {
                jsonResponse.setMessage(e.toString());
                jsonResponse.setStatus(Constants1.FAILURE);
            }

            responseEntity = ResponseEntity
                    .created(new URI("/api/" + module + "/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(module,
                            entityAfter.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            logger.warn(ex.toString());
            
        }
        return responseEntity;
    }

    @GetMapping("")
    @ResponseBody
    public List<DoctorEntity> all() {
        logger.warn("REST getItems() , {} ", new Object[]{module});
        List<DoctorEntity> list = repo.findAll();
        return list;
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<DoctorEntity> pageable_general(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int pageSize = 5;
        logger.warn("REST getItems() , {} ", new Object[]{module});

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
        } else {
            if (Integer.parseInt(pageNumber) == 0) {
                pageSize = 10000;
                pageNumber = "1";
            }
        }

        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, pageSize, sort);
        Page<DoctorEntity> pageList = repo.findAll(pageable);
        PageCover<DoctorEntity> pageCover = new PageCover<>(pageList);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
         pageCover.setFilter(filter);
         pageCover.setFilterColumn(filterColumn);
        pageCover.setModule(module);
        
        
        Titles titles=new Titles();
        titles.setModuleName(module);
           titles.setId2(true);
        titles.setName(true);
         titles.setDepartment(true);
         titles.setFees(true);
         
         
         
        pageCover.setTitles(titles);

        return pageCover;
    }
//     delete

    @GetMapping("delete/id/{id}")
    public JsonResponse DeleteMapping_id(@PathVariable("id") Long id) {

        JsonResponse response = new JsonResponse();
        logger.warn("REST request to delete {} {}", new Object[]{module, id});
        try {
            repo.deleteById(id);
            response.setStatus(Constants1.SUCCESS);
            response.setMessage("Deleted " + module + " with id " + id);
            return response;
        } catch (ConstraintViolationException e) {
            logger.warn("DeleteMapping_id={} ,\n Exception={}", new Object[]{id, module});
            response.setStatus(Constants1.FAILURE);
            response.setMessage("This " + module + " is used in other ");
            return response;

        } catch (Exception e) {
            logger.warn("DeleteMapping_id={} ,\n Exception={}", new Object[]{id, module});
            response.setStatus(Constants1.FAILURE);
            if (e.getMessage().contains("ConstraintViolationException")) {
                response.setMessage("This " + module + " (ID: " + id
                        + ")  is used in other place <br>For eg: in pharmacyBill etc");
            } else {
                response.setMessage(e.getMessage());
            }
            return response;
        }
    }

    // deleteBulk
    @PostMapping("deleteBulk")
    public ResponseEntity<JsonResponse> deleteBulk(
            @RequestParam("n") List<Long> list) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();
        String failedIds = "";
        try {
            logger.warn("deleteBulk , got={} ", list.toString());
            for (Long n : list) {
                System.out.println(" n=" + n);
                try {
                    repo.deleteById(n);
                } catch (Exception e) {

                    failedIds += "<hr><p>I Cannot delete " + module + " ID:" + n
                            + "<br>Because  "
                            + ((e.getMessage().contains("ConstraintViolationException"))
                            ? "It Used in Other place " : e.getMessage())
                            + "</p><hr>";

                }

            }
            jsonResponse.setMessage(failedIds);
            jsonResponse.setStatus(Constants1.FAILURE);
            responseEntity = ResponseEntity
                    .created(new URI("/api/" + module + "/"))
                    .headers(HeaderUtil.createEntityCreationAlert(module,
                            " "))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return responseEntity;
    }
}
