package in.srisrisri.clinic.medicineStock;

import in.srisrisri.clinic.medicineBrandName.MedicineBrandNameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicineStockRepo extends JpaRepository<MedicineStockEntity,Long>{

    

 public MedicineStockEntity findAllByMedicineBrandName(MedicineBrandNameEntity brandNameEntity);

    @Query("select sum(u.qty) from PharmacyBillRow u where u.medicineStock=?1 ")
    public Long findQtyUsedById(MedicineStockEntity  entity);

     @Query("select u from MedicineStock u where LOWER(u.medicineBrandName.brandName) LIKE LOWER(CONCAT('%',?1, '%'))  order by id asc ")
    public Page<MedicineStockEntity> findAllByBrandNameLike(String filter, Pageable pageable);

      @Query("select u from MedicineStock u where LOWER(u.medicineBrandName.genericName) LIKE LOWER(CONCAT('%',?1, '%'))  order by id asc ")
    public Page<MedicineStockEntity> findAllByGenericNameLike(String filter, Pageable pageable);
  
    @Query("select u from MedicineStock u where LOWER(u.medicineBrandName.composition) LIKE LOWER(CONCAT('%',?1, '%'))  order by id asc ")
    public Page<MedicineStockEntity> findAllByCompositionLike(String filter, Pageable pageable);

   





}