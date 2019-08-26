package in.srisrisri.clinic.medicineBrandName;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicineBrandNameRepo extends JpaRepository<MedicineBrandNameEntity,Long>{

    public MedicineBrandNameEntity findAllByBrandName(String value);

     @Query("select u from MedicineBrandName u where LOWER(u.brandName) LIKE LOWER(CONCAT('%',?1, '%'))  order by brandName asc ")
    public Page<MedicineBrandNameEntity> findAllByBrandNameLike(String filter, Pageable pageable);

     @Query("select u from MedicineBrandName u where LOWER(u.genericName) LIKE LOWER(CONCAT('%',?1, '%'))  order by brandName asc ")
     public Page<MedicineBrandNameEntity> findAllByGenericNameLike(String filter, Pageable pageable);

      @Query("select u from MedicineBrandName u where LOWER(u.composition) LIKE LOWER(CONCAT('%',?1, '%'))  order by brandName asc ")
    public Page<MedicineBrandNameEntity> findAllByCompositionLike(String filter, Pageable pageable);

public MedicineBrandNameEntity findAllByIdSpecial(Long v);

}