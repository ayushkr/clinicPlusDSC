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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author akr
 */
public class SumDAO {
   private final Logger logger = LoggerFactory.getLogger(SumDAO.class);

    List<PharmacyBillRowEntity> pharmacyBillRowEntitys;

    BigDecimal mrpTotal = new BigDecimal(0);
    BigDecimal gstTotal = new BigDecimal(0);
    BigDecimal amountTotal = new BigDecimal(0);
     BigDecimal rounded = new BigDecimal(0);
      BigDecimal amountTotalRounded = new BigDecimal(0);
    String amountTotalWords = "";
    BigDecimal bd = new BigDecimal(1.75);
    BigDecimal bd100 = new BigDecimal(100);
     BigDecimal bd1 = new BigDecimal(1);
    BigDecimal bd2 = new BigDecimal(2);
    BigDecimal taxableAmount;
    BigDecimal freeOfCost;
     BigDecimal qty;
     BigDecimal rate ;
      BigDecimal discount ;

      Long billId;
      
    public boolean calculateTotals() throws Exception{
String error="";
        for (PharmacyBillRowEntity pharmacyBillRowEntity : pharmacyBillRowEntitys) {
            MedicineStockEntity medicineStock = pharmacyBillRowEntity.getMedicineStock();
            
            
           logger.warn("findTotals,medicineStock={}", medicineStock);
         
            if (medicineStock != null) {
               
                try{
                rate = medicineStock.getRate();
                discount=medicineStock.getDiscount();
                  logger.warn("findTotals, rate ={}", rate);
                }catch(Exception e){
                    error="exception in mrp or discount";
                    logger.warn("exception in mrp or discount{}", e);
                    break;
               
                }
                 
               
                qty = new BigDecimal(pharmacyBillRowEntity.getQty() + "");
              //  logger.warn("qty, mrpPerItem ={}", qty);
              
                BigDecimal amtWithoutDiscount = rate.multiply(qty);
                medicineStock.setMrp(amtWithoutDiscount);
            //     logger.warn("findTotals, amtWithoutDiscount ={}", amtWithoutDiscount);
                BigDecimal amtWithDiscount = rate.multiply(bd1.subtract(discount)).multiply(qty);
                pharmacyBillRowEntity.setAmount(amtWithDiscount);
            //      logger.warn("findTotals, amtWithDiscount ={}", amtWithDiscount);
                BigDecimal gstAmt = (amtWithoutDiscount.multiply(medicineStock.getCgst().add(medicineStock.getSgst())));
            //     logger.warn("findTotals, gstAmt ={}", gstAmt);
                
                medicineStock.setGst(gstAmt);
                
                
                
            //    logger.warn("findTotals,pharmacyBillRowEntity id={}", pharmacyBillRowEntity.getId());
                if(medicineStock.getMedicineBrandName().getBrandName().equals("roundOff")){
                rounded=pharmacyBillRowEntity.getAmount();
                }else{
                mrpTotal = mrpTotal.add(medicineStock.getMrp());
                gstTotal = gstTotal.add(medicineStock.getGst());
                amountTotal = amountTotal.add(pharmacyBillRowEntity.getAmount());
                }

               

                medicineStock.setRate(FinanceUtils.round(medicineStock.getRate(), 2));
                medicineStock.setGst(FinanceUtils.round(medicineStock.getGst(), 2));
                pharmacyBillRowEntity.setAmount(FinanceUtils.round(pharmacyBillRowEntity.getAmount(), 2));

            }
           
        }
        
        taxableAmount = FinanceUtils.round(amountTotal.subtract(gstTotal).divide(bd2), 2);
        freeOfCost = FinanceUtils.round(mrpTotal.subtract(amountTotal), 2);
        amountTotalRounded=amountTotal.add(rounded);
         amountTotalWords = FinanceUtils.RsToWords(amountTotalRounded + "");
        return true;
    }

    public BigDecimal getRounded() {
        return rounded;
    }

    public void setRounded(BigDecimal rounded) {
        this.rounded = rounded;
    }

    public BigDecimal getAmountTotalRounded() {
        return amountTotalRounded;
    }

    public void setAmountTotalRounded(BigDecimal amountTotalRounded) {
        this.amountTotalRounded = amountTotalRounded;
    }

    
    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
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

    public BigDecimal getMrpTotal() {
        return FinanceUtils.round(mrpTotal, 2);
    }

    public BigDecimal getGstTotal() {
        return FinanceUtils.round(gstTotal, 2);

    }

    public BigDecimal getAmountTotal() {

        return FinanceUtils.round(amountTotal, 2);
    }

    public SumDAO(List<PharmacyBillRowEntity> pharmacyBillRowEntitys) {
        this.pharmacyBillRowEntitys = pharmacyBillRowEntitys;
          logger.warn("SumDAO created ");
    }

    public List<PharmacyBillRowEntity> getPharmacyBillRowEntitys() {
        return pharmacyBillRowEntitys;
    }

    public void setPharmacyBillRowEntitys(List<PharmacyBillRowEntity> pharmacyBillRowEntitys) {
        this.pharmacyBillRowEntitys = pharmacyBillRowEntitys;
    }

}
