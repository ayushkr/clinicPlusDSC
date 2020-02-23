/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.finance;

import in.srisrisri.clinic.entities.FinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author akr2
 */

@Repository
public interface FinanceRepo extends 
        JpaRepository<FinanceEntity,Long>{
    
}
