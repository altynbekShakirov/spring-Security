package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.models.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository< Department,Long > {
    @Query("select l from Department l where l.hospital.id=:id")
    List<Department> findAllByHospitalId(Long id);

}
