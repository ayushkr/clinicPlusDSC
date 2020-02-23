/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.appointment;

import in.srisrisri.clinic.entities.AppointmentEntity;
import java.util.List;

/**
 *
 * @author akr
 */
public class ReportIncomeFromDoctorsDTO {
    List<AppointmentEntity> appointmentEntitys;
Long doctorId;
    float totalConsultFee;
    float totalFeeForClinic;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public ReportIncomeFromDoctorsDTO(List<AppointmentEntity> appointmentEntitys) {
        this.appointmentEntitys = appointmentEntitys;
    }

    public List<AppointmentEntity> getAppointmentEntitys() {
        return appointmentEntitys;
    }

    public void setAppointmentEntitys(List<AppointmentEntity> appointmentEntitys) {
        this.appointmentEntitys = appointmentEntitys;
    }

    public float getTotalConsultFee() {
        return totalConsultFee;
    }

    public void setTotalConsultFee(float totalConsultFee) {
        this.totalConsultFee = totalConsultFee;
    }

    public float getTotalFeeForClinic() {
        return totalFeeForClinic;
    }

    public void setTotalFeeForClinic(float totalFeeForClinic) {
        this.totalFeeForClinic = totalFeeForClinic;
    }
    
    
    
    
 
    
    
    public void calculateTotal(){
        for(AppointmentEntity appointmentEntity : appointmentEntitys){
        totalConsultFee+=appointmentEntity.getConsultFee();
       totalFeeForClinic+=appointmentEntity.getFeeForClinic();
        }
    }
    
}
