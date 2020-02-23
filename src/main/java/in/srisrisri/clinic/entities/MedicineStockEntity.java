package in.srisrisri.clinic.entities;

import in.srisrisri.clinic.interfaces.Moduleable;
import in.srisrisri.clinic.entities.MedicineBrandNameEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "MedicineStock")
@Table(name = "MedicineStock")

public class MedicineStockEntity  implements Serializable,Moduleable {
 @Transient
    String module = "medicineStock";
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    @ColumnDefault(value = "0")
    private long idSpecial;

    @OneToOne
     private MedicineBrandNameEntity medicineBrandName;

    private String expiryDate;
    private String batch;

    // financial
    @ColumnDefault(value = "0")
    private BigDecimal mrp;

    // rate
    @ColumnDefault(value = "0")
    private BigDecimal pts;
    
     @ColumnDefault(value = "0")
    private long qtyFree;

    @ColumnDefault(value = "0")
    private long qtyPurchased;

    @ColumnDefault(value = "0")
   private  BigDecimal pts_times_qty;

    @Column(name = "discount_perc", nullable = false)
    @ColumnDefault(value = "0")
    private BigDecimal discount_perc;
    
    

    @Column(name = "discount", nullable = false)
    @ColumnDefault(value = "0")
    private BigDecimal discount;

    @ColumnDefault(value = "0")
    private BigDecimal taxableAmt;

    // GST
    @ColumnDefault(value = "0")
    private BigDecimal gst_perc;
    @ColumnDefault(value = "0")
    private BigDecimal cgst_perc;
    @ColumnDefault(value = "0")
    private BigDecimal sgst_perc;

    @ColumnDefault(value = "0")
     private BigDecimal gst;
    @ColumnDefault(value = "0")
     private BigDecimal cgst;
    @ColumnDefault(value = "0")
    private BigDecimal sgst;

    @ColumnDefault(value = "0")
    private BigDecimal taxAmountWO_Cess;

    @ColumnDefault(value = "0")
   private  BigDecimal totalwocess;

    @ColumnDefault(value = "0")
    private BigDecimal cess_perc;

    @ColumnDefault(value = "0")
     private BigDecimal amountTotal;
    // dsc price

    @ColumnDefault(value = "0")
     private BigDecimal costPrice;
    @ColumnDefault(value = "0")
    BigDecimal sellingPrice;

//    @Transient
    private long qtyRemaining;
    @Column(name = "sub_count", nullable = false)
    long subCount;

    @OneToOne
    PurchaseBillEntity purchaseBill;

    

    BigDecimal HUNDRED = BigDecimal.valueOf(100);
    
      @ColumnDefault(value = "0")
     private int slno_in_bill;
      
      
    //   common 
     @ColumnDefault(value = "0")
    long fixedId;
     @ColumnDefault(value = "0")
    long bookId;
    
      @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updationTime;
    
    @Transient
    boolean selected = false;
    @ColumnDefault(value = "0")
     long id_of_editor;
  
    String remarks;  
      
      

    ////////////////////////////////////////////////////////////////
    // special
    public BigDecimal getSellingPrice() {
       
        return sellingPrice;
    }

    public long getQtyRemaining() {
        return qtyRemaining;
    }

    public void setQtyRemaining(long qtyRemaining) {
        this.qtyRemaining = qtyRemaining;
    }

    public BigDecimal getPts_times_qty() {

        try {
//            pts_times_qty = pts.multiply(new BigDecimal(qtyPurchased));
        } catch (Exception e) {

        }
        return pts_times_qty;
    }

    public BigDecimal getTaxableAmt() {

        try {
//            BigDecimal discFac = BigDecimal.ONE.subtract(discount_perc.divide(HUNDRED));
//            taxableAmt = getPts_times_qty().multiply(discFac);
        } catch (Exception e) {

        }
        return taxableAmt;
    }

    public BigDecimal getTaxAmountWO_Cess() {
        try {

//            taxAmountWO_Cess = getTaxableAmt().multiply(getGst_perc().divide(HUNDRED));
        } catch (Exception e) {

        }
        return taxAmountWO_Cess;
    }

