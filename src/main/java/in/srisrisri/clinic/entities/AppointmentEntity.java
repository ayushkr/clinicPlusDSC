package in.srisrisri.clinic.entities;

//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.srisrisri.clinic.appointmentStatus.AppointmentStatusEntity;
import in.srisrisri.clinic.appointmentType.AppointmentTypeEntity;
import in.srisrisri.clinic.entities.DoctorEntity;
import in.srisrisri.clinic.interfaces.Moduleable;
import in.srisrisri.clinic.utils.FinanceUtils;
import java.io.Serializable;
import java.sql.Date;
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

@Entity(name = "Appointment") // this name will be used to name the Entity
@Table(name = "Appointment") // this name will be used to name a table in DB
public class AppointmentEntity   implements Serializable,Moduleable {

    @Transient
    String module="appointment";
    
    
    long rid;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    long id;
    @OneToOne
    DoctorEntity doctor;
    @OneToOne
    PatientEntity patient;
//     @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfAppointment;
    @ColumnDefault(value = "0")
    int tokenNumber;
    @ColumnDefault(value = "0")
    int consultFee;
    @ColumnDefault(value = "0")
    int feeForClinic;
    @OneToOne
    AppointmentTypeEntity appointmentTypeEntity;
    @OneToOne
    AppointmentStatusEntity appointmentStatusEntity;
   
    @Transient
    String totalInWords="not set";
    
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
    
    
    
    
    
    
    

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public long getId() {
        return id;
    }

    public AppointmentEntity setId(long id) {
        this.id = id;
        return this;
    }

   

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public int getConsultFee() {
        return consultFee;
    }

    public void setConsultFee(int consultFee) {
        this.consultFee = consultFee;
    }

    public int getFeeForClinic() {
        return feeForClinic;
    }

    public void setFeeForClinic(int feeForClinic) {
        this.feeForClinic = feeForClinic;
    }

    public AppointmentTypeEntity getAppointmentTypeEntity() {
        return appointmentTypeEntity;
    }

    public void setAppointmentTypeEntity(AppointmentTypeEntity appointmentTypeEntity) {
        this.appointmentTypeEntity = appointmentTypeEntity;
    }

    public AppointmentStatusEntity getAppointmentStatusEntity() {
        return appointmentStatusEntity;
    }

    public void setAppointmentStatusEntity(AppointmentStatusEntity appointmentStatusEntity) {
        this.appointmentStatusEntity = appointmentStatusEntity;
    }

   


    public String getTotalInWords() {
        return FinanceUtils.RsToWords((getConsultFee()+getFeeForClinic())+"");
    }

    public void setTotalInWords(String totalInWords) {
        this.totalInWords = totalInWords;
    }

    
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    
    
    
    @Override
    public String getModule() {
    return module;
            }

   
}
