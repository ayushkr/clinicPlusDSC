/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.entities.PharmacyBillRowEntity;
import in.srisrisri.clinic.exception.CustomException;
import in.srisrisri.clinic.entities.MedicineStockEntity;
import in.srisrisri.clinic.utils.FinanceUtils;
import java.math.BigDecimal;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author akr
 */
public class SumDAOForPharmacyBill {

    public boolean calculateTotals() throws Exception {
        String error = "";

        for (PharmacyBillRowEntity row : pharmacyBillRowEntitys) {
            if (row.getIdSpecial() == ConstantsInPharmacyBillRow.ROUNDOFF_ID_SPECIAL) {
                p("\t IdSpecial ={}", row.toString());
                rounderId = row.getId();
                p("\t rounderId={}", rounderId);
            }

            MedicineStockEntity stock = row.getMedicineStock();

            if (stock != null) {
                p("medicineStock={}", stock.getId());
                try {
                    sp = stock.getSellingPrice();

                    discount = stock.getDiscount();

                } catch (Exception e) {
                    p("\t--- calculateTotals, sp or discount is null");
                }

                qty = new BigDecimal(row.getQty() + "");
                p("\t calculateTotals, qty ={}", qty);

                BigDecimal amtWithoutDiscount = sp.multiply(qty);
                p("\t calculateTotals, amtWithoutDiscount ={}", amtWithoutDiscount);
                BigDecimal amtWithDiscount = BigDecimal.ZERO;
                if (discount != null) {
                    amtWithDiscount = sp.multiply(BigDecimal.ONE.subtract(discount)).multiply(qty);
                    p("\t calculateTotals, amtWithDiscount ={}", amtWithDiscount);
                } else {
                    amtWithDiscount = amtWithoutDiscount;
                }
                row.setAmount(amtWithDiscount);

                BigDecimal gstAmt = (amtWithoutDiscount.multiply(stock.getCgst().add(stock.getSgst())));
                p("\t calculateTotals, gstAmt ={}", gstAmt);

                stock.setGst(gstAmt);

                stock.setGst(BigDecimal.ZERO);

                {
                    BigDecimal mrp = stock.getMrp();
                    if (mrp == null) {
                        throw new CustomException("In medicine="
                                + stock.getMedicineBrandName().getBrandName()
                                + " with ID=" + stock.getId() + ", mrp is not set");
                    }
                    mrpTotal = mrpTotal.add(mrp);
                    gstTotal = gstTotal.add(stock.getGst());
                    amountTotal = amountTotal.add(row.getAmount());
                }

                stock.setSellingPrice(FinanceUtils.round(stock.getSellingPrice(), 2));
                stock.setGst(FinanceUtils.round(stock.getGst(), 2));
                row.setAmount(FinanceUtils.round(row.getAmount(), 2));

            } else {
                p("medicineStock is null");
            }

        }

        try {
            taxableAmount = FinanceUtils.round(amountTotal.subtract(gstTotal).divide(TWO), 2);
            p(" taxableAmount={} ", taxableAmount);

            freeOfCost = FinanceUtils.round(mrpTotal.subtract(amountTotal), 2);
            p(" freeOfCost={} ", freeOfCost);

            amountTotalRounded = amountTotal.add(roundMultiple(amountTotal, ONE));
            p(" amountTotalRounded={} ", amountTotalRounded);
            amountTotalWords = FinanceUtils.RsToWords(amountTotalRounded + "");
            p(" rounding ");
        } catch (Exception e) {
            p("exception in rounding e={}", e);

        }

        return true;
    }

    public BigDecimal roundMultiple(BigDecimal totalDirty, BigDecimal loss) {
        p("in roundMultiple()");
        BigDecimal[] divideAndRemainder = totalDirty.divideAndRemainder(loss);

        p("divideAndRemainder[0]=", divideAndRemainder[0]);
        p("divideAndRemainder[1]=", divideAndRemainder[1]);

        BigDecimal subtract = BigDecimal.ZERO.subtract(divideAndRemainder[1]);
        setRounded(subtract);

        try {
            if (rounderId != null) {
                PharmacyBillRowEntity pharmacyBillRowEntity = pharmacyBillRowRepo.findById(rounderId).get();
                pharmacyBillRowEntity.setAmount(subtract);
                PharmacyBillRowEntity saved = pharmacyBillRowRepo.save(pharmacyBillRowEntity);
                p("saved rounder entity=", saved.toString());
            } else {
                p("rounderId is null");
            }
        } catch (Exception e) {
            p("exp ={}", e.toString());
           
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

    public SumDAOForPharmacyBill(List<PharmacyBillRowEntity> pharmacyBillRowEntitys, PharmacyBillRowRepo pharmacyBillRowRepo) {
        this.pharmacyBillRowEntitys = pharmacyBillRowEntitys;
        this.pharmacyBillRowRepo = pharmacyBillRowRepo;
        p("SumDAO created ");
    }

    public List<PharmacyBillRowEntity> getPharmacyBillRowEntitys() {
        return pharmacyBillRowEntitys;
    }

    public void setPharmacyBillRowEntitys(List<PharmacyBillRowEntity> pharmacyBillRowEntitys) {
        this.pharmacyBillRowEntitys = pharmacyBillRowEntitys;
    }

    private void p(String str, Object o) {
        System.out.println(str + " --> " + o);
    }

    private void p(String str) {
        System.out.println(str + "");
    }

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
    Long rounderId = null;

    Long billId;
}
