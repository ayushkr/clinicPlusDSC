/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.FileStorage;

import in.srisrisri.clinic.entities.AppointmentEntity;
import in.srisrisri.clinic.entities.DoctorEntity;
import in.srisrisri.clinic.entities.PatientEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author akr
 */
@Entity(name = "FileStore") // this name will be used to name the Entity
@Table(name = "FileStore") // this name will be used to name a table in DB
public class FileStoreEntity implements Serializable {
     @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    long id;
     
     String name;
    String title;
    String remarks;
    String pathToFile;
 @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
 @Temporal(TemporalType.TIMESTAMP)
    java.util.Date  updationTime;

    @OneToOne
    PatientEntity patientEntity;
    
    @OneToOne
    DoctorEntity doctorEntity;
    
    @OneToOne
    AppointmentEntity appointmentEntity;

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
    
    
    

    public DoctorEntity getDoctorEntity() {
        return doctorEntity;
    }

    public void setDoctorEntity(DoctorEntity doctorEntity) {
        this.doctorEntity = doctorEntity;
    }

    public AppointmentEntity getAppointmentEntity() {
        return appointmentEntity;
    }

    public void setAppointmentEntity(AppointmentEntity appointmentEntity) {
        this.appointmentEntity = appointmentEntity;
    }
    
    
    
    
    

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

   

    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   
}
