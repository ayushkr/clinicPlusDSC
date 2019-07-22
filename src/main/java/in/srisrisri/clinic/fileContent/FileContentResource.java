package in.srisrisri.clinic.fileContent;

import in.srisrisri.clinic.appointment.AppointmentEntity;
import in.srisrisri.clinic.doctor.DoctorEntity;
import in.srisrisri.clinic.patient.PatientEntity;
import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.PageCover;
import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;
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
    String label = "fileContent";
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
        logger.warn("REST getItems() , {} ", new Object[]{label});

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
        pageCover.setModule(label);

        return pageCover;
    }

    @PostMapping("")
    public ResponseEntity<FileContent> one(@RequestParam("id") Long id,
            @RequestParam("updateFile") int updateFile,
            @RequestParam("doctor") Long doctorId,
            @RequestParam("patient") Long patientId,
            @RequestParam("appointment") Long appointmentId,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile multipartFile) {
        logger.warn("post one  id={}, \ndescription={},\nfilename={} , updateFile={}",
                new Object[]{id, description, multipartFile.getOriginalFilename(), updateFile});
        FileContent fileContentBefore = null;
        FileContent fileContentAfter = null;
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

        } catch (IOException ex) {

            return ResponseEntity.badRequest().header("error", ex.toString()).body(null);
        }

        // save updated content-related info
        fileContentAfter = fileContentRepo.save(fileContentBefore);
        return ResponseEntity.ok().header("ok", "saved").body(fileContentAfter);
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
    @GetMapping("content/{fileId}")
    public ResponseEntity<?> getContent(@PathVariable("fileId") Long id) {

        Optional<FileContent> fileContent = fileContentRepo.findById(id);
        if (fileContent.isPresent()) {
            InputStreamResource inputStreamResource = new InputStreamResource(fileContentStore.getContent(fileContent.get()));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(fileContent.get().getContentLength());
            headers.set("Content-Type", fileContent.get().getMimeType());
            return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
        }
        return null;
    }

    @GetMapping("{id}")
    @ResponseBody
    public Optional<FileContent> id(@PathVariable("id") Long id) {
        logger.warn("id {} No {}", new Object[]{label, id});
        Optional<FileContent> item = fileContentRepo.findById(id);

        return item;
    }

    // delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
        logger.warn("REST request to delete {} {}", new Object[]{label, id});
        fileContentRepo.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("Deleted FileContent with id " + id);
        return deleteResponse;
    }

}
