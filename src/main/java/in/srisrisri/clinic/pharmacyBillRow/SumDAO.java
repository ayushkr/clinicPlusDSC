/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.medicineStock.MedicineStockEntity;
import in.srisrisri.clinic.utils.FinanceUtils;
import java.math.BigDecimal;

import java.util.List;

/**
 *
 * @author akr
 */
public class SumDAO {

    List<PharmacyBillRowEntity> pharmacyBillRowEntitys;

    float mrpTotal = 0.0f;
    float gstTotal = 0.0f;
    float amountTotal = 0.0f;
    String amountTotalWords="";
    BigDecimal bd=new BigDecimal(1.75);
    float taxableAmount;
    float freeOfCost;

    public void findTotals() {

        for (PharmacyBillRowEntity pharmacyBillRowEntity : pharmacyBillRowEntitys) {
            MedicineStockEntity medicineStock = pharmacyBillRowEntity.getMedicineStock();
            if(medicineStock!=null){
            float mrpPerItem=medicineStock.getMrp()/medicineStock.getSubCount();
            float amtWithoutDiscount=mrpPerItem*pharmacyBillRowEntity.getQty();
            medicineStock.setMrp(amtWithoutDiscount);
              float amtWithDiscount=medicineStock.getRate()*pharmacyBillRowEntity.getQty();
               pharmacyBillRowEntity.setAmount(amtWithDiscount);
            float gstAmt=(amtWithoutDiscount*medicineStock.getGst()/100);
            medicineStock.setGst(gstAmt);
            System.out.println("id " + pharmacyBillRowEntity.getId());

            mrpTotal += medicineStock.getMrp();
            gstTotal += medicineStock.getGst();
            amountTotal += pharmacyBillRowEntity.getAmount();
            amountTotalWords=FinanceUtils.RsToWords(amountTotal+"");
            
            medicineStock.setRate(FinanceUtils.round(medicineStock.getRate(),2));
             medicineStock.setGst(FinanceUtils.round(medicineStock.getGst(),2));
              pharmacyBillRowEntity.setAmount(FinanceUtils.round(pharmacyBillRowEntity.getAmount(), 2));
      
            }
               }
            taxableAmount=FinanceUtils.round(amountTotal-gstTotal/2, 2);
            freeOfCost=FinanceUtils.round(mrpTotal-amountTotal, 2);
    }

    public float getFreeOfCost() {
        return freeOfCost;
    }

    public void setFreeOfCost(float freeOfCost) {
        this.freeOfCost = freeOfCost;
    }

    public float getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(float taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public BigDecimal getBd() {
        return bd;
    }

    public void setBd(BigDecimal bd) {
        this.bd = bd;
    }

    public String getAmountTotalWords() {
        return amountTotalWords;
    }

    public void setAmountTotalWords(String amountTotalWords) {
        this.amountTotalWords = amountTotalWords;
    }

    public float getMrpTotal() {
        return FinanceUtils.round(mrpTotal, 2);
    }

    public float getGstTotal() {
        return FinanceUtils.round(gstTotal, 2);
      
    }

    public float getAmountTotal() {
        
           return FinanceUtils.round(amountTotal, 2);
    }

    public SumDAO(List<PharmacyBillRowEntity> pharmacyBillRowEntitys) {
        this.pharmacyBillRowEntitys = pharmacyBillRowEntitys;
        findTotals();
    }

    public List<PharmacyBillRowEntity> getPharmacyBillRowEntitys() {
        return pharmacyBillRowEntitys;
    }

    public void setPharmacyBillRowEntitys(List<PharmacyBillRowEntity> pharmacyBillRowEntitys) {
        this.pharmacyBillRowEntitys = pharmacyBillRowEntitys;
    }

}
