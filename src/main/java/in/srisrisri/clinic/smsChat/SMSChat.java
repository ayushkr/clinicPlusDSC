/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author akr2
 */
@Entity(name = "smschat")
@Table(name = "smschat")
public class SMSChat implements Serializable {

    public static final int DOCTORS = 1, PATIENTS = 2, OTHERS = 3;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    long id; // for use     
    Date creationTime;
    Date updationTime;

    String title;
     @Column(length = 320)
    String body;
     @Column(length = 320)
    String remarks;
    int[] toPhoneNumbers;
    int[] toPhoneNumbers_messageStatus;
    @Column(length = 320)
    String report;

//    @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfSending;

    @ColumnDefault("true")
    boolean draft;
    @ColumnDefault("0")
    int status;
    
     @Transient
     String [] statusNames;
    
    @ColumnDefault("false")
    boolean processed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int[] getToPhoneNumbers() {
        return toPhoneNumbers;
    }

    public void setToPhoneNumbers(int[] toPhoneNumbers) {
        this.toPhoneNumbers = toPhoneNumbers;
    }

    public int[] getToPhoneNumbers_messageStatus() {
        return toPhoneNumbers_messageStatus;
    }

    public void setToPhoneNumbers_messageStatus(int[] toPhoneNumbers_messageStatus) {
        this.toPhoneNumbers_messageStatus = toPhoneNumbers_messageStatus;
    }

    public Date getDateOfSending() {
        return dateOfSending;
    }

    public void setDateOfSending(Date dateOfSending) {
        this.dateOfSending = dateOfSending;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

 

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

   
    public String[] getStatusNames() {
        return SMS_STATUS.statusNames;
    }

    public void setStatusNames(String[] statusNames) {
        this.statusNames = statusNames;
    }

    
    
    
    
    

    @Override
    public String toString() {
        return "SMSChat{" + "id=" + id + ", creationTime=" + creationTime + ", updationTime=" + updationTime + ", title=" + title + ", body=" + body + ", remarks=" + remarks + ", toPhoneNumbers=" + toPhoneNumbers + ", toPhoneNumbers_messageStatus=" + toPhoneNumbers_messageStatus + ", report=" + report + ", dateOfSending=" + dateOfSending + ", draft=" + draft + ", status=" + status + ", processed=" + processed + '}';
    }
    
    

}
