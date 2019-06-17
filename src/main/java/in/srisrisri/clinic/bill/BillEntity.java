package in.srisrisri.clinic.bill;

import in.srisrisri.clinic.pharmacyBill.PharmacyBillEntity;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "Bill")
@Entity
public class BillEntity implements Serializable {

    @Id
    @GeneratedValue
    long id;

    String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

  

}
