package in.srisrisri.clinic.medicineBrandName;

import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import in.srisrisri.clinic.utils.PageCover;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/clinicPlus/api/medicineBrandName")
public class MedicineBrandNameResource {

    String label = "medicineBrandName";
    private final Logger logger = LoggerFactory.getLogger(MedicineBrandNameResource.class);

    @Autowired
    MedicineBrandNameRepo medicineBrandNameRepo;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<MedicineBrandNameEntity>> getMedicineNames() {
        logger.debug("getMedicineNames", new Object() {
        });
        List<MedicineBrandNameEntity> list = medicineBrandNameRepo.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<MedicineBrandNameEntity> allPageNumber(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int pageSize = 30;
        Pageable pageable;
        logger.warn("REST getItems() , {} ", new Object[]{label});

        if (!sortColumn.equals("undefined")) {
            if (sortOrder.equals("d")) {
                sort = Sort.by(sortColumn).descending();
            } else {
                sort = Sort.by(sortColumn).ascending();
            }

        } else {
            sort = Sort.by("brandName").ascending();
        }
        if ("undefined".equals(pageNumber)) {
            pageNumber = "1";
        }

        pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, pageSize, sort);
        Page<MedicineBrandNameEntity> page = null;

        if (filterColumn.equals("undefined")) {
          
            page = medicineBrandNameRepo.findAll(pageable);
        } else {

            if (filterColumn.equals("brandName")) {
               
                page = medicineBrandNameRepo.findAllByBrandName(filter, pageable);

            } else {

            }

        }

        PageCover<MedicineBrandNameEntity> pageCover = new PageCover<>(page);
        pageCover.setSortColumn(sortColumn);
        pageCover.setSortOrder(sortOrder);
        pageCover.setModule(label);
        return pageCover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<MedicineBrandNameEntity>> getMedicineNames(@PathVariable("id") Long id) {
        Optional<MedicineBrandNameEntity> item = medicineBrandNameRepo.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // create
    @PostMapping("")
    public ResponseEntity<MedicineBrandNameEntity> PostMapping_one(MedicineBrandNameEntity entityBefore) {
        ResponseEntity<MedicineBrandNameEntity> body = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("entityBefore---- id ={}", entityBefore.getId());
            MedicineBrandNameEntity entityAfter = null;
            if (entityBefore.getId() != 0) {

                entityAfter = medicineBrandNameRepo.findById(entityBefore.getId()).get();
                //entityAfter.setUpdationTime(new Date());
            } else {
                entityAfter = new MedicineBrandNameEntity();
                // entityAfter.setCreationTime(new Date());
            }

            BeanUtils.copyProperties(entityBefore, entityAfter);
            entityAfter = medicineBrandNameRepo.save(entityAfter);

            body = ResponseEntity
                    .created(new URI("/api/MedicineBrandNameEntity/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(entityAfter);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(label).log(Level.SEVERE, null, ex);
        }
        return body;
    }

    // delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse DeleteMapping_id(@PathVariable("id") Long id) {
        DeleteResponse deleteResponse = new DeleteResponse();
        logger.warn("DeleteMapping_id obj={},id= {}", new Object[]{label, id});
        try {
            medicineBrandNameRepo.deleteById(id);
            deleteResponse.setMessage("Deleted Jote with id " + id);
        } catch (Exception e) {
            deleteResponse.setMessage(e.toString());
            deleteResponse.setStatus("fail");
        }

        return deleteResponse;
    }

}
