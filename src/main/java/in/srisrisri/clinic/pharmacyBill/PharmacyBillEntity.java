package in.srisrisri.clinic.pharmacyBill;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.srisrisri.clinic.appointment.AppointmentEntity;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "PharmacyBill")
@Table(name = "PharmacyBill")
public class PharmacyBillEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    String remarks;
   
      @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfBill;

    
   
    @OneToOne
    AppointmentEntity appointment;

    public Date getDateOfBill() {
        return dateOfBill;
    }

    public void setDateOfBill(Date dateOfBill) {
        this.dateOfBill = dateOfBill;
    }

    

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

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }

  

}
