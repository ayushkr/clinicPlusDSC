package in.srisrisri.clinic.appointmentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentTypeRepo extends JpaRepository<AppointmentTypeEntity,Long> {
    
}


