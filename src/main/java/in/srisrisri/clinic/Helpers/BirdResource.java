/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.Helpers;

import in.srisrisri.clinic.entities.AppointmentEntity;
import in.srisrisri.clinic.doctor.DoctorRepo;
import in.srisrisri.clinic.patient.PatientRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akr2
 */
@RestController
@RequestMapping("/clinicPlus/api/bird")
public class BirdResource extends Resource{
 
 @Autowired
    public BirdResource(PatientRepo repo) {
       this.repo= repo;
    }
    
    
    
}
