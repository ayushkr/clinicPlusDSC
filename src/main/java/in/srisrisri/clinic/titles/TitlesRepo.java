package in.srisrisri.clinic.titles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TitlesRepo extends JpaRepository<Titles,Long> {
    
    
    
//
// @Query("SELECT p FROM Doctor p WHERE LOWER(p.name) = LOWER(:lastName)")
//    public Optional<DoctorEntity> findLastName(@Param("lastName") String lastName);
//
//    @Query("SELECT p FROM Doctor p WHERE p.id =:id")
//    public Optional<DoctorEntity> findByDoctorId(@Param("id") Long id);
//    
//     @Query("SELECT p FROM Doctor p WHERE p.rid =:rid")
//    public Optional<DoctorEntity> findByRID(@Param("rid") Long rid);
}


