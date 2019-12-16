/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.jote;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ayush
 */
@Entity(name = "JoteEntity") // this name will be used to name the Entity
@Table(name = "JoteEntity") // this name will be used to name a table in DB
public class JoteEntity implements Serializable {

    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Id
    Long id;
    Long fxedId;
    String title;
    String category;
    String body;
    String status;
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date creationTime;
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Date updationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getFxedId() {
        return fxedId;
    }

    public void setFxedId(Long fxedId) {
        this.fxedId = fxedId;
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

    

}