    public BigDecimal getTotalwocess() {

        try {

//            totalwocess = getTaxAmountWO_Cess().add(getTaxableAmt());
        } catch (Exception e) {

        }
        return totalwocess;
    }

    public BigDecimal getAmountTotal() {
        try {

//            amountTotal = getTotalwocess().add((getTaxAmountWO_Cess().multiply(cess_perc.divide(HUNDRED))));
        } catch (Exception e) {

        }
        return amountTotal;
    }

    public BigDecimal getCostPrice() {
        try {

//            costPrice = getAmountTotal().divide(BigDecimal.valueOf(getQtyPurchased()));

        } catch (Exception e) {

        }
        return costPrice;
    }

    // end of specials
    /////////////////////////////////////////////////////////////////
    public void setTaxAmountWO_Cess(BigDecimal taxAmountWO_Cess) {
        this.taxAmountWO_Cess = taxAmountWO_Cess;
    }

    public void setPts_times_qty(BigDecimal pts_times_qty) {
        this.pts_times_qty = pts_times_qty;
    }

    public BigDecimal getDiscount_perc() {
        return discount_perc;
    }

    public void setDiscount_perc(BigDecimal discount_perc) {
        this.discount_perc = discount_perc;
    }

    public void setTaxableAmt(BigDecimal taxableAmt) {
        this.taxableAmt = taxableAmt;
    }

    public BigDecimal getGst_perc() {
        return gst_perc;
    }

    public void setGst_perc(BigDecimal gst_perc) {
        this.gst_perc = gst_perc;
    }

    public BigDecimal getCgst_perc() {
        return cgst_perc;
    }

    public void setCgst_perc(BigDecimal cgst_perc) {
        this.cgst_perc = cgst_perc;
    }

    public BigDecimal getSgst_perc() {
        return sgst_perc;
    }

    public void setSgst_perc(BigDecimal sgst_perc) {
        this.sgst_perc = sgst_perc;
    }

    public void setTotalwocess(BigDecimal totalwocess) {
        this.totalwocess = totalwocess;
    }

    public BigDecimal getCess_perc() {
        return cess_perc;
    }

    public void setCess_perc(BigDecimal cess_perc) {
        this.cess_perc = cess_perc;
    }

    public BigDecimal getPts() {
        return pts;
    }

    public void setPts(BigDecimal pts) {
        this.pts = pts;
    }

  

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public long getIdSpecial() {
        return idSpecial;
    }

    public void setIdSpecial(long idSpecial) {
        this.idSpecial = idSpecial;
    }

  

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MedicineBrandNameEntity getMedicineBrandName() {
        return medicineBrandName;
    }

    public void setMedicineBrandName(MedicineBrandNameEntity medicineBrandName) {
        this.medicineBrandName = medicineBrandName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public BigDecimal getGst() {
        return gst;
    }

    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public long getQtyPurchased() {
        return qtyPurchased;
    }

    public void setQtyPurchased(long qtyPurchased) {
        this.qtyPurchased = qtyPurchased;
    }

    public long getSubCount() {
        return subCount;
    }

    public void setSubCount(long subCount) {
        this.subCount = subCount;
    }

    public PurchaseBillEntity getPurchaseBill() {
        return purchaseBill;
    }

    public void setPurchaseBill(PurchaseBillEntity purchaseBill) {
        this.purchaseBill = purchaseBill;
    }

    public long getQtyFree() {
        return qtyFree;
    }

    public void setQtyFree(long qtyFree) {
        this.qtyFree = qtyFree;
    }

    public int getSlno_in_bill() {
        return slno_in_bill;
    }

    public void setSlno_in_bill(int slno_in_bill) {
        this.slno_in_bill = slno_in_bill;
    }

    public BigDecimal getHUNDRED() {
        return HUNDRED;
    }

    public void setHUNDRED(BigDecimal HUNDRED) {
        this.HUNDRED = HUNDRED;
    }

    public long getFixedId() {
        return fixedId;
    }

    public void setFixedId(long fixedId) {
        this.fixedId = fixedId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(Date updationTime) {
        this.updationTime = updationTime;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getId_of_editor() {
        return id_of_editor;
    }

    public void setId_of_editor(long id_of_editor) {
        this.id_of_editor = id_of_editor;
    }

    

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String getModule() {
    return module;
    }
    

}
