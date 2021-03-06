package in.srisrisri.clinic.Vendor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity(name = "vendor")
@Table(name = "vendor")
public class VendorEntity implements Serializable  {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator") 
    @Column(name = "id", nullable = false)
    private long id;
    
    String name;
    String place;
    String description;
    String address;
    String gstin;
    String contactPhone;
      String pinCode;
   String email;
     String dlNo;
    String ssid;
    
   
    @Column(name="lastTouched", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date lastTouched; 

     @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
 @Temporal(TemporalType.TIMESTAMP)
    java.util.Date  updationTime;

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

    public String getDlNo() {
        return dlNo;
    }

    public void setDlNo(String dlNo) {
        this.dlNo = dlNo;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

   

   


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastTouched() {
        return lastTouched;
    }

    public void setLastTouched(Date lastTouched) {
        this.lastTouched = lastTouched;
    }

    @Override
    public String toString() {
        return "VendorEntity{" + "id=" + id + ", name=" + name + ", place=" + place + ", description=" + description + ", address=" + address + ", gstin=" + gstin + ", contactPhone=" + contactPhone + ", pinCode=" + pinCode + ", email=" + email + ", dlNo=" + dlNo + ", ssid=" + ssid + ", lastTouched=" + lastTouched + '}';
    }

   
    
    
}