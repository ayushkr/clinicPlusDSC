/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.titles;

import java.io.Serializable;
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

@Entity(name = "Titles") // this name will be used to name the Entity
@Table(name = "Titles2") // this name will be used to name a table in DB
public class Titles implements Serializable {

    
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    long id;
    
    @ColumnDefault(value = "'notSet'")
    String moduleName;
    
    @ColumnDefault(value = "true")
    boolean id2;
    
    @ColumnDefault(value = "true")
    boolean fixedId;
    
     @ColumnDefault(value = "true")
    boolean name;
     
    @ColumnDefault(value = "true")
    boolean fees;
     
    @ColumnDefault(value = "true")
    boolean department;
      
    @ColumnDefault(value = "true")
    boolean doctor;
    
    @ColumnDefault(value = "true")
    boolean patient;
       
    @ColumnDefault(value = "true")
    boolean age;
    
      @ColumnDefault(value = "0")
    String list[];

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    

    public boolean isId2() {
        return id2;
    }

    public void setId2(boolean id2) {
        this.id2 = id2;
    }

    public boolean isFixedId() {
        return fixedId;
    }

    public void setFixedId(boolean fixedId) {
        this.fixedId = fixedId;
    }

    public boolean isName() {
        return name;
    }

    public void setName(boolean name) {
        this.name = name;
    }

    public boolean isFees() {
        return fees;
    }

    public void setFees(boolean fees) {
        this.fees = fees;
    }

    public boolean isDepartment() {
        return department;
    }

    public void setDepartment(boolean department) {
        this.department = department;
    }

    public boolean isDoctor() {
        return doctor;
    }

    public void setDoctor(boolean doctor) {
        this.doctor = doctor;
    }

    public boolean isPatient() {
        return patient;
    }

    public void setPatient(boolean patient) {
        this.patient = patient;
    }

    public boolean isAge() {
        return age;
    }

    public void setAge(boolean age) {
        this.age = age;
    }

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }
    
    
    
    
    
}
   