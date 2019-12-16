/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.dashboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 *
 * @author akr2
 */

@Repository
public interface DashboardRepo extends JpaRepository<Dashboard,Long> {
    
    @Query("select u from dashboard u where LOWER(u.title) LIKE LOWER(CONCAT('%',?1, '%'))  order by id asc ")
    public Page<Dashboard> findAllByTitleLike(String filter, Pageable pageable);

      @Query("select u from dashboard u where LOWER(u.body) LIKE LOWER(CONCAT('%',?1, '%'))  order by id asc ")
    public Page<Dashboard> findAllByBodyLike(String filter, Pageable pageable);

    

}
