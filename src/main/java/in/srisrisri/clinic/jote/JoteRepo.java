/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.jote;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ayush
 */
@Repository
public interface  JoteRepo extends JpaRepository<JoteEntity,Long>{

    @Override
    public List<JoteEntity> findAll();
    
    
  
//       @Query("SELECT p FROM Jote p WHERE p.id =:id")
//        public Optional<JoteEntity> findBy_Id(@Param("id") Long id);
//    
     public List<JoteEntity> findByOrderByStatusAscRidAsc();

//    @Override
//    public Optional<JoteEntity> findById(Long id);
//    
    
    
    
}
