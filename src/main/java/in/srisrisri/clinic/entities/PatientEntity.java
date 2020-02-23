package in.srisrisri.clinic.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import in.srisrisri.clinic.scheduler.Contactable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Patient")
@Table(name = "Patient")
public class PatientEntity  implements Serializable, Contactable {

    @Transient
     String module = "patient";
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    long id; // for use     
    
    String name;
    String age;
    String dob;
    String sex;
    String place;
    String contactPhone;
    String address;
    String notes;
    String contactEmergency;
//    @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfRegistration;
    String email;
   
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    
    
    

    public long getId() {
        return id;
    }

    public PatientEntity setId(long id) {
        this.id = id;
        return this;
    }


 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContactEmergency() {
        return contactEmergency;
    }

    public void setContactEmergency(String contactEmergency) {
        this.contactEmergency = contactEmergency;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   
    
    @Override
    public String getModule() {
    return module;
    }

    

}
