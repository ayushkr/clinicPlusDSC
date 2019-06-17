package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.Vendor.VendorEntity;
import in.srisrisri.clinic.medicineBrandName.MedicineBrandNameEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    Float gst;
    Float costPrice;
    Float costPricePerSubCount;
    Float sellingPrice;
    Float rate;
    Float mrp;
    String batch;
    String billNo;
    
    @Column(name = "discount", nullable = false)
    float discount=0;

    private String dateOfPurchase;
    private long qtyPurchased;
    private long qtyRemaining;
    @Column(name = "sub_count", nullable = false)
    long subCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
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

    public Float getCostPricePerSubCount() {
        return costPricePerSubCount;
    }

    public void setCostPricePerSubCount(Float costPricePerSubCount) {
        this.costPricePerSubCount = costPricePerSubCount;
    }

    public long getSubCount() {
        return subCount;
    }

    public void setSubCount(long subCount) {
        this.subCount = subCount;
    }

    public Float getGst() {
        return gst;
    }

    public void setGst(Float gst) {
        this.gst = gst;
    }

    public Float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Float costPrice) {
        this.costPrice = costPrice;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Float getMrp() {
        return mrp;
    }

    public void setMrp(Float mrp) {
        this.mrp = mrp;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public long getQtyRemaining() {
        return qtyRemaining;
    }

    public void setQtyRemaining(long qtyRemaining) {
        this.qtyRemaining = qtyRemaining;
    }

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "MedicineStockEntity{" + "id=" + id + ", medicineBrandName=" + medicineBrandName + ", vendor=" + vendor + ", expiryDate=" + expiryDate + ", gst=" + gst + ", costPrice=" + costPrice + ", costPricePerSubCount=" + costPricePerSubCount + ", sellingPrice=" + sellingPrice + ", mrp=" + mrp + ", batch=" + batch + ", discount=" + discount + ", dateOfPurchase=" + dateOfPurchase + ", qtyPurchased=" + qtyPurchased + ", qtyRemaining=" + qtyRemaining + ", subCount=" + subCount + '}';
    }

}
