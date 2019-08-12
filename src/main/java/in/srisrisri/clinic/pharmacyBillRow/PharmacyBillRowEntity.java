package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.medicineStock.MedicineStockEntity;
import in.srisrisri.clinic.pharmacyBill.PharmacyBillEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "PharmacyBillRow")
@Table(name = "PharmacyBillRow")
public class PharmacyBillRowEntity implements Serializable {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator") 
    private long id;

    @OneToOne
    MedicineStockEntity medicineStock;
    @OneToOne
    PharmacyBillEntity pharmacyBill;
    Integer qty;//	
    BigDecimal discount;
//     BigDecimal amount;//
    BigDecimal amount;//

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updationTime;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PharmacyBillEntity getPharmacyBill() {
        return pharmacyBill;
    }

    public void setPharmacyBill(PharmacyBillEntity pharmacyBill) {
        this.pharmacyBill = pharmacyBill;
    }

    public MedicineStockEntity getMedicineStock() {
        return medicineStock;
    }

    public void setMedicineStock(MedicineStockEntity medicineStock) {
        this.medicineStock = medicineStock;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PharmacyBillRowEntity{" + "id=" + id + ", medicineStock=" + medicineStock + ", pharmacyBill=" + pharmacyBill + ", qty=" + qty + ", discount=" + discount + ", amount=" + amount + '}';
    }

}
