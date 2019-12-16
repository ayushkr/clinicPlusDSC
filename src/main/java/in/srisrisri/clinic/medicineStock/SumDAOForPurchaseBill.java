/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.medicineStock.MedicineStockEntity;
import in.srisrisri.clinic.utils.FinanceUtils;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;

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

    BigDecimal grandPTS = new BigDecimal(0);
    BigDecimal grandPts_times_qty = new BigDecimal(0);
    BigDecimal grandDiscount = new BigDecimal(0);
    BigDecimal grandTaxableAmt = new BigDecimal(0);
    BigDecimal grandTaxAmountWO_Cess = new BigDecimal(0);
    BigDecimal grandTotalwocess = new BigDecimal(0);
    BigDecimal grandTotalwcess = new BigDecimal(0);
    BigDecimal grandCostPrice = new BigDecimal(0);

    public SumDAOForPurchaseBill(List<MedicineStockEntity> medicineStockEntitys) {
        this.medicineStockEntitys = medicineStockEntitys;
        logger.warn("SumDAOForPurchaseBill created ");
    }

    public boolean calculateTotals() throws Exception {
        String error = "";
        logger.warn("SumDAOForPurchaseBill.calculateTotals strated");
        
        // sort by slno
        Comparator<MedicineStockEntity> comparator_slno
                = (MedicineStockEntity o1, MedicineStockEntity o2) -> {
//                    System.out.println("Comapring "+o1.getId());
                    return o1.getSlno_in_bill() - o2.getSlno_in_bill();
                };
        
        Collections.sort(medicineStockEntitys, comparator_slno);
        
        
        for (MedicineStockEntity mse : medicineStockEntitys) {

            grandPTS = grandPTS.add(mse.getPts());
            grandDiscount = grandDiscount.add(mse.getDiscount());

            grandTaxAmountWO_Cess = grandTaxAmountWO_Cess.add(mse.getTaxAmountWO_Cess());
            grandTaxableAmt = grandTaxableAmt.add(mse.getTaxableAmt());
            grandPts_times_qty = grandPts_times_qty.add(mse.getPts_times_qty());
            grandTotalwocess = grandTotalwocess.add(mse.getTotalwocess());
            BigDecimal cess = (mse.getTaxAmountWO_Cess().multiply(mse.getCess_perc())).divide(BigDecimal.valueOf(100));
            grandTotalwcess = grandTotalwocess.add(cess);

        }

        

        grandTotalwcess = FinanceUtils.round(grandTotalwcess, 2);

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

    public BigDecimal getGrandTotalwcess() {
        return grandTotalwcess;
    }

    public void setGrandTotalwcess(BigDecimal grandTotalwcess) {
        this.grandTotalwcess = grandTotalwcess;
    }

    public BigDecimal getGrandPTS() {
        return grandPTS;
    }

    public void setGrandPTS(BigDecimal grandPTS) {
        this.grandPTS = grandPTS;
    }

    public BigDecimal getGrandTaxableAmt() {
        return grandTaxableAmt;
    }

    public void setGrandTaxableAmt(BigDecimal grandTaxableAmt) {
        this.grandTaxableAmt = grandTaxableAmt;
    }

    public BigDecimal getGrandTaxAmountWO_Cess() {
        return grandTaxAmountWO_Cess;
    }

    public void setGrandTaxAmountWO_Cess(BigDecimal grandTaxAmountWO_Cess) {
        this.grandTaxAmountWO_Cess = grandTaxAmountWO_Cess;
    }

    public BigDecimal getGrandTotalwocess() {
        return grandTotalwocess;
    }

    public void setGrandTotalwocess(BigDecimal grandTotalwocess) {
        this.grandTotalwocess = grandTotalwocess;
    }

    public BigDecimal getGrandPts_times_qty() {
        return grandPts_times_qty;
    }

    public void setGrandPts_times_qty(BigDecimal grandPts_times_qty) {
        this.grandPts_times_qty = grandPts_times_qty;
    }

    public BigDecimal getGrandCostPrice() {
        return grandCostPrice;
    }

    public void setGrandCostPrice(BigDecimal grandCostPrice) {
        this.grandCostPrice = grandCostPrice;
    }

    public BigDecimal getGrandDiscount() {
        return grandDiscount;
    }

    public void setGrandDiscount(BigDecimal grandDiscount) {
        this.grandDiscount = grandDiscount;
    }

}
