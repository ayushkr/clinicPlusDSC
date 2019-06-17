package in.srisrisri.clinic.user;

import in.srisrisri.clinic.doctor.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

public interface UserService {

    public DoctorEntity save(DoctorEntity doctor);
    public DoctorEntity delete(DoctorEntity doctor);
    public Long delete(Long id);
    public List<DoctorEntity> findAll();
    public Page<DoctorEntity> findAll(Pageable pageable);
    public Optional<DoctorEntity> findById(Long id);
    

}
