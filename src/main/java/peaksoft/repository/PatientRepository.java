package peaksoft.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.models.Doctor;
import peaksoft.models.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query("select l from Patient l where l.hospital.id=:id")
    List<Patient> findAllByHospitalId(Long id);

}
