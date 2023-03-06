package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.models.Doctor;

import javax.print.Doc;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long > {
    @Query("select l from Doctor l where l.hospital.id=:id")
    List<Doctor> findAllByHospitalId(Long id);
}
