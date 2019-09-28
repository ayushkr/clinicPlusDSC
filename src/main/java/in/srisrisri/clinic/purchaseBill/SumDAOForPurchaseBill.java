/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.purchaseBill;

import in.srisrisri.clinic.medicineStock.MedicineStockEntity;
import in.srisrisri.clinic.utils.FinanceUtils;
import java.math.BigDecimal;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author akr
 */
public class SumDAOForPurchaseBill {

    private final Logger logger = LoggerFactory.getLogger(SumDAOForPurchaseBill.class);
    List<MedicineStockEntity> medicineStockEntitys;
    Long billId;
    BigDecimal amountTotalGrand = new BigDecimal(0);
    
    

    public SumDAOForPurchaseBill(List<MedicineStockEntity> medicineStockEntitys) {
        this.medicineStockEntitys = medicineStockEntitys;
        logger.warn("SumDAOForPurchaseBill created ");
    }

    public boolean calculateTotals() throws Exception {
        String error = "";
        logger.warn("SumDAOForPurchaseBill.calculateTotals strated");
        for (MedicineStockEntity medicineStockEntity : medicineStockEntitys) {
            BigDecimal amountTotal = medicineStockEntity.getAmountTotal();
           amountTotalGrand= amountTotalGrand.add(amountTotal);
            logger.warn("SumDAOForPurchaseBill.calculateTotals amountTotal=" + amountTotal);
        }
       amountTotalGrand= FinanceUtils.round(amountTotalGrand, 2);
        
          logger.warn("SumDAOForPurchaseBill.calculateTotals amountTotalGrand=" + amountTotalGrand);

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

    public BigDecimal getAmountTotalGrand() {
        return amountTotalGrand;
    }

    public void setAmountTotalGrand(BigDecimal amountTotalGrand) {
        this.amountTotalGrand = amountTotalGrand;
    }

    
    
}
