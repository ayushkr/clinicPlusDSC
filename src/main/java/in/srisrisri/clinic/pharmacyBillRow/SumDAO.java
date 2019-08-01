/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.exception.CustomException;
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
    BigDecimal HUNDRED = new BigDecimal(100);
    BigDecimal ONE = new BigDecimal(1);
    BigDecimal TWO = new BigDecimal(2);
    BigDecimal taxableAmount;
    BigDecimal freeOfCost;
    BigDecimal qty;
    BigDecimal sp;
    BigDecimal discount;

    Long billId;

    public boolean calculateTotals() throws Exception {
        String error = "";

        for (PharmacyBillRowEntity pharmacyBillRowEntity : pharmacyBillRowEntitys) {
            MedicineStockEntity mediStockInOneRowInPharmacyBill = pharmacyBillRowEntity.getMedicineStock();

            logger.warn("calculateTotals ,medicineStock={}", mediStockInOneRowInPharmacyBill);

            if (mediStockInOneRowInPharmacyBill != null) {

                try {
                    sp = mediStockInOneRowInPharmacyBill.getSellingPrice();
                    logger.warn("\t calculateTotals, sp ={}", sp);
                    discount = mediStockInOneRowInPharmacyBill.getDiscount();
                    logger.warn("\t calculateTotals, discount ={}", discount);
                } catch (Exception e) {
                    logger.warn("\t--- calculateTotals, sp or discount is null");
                }

                qty = new BigDecimal(pharmacyBillRowEntity.getQty() + "");
                logger.warn("\t calculateTotals, qty ={}", qty);

                BigDecimal amtWithoutDiscount = sp.multiply(qty);
                logger.warn("\t calculateTotals, amtWithoutDiscount ={}", amtWithoutDiscount);
                BigDecimal amtWithDiscount = BigDecimal.ZERO;
                if (discount != null) {
                    amtWithDiscount = sp.multiply(BigDecimal.ONE.subtract(discount)).multiply(qty);
                    logger.warn("\t calculateTotals, amtWithDiscount ={}", amtWithDiscount);
                } else {
                    amtWithDiscount = amtWithoutDiscount;
                }
                pharmacyBillRowEntity.setAmount(amtWithDiscount);

                BigDecimal gstAmt = (amtWithoutDiscount.multiply(mediStockInOneRowInPharmacyBill.getCgst().add(mediStockInOneRowInPharmacyBill.getSgst())));
                logger.warn("\t calculateTotals, gstAmt ={}", gstAmt);

                mediStockInOneRowInPharmacyBill.setGst(gstAmt);

                mediStockInOneRowInPharmacyBill.setGst(BigDecimal.ZERO);

                if (mediStockInOneRowInPharmacyBill.getMedicineBrandName().getBrandName().equals("roundOff")) {
                    rounded = pharmacyBillRowEntity.getAmount();
                } else {
                    BigDecimal mrp = mediStockInOneRowInPharmacyBill.getMrp();
                    if (mrp == null) {
                        throw new CustomException("In medicine="
                                +mediStockInOneRowInPharmacyBill.getMedicineBrandName().getBrandName()+
                                " with ID="+mediStockInOneRowInPharmacyBill.getId()+", mrp is not set");
                    }
                    mrpTotal = mrpTotal.add(mrp);

                    gstTotal = gstTotal.add(mediStockInOneRowInPharmacyBill.getGst());
                    amountTotal = amountTotal.add(pharmacyBillRowEntity.getAmount());
                }

                mediStockInOneRowInPharmacyBill.setSellingPrice(FinanceUtils.round(mediStockInOneRowInPharmacyBill.getSellingPrice(), 2));
                mediStockInOneRowInPharmacyBill.setGst(FinanceUtils.round(mediStockInOneRowInPharmacyBill.getGst(), 2));
                pharmacyBillRowEntity.setAmount(FinanceUtils.round(pharmacyBillRowEntity.getAmount(), 2));

            }

        }

        try {
            taxableAmount = FinanceUtils.round(amountTotal.subtract(gstTotal).divide(TWO), 2);
            logger.warn(" taxableAmount={} ", taxableAmount);

            freeOfCost = FinanceUtils.round(mrpTotal.subtract(amountTotal), 2);
            logger.warn(" freeOfCost={} ", freeOfCost);
            amountTotalRounded = amountTotal.add(rounded);
            logger.warn(" amountTotalRounded={} ", amountTotalRounded);
            amountTotalWords = FinanceUtils.RsToWords(amountTotalRounded + "");
            logger.warn(" rounding ");
        } catch (Exception e) {
            logger.warn("exception in rounding e={}", e);

        }

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
