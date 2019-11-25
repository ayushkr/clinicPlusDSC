/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    String body;
    String remarks;
    int[] toPhoneNumbers;
    int[] toPhoneNumbers_messageStatus;
    String report;

    @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfSending;

    @ColumnDefault("true")
    boolean draft;
    @ColumnDefault("0")
    int sentStatus;

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

    public int getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(int sentStatus) {
        this.sentStatus = sentStatus;
    }

}
