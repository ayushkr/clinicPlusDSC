package in.srisrisri.clinic.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepo extends JpaRepository<PatientEntity,Long> {
    
    
    
//
// @Query("SELECT p FROM patient p WHERE LOWER(p.name) = LOWER(:lastName)")
//    public Optional<EntityClass> findLastName(@Param("lastName") String lastName);
//
 // @Query("SELECT p FROM patient p WHERE p.id =:id")
//    public Optional<EntityClass> findByEntityId(@Param("id") Long id);
    
//     @Query("SELECT p FROM patient p WHERE p.rid =:rid")
//    public Optional<EntityClass> findByRID(@Param("rid") Long rid);
}


