package in.srisrisri.clinic.appointment;

import in.srisrisri.clinic.doctor.*;
import in.srisrisri.clinic.patient.PatientEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity,Long> {

    public List<AppointmentEntity> findByDoctor(DoctorEntity doctorEntity,Sort sort);

 
//    @Query("select u from Patient u where LOWER(u.name) LIKE LOWER(CONCAT('%',?1, '%'))  order by name asc ")
//    public Page<PatientEntity> findAllByPatient(String filter, Pageable pageable);

    
  
//     @Query("SELECT p FROM Doctor p WHERE p.rid =:rid")
//    public Optional<DoctorEntity> findByRID(@Param("rid") Long rid);

 
    public Page<AppointmentEntity> findAllByPatient(PatientEntity patientEntity, Pageable pageable);
}


