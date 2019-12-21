package in.srisrisri.clinic.pharmacyBill;

import in.srisrisri.clinic.appointment.AppointmentEntity;
import java.sql.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PharmacyBillRepo extends JpaRepository<PharmacyBillEntity, Long> {

    public List<PharmacyBillEntity> findByDateOfBillBetween(Date dateStart, Date dateEnd);

    public Page<PharmacyBillEntity> findAllByAppointment(AppointmentEntity appointmentEntity, Pageable pageable);

    @Query("select u from PharmacyBill u where LOWER(u.appointment.patient.name) LIKE LOWER(CONCAT('%',?1, '%'))  order by dateOfBill desc ")
public Page<PharmacyBillEntity> findAllByPatientNameLike(String filter, Pageable pageable);

}
