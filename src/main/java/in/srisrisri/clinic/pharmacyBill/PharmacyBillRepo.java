package in.srisrisri.clinic.pharmacyBill;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyBillRepo extends JpaRepository<PharmacyBillEntity,Long>{

    public List<PharmacyBillEntity> findByDateOfBillBetween(Date dateStart, Date dateEnd);

  
  
 

}