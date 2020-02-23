/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.entities;

/**
 *
 * @author akr2
 */
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.srisrisri.clinic.Vendor.VendorEntity;
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

@Entity(name = "PurchaseBill")
@Table(name = "purchase_bill")
public class PurchaseBillEntity  implements Serializable,Moduleable {
 
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    long id;
    

    
    @OneToOne
    VendorEntity vendor;
     @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfPurchase;
    String billNo;
    String cash;
     @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfBill;
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
   

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

  

    public Date getDateOfBill() {
        return dateOfBill;
    }

    public void setDateOfBill(Date dateOfBill) {
        this.dateOfBill = dateOfBill;
    }

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
    
    
    


   

 
       @Transient
    String module="purchaseBill";
    @Override
    public String getModule() {
    return module;
    }

    
   

    
}
