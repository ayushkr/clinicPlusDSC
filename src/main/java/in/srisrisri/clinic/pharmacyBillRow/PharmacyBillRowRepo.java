package in.srisrisri.clinic.pharmacyBillRow;

import in.srisrisri.clinic.pharmacyBill.PharmacyBillEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyBillRowRepo extends JpaRepository<PharmacyBillRowEntity,Long>{

public List<PharmacyBillRowEntity> findByPharmacyBill(PharmacyBillEntity pharmacyBillEntity);

}