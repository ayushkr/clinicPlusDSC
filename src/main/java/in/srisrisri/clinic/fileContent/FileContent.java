package in.srisrisri.clinic.fileContent;

import in.srisrisri.clinic.entities.AppointmentEntity;
import in.srisrisri.clinic.entities.DoctorEntity;
import in.srisrisri.clinic.entities.PatientEntity;
import in.srisrisri.clinic.user.UserEntity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

@Entity
public class FileContent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;

    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updationTime;

    private String description;
    private String contentType;
    private String publicityLevel;
    private UserEntity uploader;
    
    @OneToOne
    private DoctorEntity  doctor;
    @OneToOne
    private PatientEntity patient;
    @OneToOne
    private AppointmentEntity appointment;
    
    

    @ContentId
    private String contentId;
    @ContentLength
    private long contentLength;
    private String mimeType = "text/plain";
     private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }

    public String getContentType() {
        return getMimeType().split("/")[0];
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(Date updationTime) {
        this.updationTime = updationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String toString() {
        return "FileContent{" + "id=" + id + ", creationTime=" + creationTime + ", updationTime=" + updationTime + ", description=" + description + ", contentType=" + contentType + ", doctor=" + doctor + ", patient=" + patient + ", appointment=" + appointment + ", contentId=" + contentId + ", contentLength=" + contentLength + ", mimeType=" + mimeType + ", type=" + type + '}';
    }
    
    
}
