package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.Vendor.VendorEntity;
import in.srisrisri.clinic.medicineBrandName.MedicineBrandNameEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    BigDecimal costPrice;
    BigDecimal sellingPrice;
    
    @Transient
    BigDecimal rate;
    BigDecimal mrp;
    String batch;
    String billNo;
    
    @Column(name = "discount", nullable = false)
      BigDecimal discount;

    private String dateOfPurchase;
    private long qtyPurchased;
    @Transient
    private long qtyRemaining;
    @Column(name = "sub_count", nullable = false)
    long subCount;

    ////////////////////////////////////////////////////////////////
    // special
     public BigDecimal getRate() {
        return getMrp();
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    
    public long getQtyRemaining() {
        return qtyRemaining;
    }

    public void setQtyRemaining(long qtyRemaining) {
        this.qtyRemaining = qtyRemaining;
    }
    
    // end of specials
    /////////////////////////////////////////////////////////////////
    
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

    

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

   

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp;
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
        return "MedicineStockEntity{" + "id=" + id + ", medicineBrandName=" + medicineBrandName + ", vendor=" + vendor + ", expiryDate=" + expiryDate + ", cgst=" + cgst + ", sgst=" + sgst + ", gst=" + gst + ", costPrice=" + costPrice + ", sellingPrice=" + sellingPrice + ", rate=" + rate + ", mrp=" + mrp + ", batch=" + batch + ", billNo=" + billNo + ", discount=" + discount + ", dateOfPurchase=" + dateOfPurchase + ", qtyPurchased=" + qtyPurchased + ", qtyRemaining=" + qtyRemaining + ", subCount=" + subCount + '}';
    }

  

 }

 

   
