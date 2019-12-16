/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.Helpers;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.FileStorage.FileStorageService;
import in.srisrisri.clinic.FileStorage.UploadFileResponse;
import in.srisrisri.clinic.appointment.AppointmentRepo;
import in.srisrisri.clinic.doctor.DoctorEntity;
import in.srisrisri.clinic.doctor.DoctorRepo;
import in.srisrisri.clinic.patient.PatientEntity;
import in.srisrisri.clinic.patient.PatientRepo;
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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author akr2
 */
public class Resource {

    int വി = 0;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public Entity entityAfter = new Entity();

    public JpaRepository repo;
    public FileStorageService fileStorageService;

//    private  final  DoctorRepo doctorRepo;
//     private  final  PatientRepo patientRepo;
//      private  final AppointmentRepo appointmentRepo;
    private final ResourceHelper resourceHelper;

    public  String label = "label";

    public Resource(FileStorageService fileStorageService, ResourceHelper resourceHelper) {
        this.fileStorageService = fileStorageService;
        this.resourceHelper = resourceHelper;
    }

    public Resource() {
        this.resourceHelper = null;
    }

    @GetMapping("")
    @ResponseBody
    public List<Entity> all() {
        List<Entity> list = repo.findAll();
        return list;
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<Entity> pageable_general(
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam(value = "pageSize", required = false) Optional<Integer> pageSizeOb,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int pageSize = 5;
        if (pageSizeOb.isPresent()) {
            pageSize = pageSizeOb.get();
        } else {

        }

        logger.warn("REST getItems() , {} ", new Object[]{label});

        if (!sortColumn.equals("undefined")) {
            if (sortOrder.equals("d")) {
                sort = Sort.by(sortColumn).descending();
            } else {
                sort = Sort.by(sortColumn).ascending();
            }

        } else {
//            sort = Sort.by("dateOfAppointment").descending();
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

        Pageable pageable = PageRequest.of(
                Integer.parseInt(pageNumber) - 1, pageSize, sort);

        Page<Entity> page = null;

        if (filterColumn.equals("undefined")) {
            page = repo.findAll(pageable);
        } else {

            if (filterColumn.equals("patient")) {
                PatientEntity patientEntity = new PatientEntity();
                patientEntity.setId(Long.parseLong(filter));
//                page = appointmentRepo.findAllByPatient(patientEntity, pageable);

            } else if (filterColumn.equals("doctor")) {
                DoctorEntity doctorEntity = new DoctorEntity();
                doctorEntity.setId(Long.parseLong(filter));
//                page = repo.findAllByDoctor(doctorEntity, pageable);

            }

        }

        PageCover<Entity> pageCover = new PageCover<>(page);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setFilterColumn(filterColumn);
        pageCover.setFilter(filter);
        pageCover.setModule(label);

        return pageCover;
    }


    ResponseEntity<JsonResponse> body = null;
    JsonResponse jsonResponse = new JsonResponse();

    // create
//    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one_general(Entity entityBefore) {

        logger.warn("PostMapping_one , entityBefore={} ", entityBefore.toString());

        entityAfter = (Entity) repo.findById(entityBefore.getId()).get();
        entityAfter.setUpdationTime(Date.valueOf(LocalDate.now()));
        BeanUtils.copyProperties(entityBefore, entityAfter);
        try {
            entityAfter = (Entity) repo.save(entityAfter);
            jsonResponse.setMessage("Saved ID:" + entityAfter.getId());
            jsonResponse.setStatus(Constants1.SUCCESS);
        } catch (Exception e) {
            jsonResponse.setMessage(e.toString());
            jsonResponse.setStatus(Constants1.FAILURE);
        }

        return postMappingOneHelperEnd();

    }

    public ResponseEntity<JsonResponse> postMappingOneHelperEnd() {
        logger.warn("PostMapping_one, entityAfter ={}", entityAfter);
        try {
            body = ResponseEntity
                    .created(new URI("/api/" + label + "/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return body;
    }

//
//    // create many
//    @PostMapping("many")
//    public ResponseEntity<String> many(@RequestBody EntityWrapper items)
//            throws URISyntaxException {
//        String bodyMatter = "";
//        logger.warn("REST request to add many  {} ", items);
//        for (Entity item : items.getList()) {
//            bodyMatter += "item " + item.getId();
//        }
//
//        return ResponseEntity.created(new URI("/api/" + label + "/many"))
//                .headers(HeaderUtil.createEntityCreationAlert(label, "0"))
//                .body(bodyMatter);
//
//    }
    
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

    // deleteBulk
    @PostMapping("deleteBulk")
    public ResponseEntity<JsonResponse> deleteBulk(
            @RequestParam("n") List<Long> list) {
        return resourceHelper.deleteBulk(list);
    }

    @GetMapping("port")
    @ResponseBody
    public List<Entity> port() {
        logger.warn("REST getItems() , {} ", new Object[]{label});

        List<Entity> list = repo.findAll();
        for (Entity entity : list) {
            if (entity.getFixedId() != 0) {
                entity.setId(entity.getFixedId());
                repo.save(entity);
            }
        }
        return list;
    }

    @PostMapping("profileImage")
    public UploadFileResponse profileImage(@RequestParam("file") MultipartFile file, @RequestParam("fileLabel") String fileLabel) {
        String fileName = fileStorageService.storeFile(file, fileLabel, label);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/"+label+"/" + fileLabel + ".jpeg")
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

}
