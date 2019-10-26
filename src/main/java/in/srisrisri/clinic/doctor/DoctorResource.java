package in.srisrisri.clinic.doctor;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.*;
import in.srisrisri.clinic.FileStorage.FileStorageService;
import in.srisrisri.clinic.FileStorage.UploadFileResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/clinicPlus/api/doctor")
public class DoctorResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private DoctorRepo repo;

    DoctorResource(DoctorRepo doctorRepo, FileStorageService fileStorageService_) {
        this.repo = doctorRepo;
        this.fileStorageService = fileStorageService_;
    }

    private static final String ENTITY_NAME = "Doctor";

    String label = "doctor";

    @GetMapping("port")
    @ResponseBody
    public List<DoctorEntity> port() {
        logger.warn("REST getItems() , {} ", new Object[]{label});

        List<DoctorEntity> list = repo.findAll();
        for (DoctorEntity entity : list) {
            if (entity.getFixedId() != 0) {
                entity.setId(entity.getFixedId());
                repo.save(entity);
            }
        }
        return list;
    }

    @GetMapping("")
    @ResponseBody
    public List<DoctorEntity> all() {
        logger.warn("REST getItems() , {} ", new Object[]{label});

        List<DoctorEntity> list = repo.findAll();

        return list;
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<DoctorEntity> allPageNumber(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int pageSize = 5;
        logger.warn("REST getItems() , {} ", new Object[]{ENTITY_NAME});

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
        pageCover.setModule(label);

        return pageCover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public Optional<DoctorEntity> id(@PathVariable("id") Long id) {
        logger.warn("id {} No {}", new Object[]{label, id});
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

    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(DoctorEntity entityBefore) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            DoctorEntity entityAfter = null;
            

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
                    .created(new URI("/api/"+ENTITY_NAME+"/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME,
                            entityAfter.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responseEntity;
    }


    @PostMapping("profileImage")
    public UploadFileResponse profileImage(@RequestParam("file") MultipartFile file, @RequestParam("fileLabel") String fileLabel) {
        String fileName = fileStorageService.storeFile(file, fileLabel, "doctor");

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/doctor/" + fileLabel + ".jpeg")
                .toUriString();
        logger.warn("fileUploaded to : {} ", new Object[]{fileDownloadUri});
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("fileUpload")
    public UploadFileResponse fileUpload_(@RequestParam("profileImage_file") MultipartFile file, @RequestParam("profileImage") String fileNameStrInForm) {
        String fileNameStrAfterStoring = fileStorageService.storeFile(file, fileNameStrInForm, "");

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/clinicPlus/uploads/" + fileNameStrInForm)
                .toUriString();
        logger.warn("fileUploaded to : {} ", new Object[]{fileDownloadUri});
        return new UploadFileResponse(fileNameStrAfterStoring, fileDownloadUri,
                file.getContentType(), file.getSize());
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
            if(e.getMessage().contains("ConstraintViolationException")){
            response.setMessage("This " + label + " (ID: "+id+
                    ")  is used in other place <br>For eg: in pharmacyBill etc");
            }else{
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
                    
                    failedIds += "<hr><p>I Cannot delete " + label + " ID:" + n
                            + "<br>Because  "
                            +((e.getMessage().contains("ConstraintViolationException")) ? 
                            "It Used in Other place ":e.getMessage()) 
                            + "</p><hr>";

                }

            }
            jsonResponse.setMessage(failedIds);
            jsonResponse.setStatus(Constants1.FAILURE);
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
