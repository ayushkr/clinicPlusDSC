package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.entities.PharmacyBillRowEntity;
import in.srisrisri.clinic.entities.DoctorEntity;
import in.srisrisri.clinic.entities.PatientEntity;
import in.srisrisri.clinic.entities.PharmacyBillEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PharmacyBillRowRepo extends JpaRepository<PharmacyBillRowEntity,Long>{

public List<PharmacyBillRowEntity> findByPharmacyBill(PharmacyBillEntity pharmacyBillEntity);


   @Query("select u from PharmacyBillRow u where  u.pharmacyBill.appointment.patient=?1")
  public Page<PharmacyBillRowEntity> findAllByPatient(PatientEntity patientEntity, Pageable pageable);

    @Query("select u from PharmacyBillRow u where  u.pharmacyBill.appointment.doctor=?1")
    public Page<PharmacyBillRowEntity> findAllByDoctor(DoctorEntity doctorEntity, Pageable pageable);

}