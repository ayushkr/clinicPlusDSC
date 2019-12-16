/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.purchaseBill;

import in.srisrisri.clinic.Vendor.VendorEntity;
import java.sql.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseBillRepo extends JpaRepository<PurchaseBillEntity, Long> {

    public List<PurchaseBillEntity> findByDateOfBillBetween(Date dateStart, Date dateEnd);


    public Page<PurchaseBillEntity> findAllByVendor(VendorEntity vendorEntity, Pageable pageable);

 @Query("select u from PurchaseBill u where LOWER(u.billNo) LIKE LOWER(CONCAT('%',?1, '%'))  order by dateOfBill desc ")
    public Page<PurchaseBillEntity> findAllByBillNoLike(String filter,Pageable pageable);

  

    public Page<PurchaseBillEntity> findAllByDateOfBill(Date valueOf, Pageable pageable);

}
