package in.srisrisri.clinic.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.srisrisri.clinic.entities.AppointmentEntity;
import in.srisrisri.clinic.interfaces.Moduleable;

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

@Entity(name = "PharmacyBill")
@Table(name = "PharmacyBill")
public class PharmacyBillEntity implements Serializable,Moduleable {
  @Transient
    final String module="pharmacyBill";
  
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

     

    @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfBill;

    @OneToOne
    AppointmentEntity appointment;

    

    @ColumnDefault(value = "0")
    private int freshness;

    @Transient
     @ColumnDefault(value = "true")
    private boolean showBillMetaData;
    
    
    
    /////////////// common to all fields////////
  
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
     @ColumnDefault(value = "0")
     long id_display;
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

    public long getId_display() {
        return id_display;
    }

    public void setId_display(long id_display) {
        this.id_display = id_display;
    }

    public String getRemarks() {
        return remarks;
    }

    /////////////// common to all methods////////
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    ///////////// other methods /////////////
    public int getFreshness() {
        return freshness;
    }

    public void setFreshness(int freshness) {
        this.freshness = freshness;
    }

   

    public Date getDateOfBill() {
        return dateOfBill;
    }

    public void setDateOfBill(Date dateOfBill) {
        this.dateOfBill = dateOfBill;
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

  
    
    @Override
    public String getModule() {
    return module;
    }

    public boolean isShowBillMetaData() {
        return showBillMetaData;
    }

    public void setShowBillMetaData(boolean showBillMetaData) {
        this.showBillMetaData = showBillMetaData;
    }

    @Override
    public String toString() {
        return "PharmacyBillEntity{" + "module=" + module + ", id=" + id + ", dateOfBill=" + dateOfBill + ", appointment=" + appointment + ", freshness=" + freshness + ", showBillMetaData=" + showBillMetaData + '}';
    }

   
    
    
}
