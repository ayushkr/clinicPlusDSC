/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.purchaseBill;

import in.srisrisri.clinic.pharmacyBillRow.*;
import in.srisrisri.clinic.exception.CustomException;
import in.srisrisri.clinic.medicineStock.MedicineStockEntity;
import in.srisrisri.clinic.medicineStock.MedicineStockRepo;
import in.srisrisri.clinic.utils.FinanceUtils;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author akr
 */
public class SumDAOForPurchaseBill {

    private final Logger logger = LoggerFactory.getLogger(SumDAOForPurchaseBill.class);
    
    List<MedicineStockEntity> medicineStockEntitys;

    Long billId;

    public SumDAOForPurchaseBill(List<MedicineStockEntity> medicineStockEntitys) {
        this.medicineStockEntitys = medicineStockEntitys;
        logger.warn("SumDAOForPurchaseBill created ");
    }

    public boolean calculateTotals() throws Exception {
        String error = "";
        logger.warn("calculateTotals not impl");


        return true;
    }

    


    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public List<MedicineStockEntity> getMedicineStockEntitys() {
        return medicineStockEntitys;
    }

    public void setMedicineStockEntitys(List<MedicineStockEntity> medicineStockEntitys) {
        this.medicineStockEntitys = medicineStockEntitys;
    }

  

}
