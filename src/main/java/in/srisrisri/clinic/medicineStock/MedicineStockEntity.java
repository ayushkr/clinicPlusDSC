package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.Vendor.VendorEntity;
import in.srisrisri.clinic.medicineBrandName.MedicineBrandNameEntity;
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

@Entity(name = "MedicineStock")
@Table(name = "MedicineStock")

public class MedicineStockEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    MedicineBrandNameEntity medicineBrandName;
    @OneToOne
    VendorEntity vendor;
    private String expiryDate;
    BigDecimal cgst;
    BigDecimal sgst;
    BigDecimal gst;
    @ColumnDefault(value = "0")
    BigDecimal costPrice;
    @ColumnDefault(value = "0")
    BigDecimal sellingPrice;
    @ColumnDefault(value = "0")
    BigDecimal mrp;

    String batch;
    String billNo;
    String cash;

    @Column(name = "discount", nullable = false)
    @ColumnDefault(value = "0")
    BigDecimal discount;

    private String dateOfPurchase;
    private long qtyPurchased;
//    @Transient
    private long qtyRemaining;
    @Column(name = "sub_count", nullable = false)
    long subCount;
    
     @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
 @Temporal(TemporalType.TIMESTAMP)
    java.util.Date  updationTime;

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

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
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

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
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

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
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

    @Override
    public String toString() {
        return "MedicineStockEntity{" + "id=" + id + ", medicineBrandName=" + medicineBrandName + ", vendor=" + vendor + ", expiryDate=" + expiryDate + ", cgst=" + cgst + ", sgst=" + sgst + ", gst=" + gst + ", costPrice=" + costPrice + ", sellingPrice=" + sellingPrice + ", batch=" + batch + ", billNo=" + billNo + ", cash=" + cash + ", discount=" + discount + ", dateOfPurchase=" + dateOfPurchase + ", qtyPurchased=" + qtyPurchased + ", qtyRemaining=" + qtyRemaining + ", subCount=" + subCount + '}';
    }

}
