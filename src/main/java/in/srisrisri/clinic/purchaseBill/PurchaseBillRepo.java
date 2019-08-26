/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.purchaseBill;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseBillRepo extends JpaRepository<PurchaseBillEntity, Long> {

    public List<PurchaseBillEntity> findByDateOfBillBetween(Date dateStart, Date dateEnd);

//    @Query("select u from PurchaseBill u where LOWER(u.appointment.patient.name) LIKE LOWER(CONCAT('%',?1, '%'))  order by dateOfBill desc ")
//    public Page<PurchaseBillEntity> findAllByPatientNameLike(String filter, Pageable pageable);

}
