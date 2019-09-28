package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.Vendor.VendorEntity;
import in.srisrisri.clinic.medicineBrandName.MedicineBrandNameEntity;
import in.srisrisri.clinic.purchaseBill.PurchaseBillEntity;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "MedicineStock")
@Table(name = "MedicineStock")

public class MedicineStockEntity implements Serializable {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

      @ColumnDefault(value = "0")
    private long idSpecial;
    
    @OneToOne
    MedicineBrandNameEntity medicineBrandName;

    private String expiryDate;
    String batch;
    BigDecimal cgst;
    BigDecimal sgst;
    BigDecimal gst;
    @ColumnDefault(value = "0")
    BigDecimal costPrice;
    @ColumnDefault(value = "0")
    BigDecimal sellingPrice;
    @ColumnDefault(value = "0")
    BigDecimal mrp;
    
      @ColumnDefault(value = "0")
    BigDecimal pts;

    @Column(name = "discount", nullable = false)
    @ColumnDefault(value = "0")
    BigDecimal discount;

    private long qtyPurchased;
//    @Transient
    private long qtyRemaining;
    @Column(name = "sub_count", nullable = false)
    long subCount;
    
    @OneToOne
    PurchaseBillEntity purchaseBill;
    
    @ColumnDefault(value = "0")
    BigDecimal amountTotal;

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updationTime;
    
    String remarks;

    ////////////////////////////////////////////////////////////////
    // special
    public BigDecimal getSellingPrice() {
        if (sellingPrice == null) {
            return getMrp();
        } else {
            return sellingPrice;
        }
    }

    public long getQtyRemaining() {
        return qtyRemaining;
    }

    public void setQtyRemaining(long qtyRemaining) {
        this.qtyRemaining = qtyRemaining;
    }

    // end of specials
    /////////////////////////////////////////////////////////////////

    public BigDecimal getPts() {
        return pts;
    }

    public void setPts(BigDecimal pts) {
        this.pts = pts;
    }

    
    
    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    
    
    public BigDecimal getAmountTotal() {
        return amountTotal;
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

    public BigDecimal getCostPrice() {
        return costPrice;
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

   

    

}
