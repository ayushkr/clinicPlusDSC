package in.srisrisri.clinic.medicineBrandName;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineBrandNameRepo extends JpaRepository<MedicineBrandNameEntity,Long>{

    public MedicineBrandNameEntity findAllByBrandName(String value);

    public Page<MedicineBrandNameEntity> findAllByBrandName(String filter, Pageable pageable);



}