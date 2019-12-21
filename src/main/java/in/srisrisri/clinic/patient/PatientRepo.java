package in.srisrisri.clinic.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepo extends JpaRepository<PatientEntity,Long> {

  

    public Page<PatientEntity> findAllByName(String filter, Pageable pageable);

    @Query("select u from Patient u where LOWER(u.name) LIKE LOWER(CONCAT('%',?1, '%'))  order by name asc ")
    public Page<PatientEntity> findAllByNameLike(String filter, Pageable pageable);

     @Query("select u from Patient u where u.contactPhone LIKE CONCAT('%',?1, '%')  order by name asc ")
    public Page<PatientEntity> findAllByContactPhoneLike(String filter, Pageable pageable);


}


