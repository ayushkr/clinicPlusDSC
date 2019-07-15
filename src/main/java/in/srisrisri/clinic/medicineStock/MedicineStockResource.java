package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.Vendor.VendorEntity;
import in.srisrisri.clinic.Vendor.VendorRepo;
import in.srisrisri.clinic.medicineBrandName.*;
import in.srisrisri.clinic.responses.DeleteResponse;
import in.srisrisri.clinic.utils.HeaderUtil;
import in.srisrisri.clinic.utils.PageCover;
import in.srisrisri.clinic.utils.PageExtendedByAyush;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/clinicPlus/api/medicineStock")
public class MedicineStockResource {

    String label = "medicineStock";
    private final Logger logger = LoggerFactory.getLogger(MedicineStockResource.class);

    @Autowired
    MedicineStockRepo medicineStockRepo;
    @Autowired
    MedicineBrandNameRepo medicineBrandNameRepo;
    @Autowired
    VendorRepo vendorRepo;
    @Autowired
    MedicineBrandNameResource medicineBrandNameResource;
    @Autowired
    MedicineStockResource medicineStockResource;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<MedicineStockEntity>> getList() {
        logger.debug("getMedicineNames", new Object() {
        });
        List<MedicineStockEntity> list = medicineStockRepo.findAll(Sort.by(Sort.Direction.ASC, "expiryDate"));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("pageable")
    @ResponseBody
    public PageCover<MedicineStockEntity> getPage(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
        int noOfItemsInAPage = 30;
        logger.warn("REST getItems() , {} ", new Object[]{label});

        if (!sortColumn.equals("undefined")) {

            if (sortOrder.equals("d")) {
                sort = Sort.by(sortColumn).descending();
            } else {
                sort = Sort.by(sortColumn).ascending();
            }

        } else {
            sort = Sort.by("qtyRemaining").ascending();
        }
        if ("undefined".equals(pageNumber)) {
            pageNumber = "1";
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, noOfItemsInAPage, sort);
        Page<MedicineStockEntity> medicineStockPage;

        if (sortColumn.equals("brandName")) {

            List<MedicineBrandNameEntity> listOfMedicineBrandNameEntitysSortedByBrandName = medicineBrandNameRepo.findAll(Sort.by("brandName"));
            List<MedicineStockEntity> listOfMedicineStockEntitysNew = new ArrayList<>();

            listOfMedicineBrandNameEntitysSortedByBrandName.forEach((brandNameEntity) -> {
                MedicineStockEntity medicineStockEntityFoundById;
                medicineStockEntityFoundById = medicineStockRepo.findAllByMedicineBrandName(brandNameEntity);
                listOfMedicineStockEntitysNew.add(medicineStockEntityFoundById);
            });
            medicineStockPage = new PageExtendedByAyush(listOfMedicineStockEntitysNew, pageable);

        } else {

            medicineStockPage = medicineStockRepo.findAll(pageable);

        }

        List<MedicineStockEntity> list = medicineStockPage.getContent();
        for (MedicineStockEntity medicineStockEntity : list) {
            Long qtyUsed = medicineStockRepo.findQtyUsedById(medicineStockEntity);
            if(qtyUsed!=null){
            medicineStockEntity.setQtyRemaining(medicineStockEntity.getQtyPurchased() - qtyUsed);
             medicineStockRepo.save(medicineStockEntity);
           
            }
            
        }
        PageCover<MedicineStockEntity> medicineStockpageCover = new PageCover<>(medicineStockPage);
        medicineStockpageCover.setSortColumn(sortColumn);
        medicineStockpageCover.setSortOrder(sortOrder);
        medicineStockpageCover.setModule(label);
        return medicineStockpageCover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<MedicineStockEntity> findById(@PathVariable("id") Long id) {
        MedicineStockEntity item = medicineStockRepo.findById(id).get();

//        long used = medicineStockRepo.findQtyRemainById(item);
//        item.setQtyRemaining(item.getQtyPurchased() - used);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // create
    @PostMapping("")
    public ResponseEntity<MedicineStockEntity> postOne(MedicineStockEntity entityBefore) {
        ResponseEntity<MedicineStockEntity> body = null;
        try {
            logger.warn("PostMapping_one id:{} ", entityBefore.toString());
            logger.warn("---- id ={}", entityBefore.getId());
            MedicineStockEntity entityAfter = null;
            if (entityBefore.getId() != 0) {

                entityAfter = medicineStockRepo.findById(entityBefore.getId()).get();

                //entityAfter.setUpdationTime(new Date());
            } else {
                entityAfter = new MedicineStockEntity();
                // entityAfter.setCreationTime(new Date());
            }

            BeanUtils.copyProperties(entityBefore, entityAfter);
//             if(entityBefore.isRateAvailable()==null) entityAfter.setRateAvailable(Boolean.FALSE);

            entityAfter = medicineStockRepo.save(entityAfter);

            body = ResponseEntity
                    .created(new URI("/api/MedicineStockEntity/" + entityAfter.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            entityAfter.getId() + ""))
                    .body(entityAfter);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger("MedicineStockEntity").log(Level.SEVERE, null, ex);
        }
        return body;
    }

    // delete
    @GetMapping("delete/id/{id}")
    public DeleteResponse deleteById(@PathVariable("id") Long id) {
        logger.warn("DeleteMapping_id obj={},id= {}", new Object[]{label, id});
        medicineStockRepo.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("Deleted " + label + " with id " + id);
        return deleteResponse;
    }

    @GetMapping("updateFromExcel")
    public ResponseEntity<?> updateFromExcel() {
        ResponseEntity<?> responseEntity = null;
        try {
            File file = new File("/common/common/dsc/medicineStock.xlsx");
            FileInputStream excelFile
                    = new FileInputStream(file);
            System.out.println("excel file name=" + file.getAbsolutePath());
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheetAt(0);

            Row row0 = sheet.getRow(0);
            short lastCellNum = row0.getLastCellNum();
            for (int colNum = 0; colNum < lastCellNum; colNum++) {
                Cell cell0 = row0.getCell(colNum);
                String stringCellValue = cell0.getStringCellValue();
                System.out.println("value [" + colNum + "] =" + stringCellValue);
            }

            System.out.println("--getLastRowNum------------------------" + sheet.getLastRowNum());

            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                MedicineStockEntity medicineStockEntity = new MedicineStockEntity();
                MedicineBrandNameEntity medicineBrandName = null, medicineBrandNameEntityFound = null;
                Row row = sheet.getRow(rowNum);
                for (int colNum = 0; colNum < lastCellNum; colNum++) {
                    Cell cell0 = row.getCell(colNum);
                    try {
                        switch (colNum) {

                            case 1: {
                                //value [1] =PRODUCT NAME
                                String value = cell0.getStringCellValue();
                                medicineBrandNameEntityFound = medicineBrandNameRepo.findAllByBrandName(value);
                                if (medicineBrandNameEntityFound == null) {
                                    medicineBrandName = new MedicineBrandNameEntity();
                                    medicineBrandName.setId(0);
                                    medicineBrandName.setBrandName(value);
                                } else {
                                    System.out.println(" medicineBrandName exists " + medicineBrandNameEntityFound.toString());
                                    medicineStockEntity.setMedicineBrandName(medicineBrandNameEntityFound);
                                }

                                break;
                            }
                            case 2: {
                                // value [2] =COMPANY
                                String value = cell0.getStringCellValue();
                                if (medicineBrandName != null) {
                                    medicineBrandName.setCompany(value);
                                }

                                break;
                            }
                            case 3: {
                                //value [3] =BATCH

                                String value;
                                try {
                                    value = cell0.getStringCellValue();
                                } catch (Exception e) {
                                    double valueN = cell0.getNumericCellValue();
                                    value = (long) valueN + "";
                                }

                                medicineStockEntity.setBatch(value);
                                break;
                            }
                            case 4: {
//                            value[4] = EXPIRY
                                Date dateCellValue = cell0.getDateCellValue();
                                String yearStr = "20" + (dateCellValue.getYear()) % 100;
                                int month = (dateCellValue.getMonth() + 1);
                                String monthStr = (month < 10 ? "0" + month : month + "");
                                System.out.println("year" + yearStr + ", month=" + monthStr);
                                medicineStockEntity.setExpiryDate(yearStr + "-" + monthStr);

                                break;
                            }
                            case 5: {
                                //value[5] = HSN CODE String value = cell0.getStringCellValue();
                                String value;
                                try {
                                    value = cell0.getStringCellValue();
                                } catch (Exception e) {
                                    double valueN = cell0.getNumericCellValue();
                                    value = (long) valueN + "";
                                }
                                if (medicineBrandName != null) {
                                    medicineBrandName.setHSN(value);
                                }

                                break;
                            }
                            case 6: {
                                // value[6] = MRP

                                double value = cell0.getNumericCellValue();
                                System.out.println("value mrp=" + value);
                                medicineStockEntity.setMrp(new BigDecimal(value + ""));
                                break;
                            }

                            case 7: {
                                //value[7] = CGST

                                double value = cell0.getNumericCellValue();
                                medicineStockEntity.setCgst(new BigDecimal(value + ""));
                                break;
                            }
                            case 8: {
                                // value[8] = SGST
                                double value = cell0.getNumericCellValue();
                                medicineStockEntity.setSgst(new BigDecimal(value + ""));
                                break;
                            }
                            case 9: {
                                // value[9] = vendor
                                double value = cell0.getNumericCellValue();
                                VendorEntity vendorEntity = new VendorEntity();
                                vendorEntity.setId((int) value);
                                medicineStockEntity.setVendor(vendorEntity);
                                break;
                            }

                        }
                        medicineStockEntity.setDiscount(new BigDecimal(0 + ""));
                    } catch (Exception e) {
                        System.out.println("exception at  column " + sheet.getRow(0).getCell(colNum).getStringCellValue());
                        System.out.println("exception =" + e.toString());
                    }

                    if (medicineBrandName != null) {

                        medicineStockEntity.setMedicineBrandName(medicineBrandName);

                    }
                }
                try {
                    if (medicineBrandNameEntityFound == null) {
                        medicineBrandNameResource.PostMapping_one(medicineBrandName);
                    }
                } catch (Exception e) {
                    System.out.println("Some prob in PostMapping_one  medicineBrandName" + e.toString());
                }
                // end of row 
                try {
                    System.out.println("row " + rowNum + "    " + medicineStockEntity.toString());
                    medicineStockResource.postOne(medicineStockEntity);

                } catch (Exception e) {
                    System.out.println("Some prob in PostMapping_one medicineStockEntity  " + e.toString());
                }

//            
            }

        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("exception at " + e.toString());
        }
        responseEntity = ResponseEntity.created(null).body("ok");

        return responseEntity;
    }

}
