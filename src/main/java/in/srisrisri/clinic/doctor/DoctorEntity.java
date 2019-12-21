package in.srisrisri.clinic.doctor;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Doctor") // this name will be used to name the Entity
@Table(name = "Doctor") // this name will be used to name a table in DB
public class DoctorEntity implements Serializable {

    

    long id_of_editor;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    long id;
    @ColumnDefault(value = "0")
    long fixedId;
    String name;
    String department;
    int fees;
    String feesList;
    String address;
    String description;
     String remediesFor;
    String contactPhone;

    int feeForClinic;
    String doctor_code;
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
 @Temporal(TemporalType.TIMESTAMP)
    java.util.Date  updationTime;
    Date dateOfJoining;
    String visitDay;
    String visitTime;
    String email;
    long id_display;
    String remarks;
    String profileImage;
     String sex;
    
   String sun;
   String mon;
   String tue;
   String wed;
   String thu;
   String fri;
   String sat;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

   
   
   
   
    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

   
   
   
    public long getId_of_editor() {
        return id_of_editor;
    }

    public void setId_of_editor(long id_of_editor) {
        this.id_of_editor = id_of_editor;
    }

    public long getId() {
        return id;
    }

    public DoctorEntity setId(long id) {
        this.id = id;
        return this;
    }

    public long getFixedId() {
        return fixedId;
    }

    public void setFixedId(long fixedId) {
        this.fixedId = fixedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public String getFeesList() {
        return feesList;
    }

    public void setFeesList(String feesList) {
        this.feesList = feesList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemediesFor() {
        return remediesFor;
    }

    public void setRemediesFor(String remediesFor) {
        this.remediesFor = remediesFor;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public int getFeeForClinic() {
        return feeForClinic;
    }

    public void setFeeForClinic(int feeForClinic) {
        this.feeForClinic = feeForClinic;
    }

    public String getDoctor_code() {
        return doctor_code;
    }

    public void setDoctor_code(String doctor_code) {
        this.doctor_code = doctor_code;
    }

    public  java.util.Date  getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public  java.util.Date  getUpdationTime() {
        return updationTime;
    }

    public void setUpdationTime(Date updationTime) {
        this.updationTime = updationTime;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getVisitDay() {
        return visitDay;
    }

    public void setVisitDay(String visitDay) {
        this.visitDay = visitDay;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId_display() {
        return id_display;
    }

    public void setId_display(long id_display) {
        this.id_display = id_display;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "DoctorEntity{" + "id_of_editor=" + id_of_editor + ", id=" + id + ", fixedId=" + fixedId + ", name=" + name + ", department=" + department + ", fees=" + fees + ", feesList=" + feesList + ", address=" + address + ", description=" + description + ", remediesFor=" + remediesFor + ", contactPhone=" + contactPhone + ", feeForClinic=" + feeForClinic + ", doctor_code=" + doctor_code + ", creationTime=" + creationTime + ", updationTime=" + updationTime + ", dateOfJoining=" + dateOfJoining + ", visitDay=" + visitDay + ", visitTime=" + visitTime + ", email=" + email + ", id_display=" + id_display + ", remarks=" + remarks + ", profileImage=" + profileImage + ", sex=" + sex + ", sun=" + sun + ", mon=" + mon + ", tue=" + tue + ", wed=" + wed + ", thu=" + thu + ", fri=" + fri + ", sat=" + sat + '}';
    }

  

   

}
