package peaksoft.service.serviceimpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Hospital;
import peaksoft.models.Patient;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.repository.PatientRepository;
import peaksoft.service.PatientService;

import java.util.List;

/**
 * The golden boy
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public Patient save(Long hospitalId, Patient patients) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow();
        Patient save = patientRepository.save(patients);
        save.setHospital(hospital);
        return patients;
    }

    @Override
    public List<Patient> getAll(Long id) {
        return patientRepository.findAllByHospitalId(id);
    }

    @Override
    public Patient getById(Long id) {
        return patientRepository.findById(id).orElseThrow();
    }

    @Override
    public void updatePatients(Long id, Patient newPatient) {
        Patient patient = patientRepository.findById(id).orElseThrow();
        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());
        patient.setEmail(newPatient.getEmail());
        patient.setGender(newPatient.getGender());
        patient.setPhoneNumber(newPatient.getPhoneNumber());
        patientRepository.save(patient);
    }

    @Override
    public void deleteByPatientsId(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow();
        Hospital hospital = patient.getHospital();
        List<Appointment> appointments = patient.getAppointments();
        appointments.forEach(a -> a.getPatients().setAppointments(null));
        appointments.forEach(a -> a.getDoctor().setAppointments(null));
        hospital.getAppointments().removeAll(appointments);
        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepository.deleteById(appointments.get(i).getId());
        }
        patientRepository.deleteById(id);
    }


}
