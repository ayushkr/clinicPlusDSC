package in.srisrisri.clinic.fileContent;

import in.srisrisri.clinic.doctor.DoctorEntity;
import in.srisrisri.clinic.patient.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="files", collectionResourceRel="files")
public interface FileContentRepository extends JpaRepository<FileContent, Long> {

   @Query("select u from FileContent  u  where description LIKE  %?1%  order by id ")
    public Page<FileContent> findByDescription(String filter, Pageable pageable);

    public Page<FileContent> findAllByPatient(PatientEntity patientEntity, Pageable pageable);

    public Page<FileContent> findAllByDoctor(DoctorEntity doctorEntity, Pageable pageable);

  

}
