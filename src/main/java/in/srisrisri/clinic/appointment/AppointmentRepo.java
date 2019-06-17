package in.srisrisri.clinic.appointment;

import in.srisrisri.clinic.doctor.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity,Long> {

    public List<AppointmentEntity> findByDoctor(DoctorEntity doctorEntity,Sort sort);

 
    
    
    
//
// @Query("SELECT p FROM Doctor p WHERE LOWER(p.name) = LOWER(:lastName)")
//    public Optional<DoctorEntity> findLastName(@Param("lastName") String lastName);
//
//    @Query("SELECT p FROM Doctor p WHERE p.id =:id")
//    public Optional<DoctorEntity> findByDoctorId(@Param("id") Long id);
//    
//     @Query("SELECT p FROM Doctor p WHERE p.rid =:rid")
//    public Optional<DoctorEntity> findByRID(@Param("rid") Long rid);
}


