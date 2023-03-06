package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.models.Appointment;
import peaksoft.models.Doctor;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long > {
    @Query("select l from Appointment l where l.doctor.hospital.id=:id")
    List<Appointment> findAllByHospitalId(Long id);

}
