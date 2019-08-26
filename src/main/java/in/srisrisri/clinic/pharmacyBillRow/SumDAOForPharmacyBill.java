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
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author akr
 */
public class SumDAOForPharmacyBill {

    private final Logger logger = LoggerFactory.getLogger(SumDAOForPharmacyBill.class);
    @Autowired
    PharmacyBillRowRepo pharmacyBillRowRepo;
    List<PharmacyBillRowEntity> pharmacyBillRowEntitys;

    BigDecimal mrpTotal = new BigDecimal(0);
    BigDecimal gstTotal = new BigDecimal(0);
    BigDecimal amountTotal = new BigDecimal(0);
    BigDecimal roundThisMuch = new BigDecimal(0);
    BigDecimal amountTotalRounded = new BigDecimal(0);
    String amountTotalWords = "";
    BigDecimal bd = new BigDecimal(1.75);
    BigDecimal HUNDRED = new BigDecimal(100);
    BigDecimal ONE = new BigDecimal(1);
    BigDecimal TWO = new BigDecimal(2);
    BigDecimal FIVE = new BigDecimal(5);
    BigDecimal taxableAmount;
    BigDecimal freeOfCost;
    BigDecimal qty;
    BigDecimal sp;
    BigDecimal discount;
    Long rounderId;

    Long billId;

    public boolean calculateTotals() throws Exception {
        String error = "";

        for (PharmacyBillRowEntity pharmacyBillRowEntity : pharmacyBillRowEntitys) {
            MedicineStockEntity medicineStock_gotFrom_PharmacyBillRowEntity = pharmacyBillRowEntity.getMedicineStock();

            logger.warn("calculateTotals ,\n medicineStock={}", medicineStock_gotFrom_PharmacyBillRowEntity);

            if (pharmacyBillRowEntity.getIdSpecial() == ConstantsInPharmacyBillRow.ROUNDOFF_ID_SPECIAL) {
                    logger.warn("\t IdSpecial ={}", pharmacyBillRowEntity.toString());
                    rounderId = pharmacyBillRowEntity.getId();
                    logger.warn("\t rounderId={}", rounderId);
                }
            if (medicineStock_gotFrom_PharmacyBillRowEntity != null) {

                try {
                    sp = medicineStock_gotFrom_PharmacyBillRowEntity.getSellingPrice();
                    logger.warn("\t calculateTotals, sp ={}", sp);
                    discount = medicineStock_gotFrom_PharmacyBillRowEntity.getDiscount();
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

                BigDecimal gstAmt = (amtWithoutDiscount.multiply(medicineStock_gotFrom_PharmacyBillRowEntity.getCgst().add(medicineStock_gotFrom_PharmacyBillRowEntity.getSgst())));
                logger.warn("\t calculateTotals, gstAmt ={}", gstAmt);

                medicineStock_gotFrom_PharmacyBillRowEntity.setGst(gstAmt);

                medicineStock_gotFrom_PharmacyBillRowEntity.setGst(BigDecimal.ZERO);

                 
                {
                    BigDecimal mrp = medicineStock_gotFrom_PharmacyBillRowEntity.getMrp();
                    if (mrp == null) {
                        throw new CustomException("In medicine="
                                + medicineStock_gotFrom_PharmacyBillRowEntity.getMedicineBrandName().getBrandName()
                                + " with ID=" + medicineStock_gotFrom_PharmacyBillRowEntity.getId() + ", mrp is not set");
                    }
                    mrpTotal = mrpTotal.add(mrp);
                    gstTotal = gstTotal.add(medicineStock_gotFrom_PharmacyBillRowEntity.getGst());
                    amountTotal = amountTotal.add(pharmacyBillRowEntity.getAmount());
                }

                medicineStock_gotFrom_PharmacyBillRowEntity.setSellingPrice(FinanceUtils.round(medicineStock_gotFrom_PharmacyBillRowEntity.getSellingPrice(), 2));
                medicineStock_gotFrom_PharmacyBillRowEntity.setGst(FinanceUtils.round(medicineStock_gotFrom_PharmacyBillRowEntity.getGst(), 2));
                pharmacyBillRowEntity.setAmount(FinanceUtils.round(pharmacyBillRowEntity.getAmount(), 2));

            }

        }

        try {
            taxableAmount = FinanceUtils.round(amountTotal.subtract(gstTotal).divide(TWO), 2);
            logger.warn(" taxableAmount={} ", taxableAmount);

            freeOfCost = FinanceUtils.round(mrpTotal.subtract(amountTotal), 2);
            logger.warn(" freeOfCost={} ", freeOfCost);

            amountTotalRounded = amountTotal.add(getRoundWithNextMultipleOf5(amountTotal));
            logger.warn(" amountTotalRounded={} ", amountTotalRounded);
            amountTotalWords = FinanceUtils.RsToWords(amountTotalRounded + "");
            logger.warn(" rounding ");
        } catch (Exception e) {
            logger.warn("exception in rounding e={}", e);

        }

        return true;
    }

    public BigDecimal getRoundWithNextMultipleOf5(BigDecimal totalDirty) {

        BigDecimal[] divideAndRemainder = totalDirty.divideAndRemainder(FIVE);

        logger.warn("getRoundWithNextMultipleOf5() \t divideAndRemainder {} ,{}", divideAndRemainder[0], divideAndRemainder[1]);
        BigDecimal subtract = BigDecimal.ZERO.subtract(divideAndRemainder[1]);
        setRounded(subtract);
        try {
            PharmacyBillRowEntity pharmacyBillRowEntity = pharmacyBillRowRepo.findById(rounderId).get();
            pharmacyBillRowEntity.setAmount(subtract);
            PharmacyBillRowEntity saved = pharmacyBillRowRepo.save(pharmacyBillRowEntity);
            logger.warn("getRoundWithNextMultipleOf5() \t saved rounding ={}", saved.toString());
        } catch (Exception e) {
             logger.warn("getRoundWithNextMultipleOf5() \t exp ={}", e.toString());
            logger.warn("getRoundWithNextMultipleOf5 \t  pharmacyBillRowRepo.findById null id={}", rounderId);
        }
        return subtract;

    }

    public BigDecimal getRounded() {
        return roundThisMuch;
    }

    public void setRounded(BigDecimal rounded) {
        this.roundThisMuch = rounded;
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

    public SumDAOForPharmacyBill(List<PharmacyBillRowEntity> pharmacyBillRowEntitys,PharmacyBillRowRepo pharmacyBillRowRepo) {
        this.pharmacyBillRowEntitys = pharmacyBillRowEntitys;
        this.pharmacyBillRowRepo=pharmacyBillRowRepo;
        logger.warn("SumDAO created ");
    }

    public List<PharmacyBillRowEntity> getPharmacyBillRowEntitys() {
        return pharmacyBillRowEntitys;
    }

    public void setPharmacyBillRowEntitys(List<PharmacyBillRowEntity> pharmacyBillRowEntitys) {
        this.pharmacyBillRowEntitys = pharmacyBillRowEntitys;
    }

}
