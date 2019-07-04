package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.medicineBrandName.MedicineBrandNameEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineStockRepo extends JpaRepository<MedicineStockEntity,Long>{

    


    public MedicineStockEntity findAllByMedicineBrandName(MedicineBrandNameEntity brandNameEntity);

   





}