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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "PharmacyBill")
@Table(name = "PharmacyBill")
public class PharmacyBillEntity implements Serializable {

    @Id

    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

     @ColumnDefault(value = "0")
    Long id_manual;
    String remarks;

    @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfBill;

    @OneToOne
    AppointmentEntity appointment;

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updationTime;

    @ColumnDefault(value = "0")
    private int freshness;
    
   
    
    ///////////// methods /////////////

    public long getId_manual() {
        return id_manual;
    }

    public void setId_manual(long id_manual) {
        this.id_manual = id_manual;
    }

    public int getFreshness() {
        return freshness;
    }

    public void setFreshness(int freshness) {
        this.freshness = freshness;
    }

    public java.util.Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(java.util.Date creationTime) {
        this.creationTime = creationTime;
    }

    public java.util.Date getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(java.util.Date updationTime) {
        this.updationTime = updationTime;
    }

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
