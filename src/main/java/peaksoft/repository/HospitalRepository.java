package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.models.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {

}
