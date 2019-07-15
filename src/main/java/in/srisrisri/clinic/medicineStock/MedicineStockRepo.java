package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.medicineBrandName.MedicineBrandNameEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicineStockRepo extends JpaRepository<MedicineStockEntity,Long>{

    


    public MedicineStockEntity findAllByMedicineBrandName(MedicineBrandNameEntity brandNameEntity);

    @Query("select sum(u.qty) from PharmacyBillRow u where u.medicineStock=?1 ")
    public Long findQtyUsedById(MedicineStockEntity  entity);

   





}