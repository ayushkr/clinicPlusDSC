package in.srisrisri.clinic.fileContent;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.entities.AppointmentEntity;
import in.srisrisri.clinic.entities.DoctorEntity;
import in.srisrisri.clinic.entities.PatientEntity;
import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.responses.JsonResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import in.srisrisri.clinic.utils.PageCover;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/clinicPlus/api/fileContent")
public class FileContentResource {

    @Autowired
    private FileContentRepository fileContentRepo;
    @Autowired
    private FileContentStore fileContentStore;
    String ENTITY_NAME = "fileContent";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<FileContent> allPageNumber(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter
    ) {
        Sort sort;
        int pageSize = 20;
        logger.warn("REST getItems() , {} ", new Object[]{ENTITY_NAME});

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

        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, 10, sort);

        Page<FileContent> page = null;

        if (filterColumn.equals("undefined")) {
            page = fileContentRepo.findAll(pageable);
        } else {

            if (filterColumn.equals("patient")) {

                PatientEntity patientEntity = new PatientEntity();
                patientEntity.setId(Long.parseLong(filter));
                page = fileContentRepo.findAllByPatient(patientEntity, pageable);

            }

            if (filterColumn.equals("doctor")) {
                DoctorEntity doctorEntity = new DoctorEntity();
                doctorEntity.setId(Long.parseLong(filter));
                page = fileContentRepo.findAllByDoctor(doctorEntity, pageable);

            }

        }

        PageCover<FileContent> pageCover = new PageCover<>(page);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setModule(ENTITY_NAME);

        return pageCover;
    }

    public ResponseEntity<JsonResponse> postMapping_oneEntityWise(
            int updateFile,
            FileContent fileContent,
            MultipartFile multipartFile
    ) {
        logger.warn("postMapping_oneEntityWise  ");

        return postMapping_one(
                updateFile,
                fileContent.getId(),
                (fileContent.getDoctor() != null
                ? fileContent.getDoctor().getId() : null),
                (fileContent.getPatient() != null
                ? fileContent.getPatient().getId() : null),
                (fileContent.getAppointment() != null
                ? fileContent.getAppointment().getId() : null),
                fileContent.getDescription(),
                multipartFile);

    }

    @PostMapping("")
    public ResponseEntity<JsonResponse> postMapping_one(
            @RequestParam("updateFile") int updateFile,
            @RequestParam("id") Long id,
            @RequestParam("doctor") Long doctorId,
            @RequestParam("patient") Long patientId,
            @RequestParam("appointment") Long appointmentId,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile multipartFile) {

        logger.warn("postMapping_one id={}", new Object[]{id});
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();

        FileContent fileContentBefore = null;
        FileContent fileContentAfter = null;
        try {
            if (id == 0) {
                fileContentBefore = new FileContent();
                fileContentBefore.setCreationTime(Calendar.getInstance().getTime());
            } else {
                fileContentBefore = fileContentRepo.findById(id).get();
                fileContentBefore.setUpdationTime(Calendar.getInstance().getTime());
            }

            try {
                fileContentBefore.setDescription(description);

                if (updateFile == 1) {
                    fileContentBefore.setMimeType(multipartFile.getContentType());
                    fileContentStore.setContent(fileContentBefore, multipartFile.getInputStream());
                }
                if (doctorId != null) {
                    fileContentBefore.setDoctor(new DoctorEntity().setId(doctorId));
                }
                if (patientId != null) {
                    fileContentBefore.setPatient(new PatientEntity().setId(patientId));
                }
                if (appointmentId != null) {
                    fileContentBefore.setAppointment(new AppointmentEntity().setId(appointmentId));
                }

            } catch (IOException e) {
                jsonResponse.setMessage(e.toString());
                jsonResponse.setStatus(Constants1.FAILURE);

            }

            // save updated content-related info
            fileContentAfter = fileContentRepo.save(fileContentBefore);
            try {
                fileContentAfter = fileContentRepo.save(fileContentBefore);
                jsonResponse.setMessage("Saved ID:" + fileContentAfter.getId());
                jsonResponse.setStatus(Constants1.SUCCESS);
            } catch (Exception e) {
                jsonResponse.setMessage(e.toString());
                jsonResponse.setStatus(Constants1.FAILURE);
            }

            responseEntity = ResponseEntity
                    .created(new URI("/api/" + ENTITY_NAME + "/" + fileContentAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME,
                            fileContentAfter.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
        }

        return responseEntity;
    }

//    @RequestMapping(value = "/files/{fileId}", method = RequestMethod.PUT)
    @PutMapping("{fileId}")
    public ResponseEntity<?> setContent(@PathVariable("fileId") Long id, @RequestParam("file") MultipartFile multipartFile)
            throws IOException {

        Optional<FileContent> fileContent = fileContentRepo.findById(id);
        if (fileContent.isPresent()) {
            fileContent.get().setMimeType(multipartFile.getContentType());

            fileContentStore.setContent(fileContent.get(), multipartFile.getInputStream());

            // save updated content-related info
            fileContentRepo.save(fileContent.get());

            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return null;
    }

//    @RequestMapping(value = "/files/{fileId}", method = RequestMethod.GET)
    @GetMapping("{fileId}/content")
    public ResponseEntity<?> getContent(@PathVariable("fileId") Long id) {

        Optional<FileContent> fileContent = fileContentRepo.findById(id);
        if (fileContent.isPresent()) {
            try {
                InputStreamResource inputStreamResource = new InputStreamResource(fileContentStore.getContent(fileContent.get()));
                HttpHeaders headers = new HttpHeaders();
                headers.setContentLength(fileContent.get().getContentLength());
                headers.set("Content-Type", fileContent.get().getMimeType());
                headers.set("Content-Disposition", fileContent.get().getDescription() + "."
                        + fileContent.get().getMimeType().split("/")[1]);
                return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("No file set yet "+e.toString(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("fileContent is not present", HttpStatus.OK);
        }

    }

    @GetMapping("{id}")
    @ResponseBody
    public Optional<FileContent> id(@PathVariable("id") Long id) {
        logger.warn("id {} No {}", new Object[]{ENTITY_NAME, id});

        Optional<FileContent> item;
        if (id > 0) {
            item = fileContentRepo.findById(id);
        } else {

            FileContent entityAfter = new FileContent();
            entityAfter.setId(0L);
            entityAfter.setCreationTime(Date.valueOf(LocalDate.now()));
            fileContentRepo.save(entityAfter);
            item = Optional.of(entityAfter);

        }

        return item;

    }

    @GetMapping("")
    @ResponseBody
    public List<FileContent> all() {
        logger.warn("id {} ", new Object[]{ENTITY_NAME});
        List<FileContent> findAll = fileContentRepo.findAll();
        return findAll;
    }

    // delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
        logger.warn("REST request to delete {} {}", new Object[]{ENTITY_NAME, id});
        
        FileContent get = fileContentRepo.findById(id).get();
        fileContentStore.unsetContent(get);
        fileContentRepo.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("Deleted FileContent with id " + id);
        return deleteResponse;
    }

}
