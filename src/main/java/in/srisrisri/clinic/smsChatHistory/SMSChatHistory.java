/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChatHistory;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.srisrisri.clinic.smsChat.SMSChat;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author akr2
 */
@Entity(name = "sms_chat_history")
@Table(name = "sms_chat_history")
public class SMSChatHistory implements Serializable {

    public static final int SEND_STATUS_WAITING = 1, SEND_STATUS_FAILED = 2, SEND_STATUS_OK = 3;
    public static final int DOCTORS = 1, PATIENTS = 2, OTHERS = 3;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    long id; // for use     
    Date creationTime;
    Date updationTime;

    String contactPersonType;
    String contactName;
    String contactNumber;

    
   @Column(length = 320)
    String remarks;
  

//    @JsonDeserialize(using = in.srisrisri.clinic.utils.DateHandler.class)
    Date dateOfSending;

   
    @ColumnDefault(value = "0")
    int sentStatus;
    @Transient
    String [] sentStatusNames={"","WAITING", "FAILED", "OK","4Notset"};
    
    @OneToOne
    SMSChat sMSChat;

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

   

    public Date getDateOfSending() {
        return dateOfSending;
    }

    public void setDateOfSending(Date dateOfSending) {
        this.dateOfSending = dateOfSending;
    }

   

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(int sentStatus) {
        this.sentStatus = sentStatus;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactPersonType() {
        return contactPersonType;
    }

    public void setContactPersonType(String contactPersonType) {
        this.contactPersonType = contactPersonType;
    }

    @Override
    public String toString() {
        return "SMSChatHistory{" + "id=" + id + ", creationTime=" + creationTime + ", updationTime=" + updationTime + ", contactPersonType=" + contactPersonType + ", contactName=" + contactName + ", contactNumber=" + contactNumber + ", remarks=" + remarks + ", dateOfSending=" + dateOfSending + ", sentStatus=" + sentStatus + ", sentStatusNames=" + sentStatusNames + ", sMSChat=" + sMSChat + '}';
    }

  


    

    public String[] getSentStatusNames() {
        return sentStatusNames;
    }

    public void setSentStatusNames(String[] sentStatusNames) {
        this.sentStatusNames = sentStatusNames;
    }

    public SMSChat getsMSChat() {
        return sMSChat;
    }

    public void setsMSChat(SMSChat sMSChat) {
        this.sMSChat = sMSChat;
    }

    
    
}
