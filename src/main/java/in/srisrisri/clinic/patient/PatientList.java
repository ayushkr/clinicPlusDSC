/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.patient;

import in.srisrisri.clinic.entities.PatientEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author akr
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientList {
    List<PatientEntity> patients;

    public PatientList() {
        patients=new ArrayList<>();
    }

    
    
    
    public List<PatientEntity> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientEntity> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        String res="";
        for(PatientEntity patientEntity: patients){
        res+=patientEntity.toString();
        }
        return res;
    }

    
    
}
