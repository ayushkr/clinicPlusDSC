package in.srisrisri.clinic.appointment;

import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.doctor.DoctorEntity;
import in.srisrisri.clinic.patient.PatientEntity;

import in.srisrisri.clinic.responses.JsonResponse;
import in.srisrisri.clinic.utils.*;
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
import java.util.Calendar;
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
@RequestMapping("/clinicPlus/api/appointment")
public class AppointmentResource {

    int വി = 0;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppointmentRepo repo;

    public AppointmentResource(AppointmentRepo appointmentRepo) {
        this.repo = appointmentRepo;
    }

    private static final String label = "appointment";

    @GetMapping("")
    @ResponseBody
    public List<AppointmentEntity> local_all() {
        return local_allByDateOfAppointmentDesc();
    }

    @GetMapping("dateOfAppointment")
    @ResponseBody
    public List<AppointmentEntity> local_allByDateOfAppointmentDesc() {
        logger.warn("local_allByDateOfAppointmentDesc, {} ", new Object[]{label});
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "dateOfAppointment"));
        List<AppointmentEntity> list = repo.findAll(sort);
        return list;
    }

    @GetMapping("doctor/{id}")
    @ResponseBody
    public ReportIncomeFromDoctorsDTO all_ByDoctor(@PathVariable("id") Long doctorId,
            @RequestParam("dateFrom") String dateFrom,
            @RequestParam("dateTo") String dateTo
    ) {
        logger.warn("REST getItems() , {} ", new Object[]{label});
        Sort sort = Sort.by(Sort.Direction.DESC, "dateOfAppointment");

        Date dateToDate;

        if ("".equals(dateTo)) {
            dateToDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

        } else {
            dateToDate = Date.valueOf(dateTo);
        }

        if ("".equals(dateFrom)) {
            dateFrom = "2018-01-01";
        }
        List<AppointmentEntity> list = null;

        if (doctorId == 0) {
            list = repo.findByAnyDoctorDateBetween(
                    Date.valueOf(dateFrom), dateToDate);
        } else {
            DoctorEntity doctorEntity = new DoctorEntity();
            doctorEntity.setId(doctorId);
            list = repo.findByDoctorDateBetween(
                    Date.valueOf(dateFrom), dateToDate, doctorEntity);
        }

        ReportIncomeFromDoctorsDTO reportIncomeFromDoctorsDTO = new ReportIncomeFromDoctorsDTO(list);
        reportIncomeFromDoctorsDTO.setDoctorId(doctorId);
        reportIncomeFromDoctorsDTO.calculateTotal();

        return reportIncomeFromDoctorsDTO;
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<AppointmentEntity> allPageNumber(
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
            sort = Sort.by("dateOfAppointment").descending();
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

        Page<AppointmentEntity> page = null;

        if (filterColumn.equals("undefined")) {
            page = repo.findAll(pageable);
        } else {

            if (filterColumn.equals("patient")) {
                PatientEntity patientEntity = new PatientEntity();
                patientEntity.setId(Long.parseLong(filter));
                page = repo.findAllByPatient(patientEntity, pageable);

            } else if (filterColumn.equals("doctor")) {
                DoctorEntity doctorEntity = new DoctorEntity();
                doctorEntity.setId(Long.parseLong(filter));
                page = repo.findAllByDoctor(doctorEntity, pageable);

            }

        }

        PageCover<AppointmentEntity> pageCover = new PageCover<>(page);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setFilterColumn(filterColumn);
        pageCover.setFilter(filter);
        pageCover.setModule(label);

        return pageCover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public Optional<AppointmentEntity> id(@PathVariable("id") Long id) {
        logger.warn("id {} No {}", new Object[]{label, id});
        Optional<AppointmentEntity> item;
        if (id >= 0) {
            item = repo.findById(id);
        } else {
            AppointmentEntity entityAfter = new AppointmentEntity();
            entityAfter.setCreationTime(Date.valueOf(LocalDate.now()));
            repo.save(entityAfter);
            item = Optional.of(entityAfter);
        }
        return item;
    }

    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(AppointmentEntity entityBefore) {
        ResponseEntity<JsonResponse> body = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            logger.warn("PostMapping_one , entityBefore={} ", entityBefore.toString());

            AppointmentEntity entityAfter = null;

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
            logger.warn("PostMapping_one, entityAfter ={}", entityAfter);
            body = ResponseEntity
                    .created(new URI("/api/appointment/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(jsonResponse);

        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(AppointmentResource.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return body;
    }

    // create many
    @PostMapping("many")
    public ResponseEntity<String> many(@RequestBody AppointmentWrapper items)
            throws URISyntaxException {
        String bodyMatter = "";
        logger.warn("REST request to add many  {} ", items);
        for (AppointmentEntity item : items.getList()) {
            bodyMatter += "item " + item.getId();
        }

        return ResponseEntity.created(new URI("/api/appointment/many"))
                .headers(HeaderUtil.createEntityCreationAlert(label, "0"))
                .body(bodyMatter);

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

    // deleteBulk
    @PostMapping("deleteBulk")
    public ResponseEntity<JsonResponse> deleteBulk(
            @RequestParam("n") List<Long> list) {
        ResponseEntity<JsonResponse> responseEntity = null;
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatus(Constants1.SUCCESS);
        String failedIds = "";
        try {
            logger.warn("deleteBulk , got={} ", list.toString());
            for (Long n : list) {
                System.out.println(" n=" + n);
                try {
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
