package peaksoft.service.serviceimpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Doctor;
import peaksoft.models.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DoctorService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The golden boy
 */
@Service
@RequiredArgsConstructor
@Transactional

public class  DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public Doctor save(Long hospitalId, Doctor doctor) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow();
        doctorRepository.save(doctor);
        doctor.setHospital(hospital);
        return doctor;
    }


    @Override
    public List<Doctor> getAll(Long id) {

        return doctorRepository.findAllByHospitalId(id);
    }

    @Override
    public Doctor getById(Long id) {
        return doctorRepository.findById(id).orElseThrow(()->new NoSuchElementException("this "+"id u nas net!!"));
    }

    @Override
    public void update(Long id, Doctor newDoctor) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        doctor.setEmail(newDoctor.getEmail());
        doctor.setFirstName(newDoctor.getFirstName());
        doctor.setLastName(newDoctor.getLastName());
        doctor.setPosition(newDoctor.getPosition());
    }

    @Override
    public void delete(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        Hospital hospital = doctor.getHospital();
        List<Appointment> appointments = doctor.getAppointments();
        appointments.forEach(a -> a.getDoctor().setAppointments(null));
        appointments.forEach(a -> a.getPatients().setAppointments(null));
        hospital.getAppointments().removeAll(appointments);

        for (int i = 0; i < appointments.size(); i++) {
                appointmentRepository.deleteById(appointments.get(i).getId());
        }
        doctorRepository.deleteById(id);
    }


}
