package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.entities.MedicineStockEntity;
import in.srisrisri.clinic.entities.MedicineBrandNameEntity;
import in.srisrisri.clinic.Constants.Constants1;
import in.srisrisri.clinic.Vendor.VendorEntity;
import in.srisrisri.clinic.Vendor.VendorRepo;
import in.srisrisri.clinic.medicineBrandName.*;
import in.srisrisri.clinic.entities.PurchaseBillEntity;
import in.srisrisri.clinic.responses.JsonResponse;
import in.srisrisri.clinic.titles.Titles;
import in.srisrisri.clinic.utils.HeaderUtil;
import in.srisrisri.clinic.utils.PageCover;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
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
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
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
    public PageCover<MedicineStockEntity> pageable(
            @RequestParam("pageNumber") String pageNumber,
            @RequestParam("filterColumn") String filterColumn,
            @RequestParam("filter") String filter,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortOrder") String sortOrder
    ) {
        Sort sort;
       
         int pageSize = 20;
        logger.warn("pageable={} , filter={}by {} ", new Object[]{label, filterColumn, filter});

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
        } else {
            if (Integer.parseInt(pageNumber) == 0) {
                pageSize = 10000;
                pageNumber = "1";
            }
        }
        
        
        
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, pageSize, sort);
        Page<MedicineStockEntity> page = null;

        if (filterColumn.equals("undefined")) {
            page = medicineStockRepo.findAll(pageable);
        } else {
            if (filterColumn.equals("brandName")) {

                page = medicineStockRepo.findAllByBrandNameLike(filter, pageable);
            }

            if (filterColumn.equals("genericName")) {

                page = medicineStockRepo.findAllByGenericNameLike(filter, pageable);
            }

            if (filterColumn.equals("composition")) {

                page = medicineStockRepo.findAllByCompositionLike(filter, pageable);
            }
        }

        List<MedicineStockEntity> medicineStockEntitysList = page.getContent();
        for (MedicineStockEntity medicineStockEntity : medicineStockEntitysList) {
            Long qtyUsed = medicineStockRepo.findQtyUsedById(medicineStockEntity);
            if (qtyUsed != null) {
                medicineStockEntity.setQtyRemaining(medicineStockEntity.getQtyPurchased() - qtyUsed);
                medicineStockRepo.save(medicineStockEntity);

            }

        }
        PageCover<MedicineStockEntity> cover = new PageCover<>(page);
        cover.setSortColumn(sortColumn);
        cover.setSortOrder(sortOrder);
        cover.setFilter(filter);
        cover.setFilterColumn(filterColumn);
        cover.setModule(label);
        
         Titles titles = new Titles();
        titles.setModuleName(label);
        titles.setId2(true);
        titles.setName(true);
       
       titles.setList(new String[]{"id","medicineBrandName_brandName","company"});
        cover.setTitles(titles);
        
        
        
        
        
        return cover;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Optional<MedicineStockEntity>> findById(@PathVariable("id") Long id) {
        logger.warn("---- id+1 ={}", id + 1);
        Optional<MedicineStockEntity> item;
        if (id != -1) {
            item = medicineStockRepo.findById(id);
        } else {
            MedicineStockEntity entityAfter = new MedicineStockEntity();
            entityAfter.setQtyPurchased(1);
            entityAfter.setPts(BigDecimal.valueOf(6));
            entityAfter.setPts_times_qty(BigDecimal.ZERO);
            entityAfter.setMrp(BigDecimal.ZERO);
            entityAfter.setDiscount(BigDecimal.ZERO);
            entityAfter.setGst_perc(BigDecimal.valueOf(12));
            entityAfter.setCgst_perc(BigDecimal.valueOf(6));
            entityAfter.setSgst_perc(BigDecimal.valueOf(6));
            entityAfter.setCess_perc(BigDecimal.valueOf(1));
            entityAfter.setDiscount_perc(BigDecimal.ZERO);
            entityAfter.setSellingPrice(BigDecimal.ZERO);
            entityAfter.setCostPrice(BigDecimal.ZERO);

            entityAfter.setCreationTime(java.sql.Date.valueOf(LocalDate.now()));
            medicineStockRepo.save(entityAfter);
            item = Optional.of(entityAfter);

        }
//        long used = medicineStockRepo.findQtyRemainById(item);
//        item.setQtyRemaining(item.getQtyPurchased() - used);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("ByBillId/{id}")
    @ResponseBody
    public ResponseEntity<?> ByBillId_id(@PathVariable("id") Long id) {

        JsonResponse jsonResponse = new JsonResponse();
        PurchaseBillEntity purchaseBillEntity = new PurchaseBillEntity();
        purchaseBillEntity.setId(id);

        List<MedicineStockEntity> medicineStockEntityList
                = medicineStockRepo.findAllByPurchaseBill(purchaseBillEntity);
        SumDAOForPurchaseBill sumDAO = new SumDAOForPurchaseBill(medicineStockEntityList);
        sumDAO.setBillId(id);
        try {
            sumDAO.calculateTotals();
            jsonResponse.setStatus(Constants1.SUCCESS);
            jsonResponse.setMessage("ok");
            jsonResponse.getMap().put("payload", sumDAO);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            
        } catch (Exception e) {
            logger.warn("Exception= {} ", e);
            jsonResponse.setStatus(Constants1.FAILURE);
            jsonResponse.setMessage("In SumDAOForPurchaseBill.calculateTotals(); <br>&nbsp;<span>"
                    + e.toString() + "</span>");
            jsonResponse.getMap().put("payload", sumDAO);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }

    }
    BigDecimal HUNDRED = BigDecimal.valueOf(100);

    // create
    @PostMapping("")
    public ResponseEntity<JsonResponse> PostMapping_one(MedicineStockEntity b) {
        ResponseEntity<JsonResponse> repsonse = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            logger.warn("PostMapping_one id:{} ", b.toString());
            logger.warn("---- id ={}", b.getId());
            MedicineStockEntity a = null;

            a = medicineStockRepo.findById(b.getId()).get();

            BeanUtils.copyProperties(b, a);
            // business logic
            a.setSellingPrice(b.getMrp());

            a.setPts_times_qty(a.getPts().multiply(new BigDecimal((float) a.getQtyPurchased())));

            BigDecimal discountAmt = a.getPts_times_qty().multiply(a.getDiscount_perc().divide(HUNDRED));
            a.setDiscount(discountAmt);
            BigDecimal taxableAmt = a.getPts_times_qty().subtract(discountAmt);

//            BigDecimal discFac = BigDecimal.ONE.subtract(a.getDiscount_perc().divide(HUNDRED));        
//            BigDecimal taxableAmt = a.getPts_times_qty().multiply(discFac);
            a.setTaxableAmt(taxableAmt);

            BigDecimal taxAmountWO_Cess = a.getTaxableAmt().multiply(a.getGst_perc().divide(HUNDRED));
            a.setTaxAmountWO_Cess(taxAmountWO_Cess);

            BigDecimal totalwocess = a.getTaxAmountWO_Cess().add(a.getTaxableAmt());
            a.setTotalwocess(totalwocess);

            BigDecimal amountTotal = a.getTotalwocess().add((a.getTaxAmountWO_Cess().multiply(a.getCess_perc().divide(HUNDRED))));
            a.setAmountTotal(amountTotal);

            BigDecimal costPrice = a.getAmountTotal().divide(BigDecimal.valueOf(a.getQtyPurchased()));
            a.setCostPrice(costPrice);
//             if(entityBefore.isRateAvailable()==null) entityAfter.setRateAvailable(Boolean.FALSE);

            try {
//                if (entityAfter.getCostPrice() == BigDecimal.ZERO) {
//                    long qtyPurchased = entityAfter.getQtyPurchased();
//                    BigDecimal qtyBigDec = new BigDecimal(qtyPurchased);
//                    entityAfter.setCostPrice(entityAfter.getAmountTotal().divide(qtyBigDec));
//                }

                a = medicineStockRepo.save(a);
                jsonResponse.setMessage("Saved ID:" + a.getId());
                jsonResponse.setStatus(Constants1.SUCCESS);
            } catch (Exception e) {
                jsonResponse.setMessage(e.toString());
                jsonResponse.setStatus(Constants1.FAILURE);
            }
            repsonse = ResponseEntity
                    .created(new URI("/api/MedicineStockEntity/" + a.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(label,
                            a.getId() + ""))
                    .body(jsonResponse);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger("MedicineStockEntity").log(Level.SEVERE, null, ex);
        }
        return repsonse;
    }

    @GetMapping("import/{id}/{test}")
    public ResponseEntity<?> updateFromExcel(@PathVariable("id") String id, @PathVariable("test") String test) {
        ResponseEntity<?> responseEntity = null;
        File file = new File("/tmp/a");
        URL url = null;
        try {

            try {
                url = new URL("http://localhost:8081/clinicPlus/api/fileContent/content/" + id + "");
                System.out.println("url =" + url.toString());
            } catch (Exception e) {
                System.out.println("url = new URL(\"htt  e= " + e.toString());
            }

            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException e) {
                System.out.println(" FileUtils.copyURLToFile  e= " + e.toString());
            }

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
                                medicineStockEntity.setSellingPrice(new BigDecimal(value + ""));
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
//                                medicineStockEntity.setVendor(vendorEntity);
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
                        if (test.equals("post")) {
                            medicineBrandNameResource.PostMapping_one(medicineBrandName);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Some prob in PostMapping_one  medicineBrandName" + e.toString());
                }
                // end of row 
                try {
                    System.out.println("row " + rowNum + "    " + medicineStockEntity.toString());
                    if (test.equals("post")) {
                        medicineStockResource.PostMapping_one(medicineStockEntity);
                    }

                } catch (Exception e) {
                    System.out.println("Some prob in PostMapping_one medicineStockEntity  " + e.toString());
                }

//            
            }

        } catch (FileNotFoundException e) {
            System.out.println("exception FileNotFoundException= " + e.toString());
        } catch (Exception e) {
            System.out.println("exception at " + e.toString());
        }
        responseEntity = ResponseEntity.created(null).body("ok");

        return responseEntity;
    }

    // delete
    @GetMapping("delete/id/{id}")
    public JsonResponse DeleteMapping_id(@PathVariable("id") Long id) {

        JsonResponse response = new JsonResponse();
        logger.warn("REST request to delete {} {}", new Object[]{label, id});
        try {
            medicineStockRepo.deleteById(id);
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
        String failedIds = "";
        try {
            logger.warn("deleteBulk , got={} ", list.toString());
            for (Long n : list) {
                System.out.println(" n=" + n);
                try {
                    medicineStockRepo.deleteById(n);
                } catch (Exception e) {

                    failedIds += "<hr><p>I Cannot delete " + label + " ID:" + n
                            + "<br>Because  "
                            + ((e.getMessage().contains("ConstraintViolationException"))
                            ? "It Used in Other place " : e.getMessage())
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
