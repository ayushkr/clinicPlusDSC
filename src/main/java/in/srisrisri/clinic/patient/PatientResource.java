package in.srisrisri.clinic.patient;

import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.*;
import in.srisrisri.clinic.FileStorage.FileStorageService;
import in.srisrisri.clinic.FileStorage.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

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
@RequestMapping("/clinicPlus/api/patient")
public class PatientResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final FileStorageService fileStorageService;

    @Autowired
    private final PatientRepo repo;

    public PatientResource(FileStorageService fileStorageService, PatientRepo repo) {
        this.fileStorageService = fileStorageService;
        this.repo = repo;
    }

    private static final String label = "patient";

    @PostMapping("prescription")
    public UploadFileResponse fileUploadPrescription(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("category") String category,
            @RequestParam("patientId") Long patientId,
            @RequestParam("name") String name,
            @RequestParam("doctorId") Long doctorId
    ) {

        System.out.println("pat id=" + patientId + " doc Id=" + doctorId + " name=" + name);
        String parentPath = "patient/" + patientId + "/" + category;
        String fileNameSaved = fileStorageService.storeFile(multipartFile, new java.util.Date().getTime() + "", parentPath);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/" + parentPath + "/" + fileNameSaved)
                .toUriString();
        logger.warn("fileUploaded to : {} ", new Object[]{fileDownloadUri});
        return new UploadFileResponse(fileNameSaved, fileDownloadUri,
                multipartFile.getContentType(), multipartFile.getSize());
    }

    @GetMapping("")
    @ResponseBody
    public List<PatientEntity> all() {
        logger.warn("REST getItems() , {} ", new Object[]{label});

        List<PatientEntity> list = repo.findAll();

        return list;
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<PatientEntity> allPageNumber(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        logger.warn("REST getItems() , {} ", new Object[]{label});

        if (!sortColumn.equals("undefined")) {
            if (sortOrder.equals("d")) {
                sort = Sort.by(sortColumn).descending();
            } else {
                sort = Sort.by(sortColumn).ascending();
            }

        } else {
            sort = Sort.by("dateOfRegistration").descending();
        }
        if ("undefined".equals(pageNumber)) {
            pageNumber = "1";
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1,20, sort);
        Page<PatientEntity> page = null;
        if (filterColumn.equals("undefined")) {
            page = repo.findAll(pageable);

        } else {
            if (filterColumn.equals("name")) {
                page = repo.findAllByNameLike(filter, pageable);

            }
        }

        PageCover<PatientEntity> pageCover = new PageCover<>(page);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setFilter(filter);
        pageCover.setFilterColumn(filterColumn);
        pageCover.setModule(label);
        return pageCover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public Optional<PatientEntity> id(@PathVariable("id") Long id) {
        logger.warn("id  No {}", new Object[]{id});
        Optional<PatientEntity> item = repo.findById(id);
        //  item.get().setCreationTime(new Date());
        return item;
    }

    // create
    @PostMapping("search")
    public List<PatientEntity> PostMapping_search(PatientEntity entityBefore) {
        List<PatientEntity> list = null;
        try {
            logger.warn("PostMapping(\"search\") {} ", "");
            list = repo.findAll();
        } catch (Exception e) {

        }
        return list;
    }
    // create

    @PostMapping("/json")
    public String PostMapping_many(@RequestBody PatientList entityBefore) {
        String result = "";
        result += "length=" + entityBefore;
        System.out.println(" " + result);

        return result;
    }

    // create
    @PostMapping("")
    public ResponseEntity<PatientEntity> PostMapping_one(PatientEntity entityBefore) {
        ResponseEntity<PatientEntity> body = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            PatientEntity entityAfter = null;
            if (entityBefore.getId() != 0) {

                entityAfter = repo.findById(entityBefore.getId()).get();
                //entityAfter.setUpdationTime(new Date());
            } else {
                entityAfter = new PatientEntity();
                //entityAfter.setCreationTime(new Date());
            }

            BeanUtils.copyProperties(entityBefore, entityAfter);
            entityAfter = repo.save(entityAfter);

            body = ResponseEntity
                    .created(new URI("/api/patient/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(entityAfter);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger("patient").log(Level.SEVERE, null, ex);
        }
        return body;
    }

    // delete
    @GetMapping("/delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
        logger.warn("REST request to delete {} {}", new Object[]{label, id});
        repo.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("Deleted patient with id " + id);
        return deleteResponse;

    }

}
