package peaksoft.service.serviceimpl;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.models.*;
import peaksoft.repository.*;
import peaksoft.service.AppointmentService;

import java.util.List;


/**
 * The golden boy
 */
@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {


    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentServiceImpl( AppointmentRepository appointmentRepository,
                                   HospitalRepository hospitalRepository,
                                   DepartmentRepository departmentRepository,
                                   PatientRepository patientRepository,
                                   DoctorRepository doctorRepository) {
       this.appointmentRepository = appointmentRepository;
       this.hospitalRepository = hospitalRepository;
       this.departmentRepository = departmentRepository;
       this.patientRepository = patientRepository;
       this.doctorRepository = doctorRepository;
   }
    @Override
    public void save(Long id,Appointment appointment) {
       Hospital hospital= hospitalRepository.findById(id).orElseThrow();
        Appointment newAppointment=new Appointment();
        newAppointment.setId(appointment.getId());
        newAppointment.setDate(appointment.getDate());
        newAppointment.setDoctor(doctorRepository.findById(appointment.getDoctorId()).orElseThrow());
        newAppointment.setDepartment(departmentRepository.findById(appointment.getDepartmentId()).orElseThrow());
        newAppointment.setPatients(patientRepository.findById(appointment.getPatientId()).orElseThrow());
        hospital.addAppointment(newAppointment);
       System.out.println(appointmentRepository.save(newAppointment));
   }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Appointment> getAll(Long id) {
        return appointmentRepository.findAllByHospitalId(id);
    }

    @Override
    public void update(Long id,Appointment newAppointment) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setDate(newAppointment.getDate());

    }

    @Override
    public void delete(Long id) {
        Appointment existAppointment = appointmentRepository.findById(id).orElseThrow();
        Hospital existHospital = existAppointment.getDoctor().getHospital();
        existHospital.getAppointments().remove(existAppointment);
        for (Doctor doctor : existHospital.getDoctors()) {
            doctor.getAppointments().remove(existAppointment);
        }
        for (Patient patient : existHospital.getPatients()) {
            patient.getAppointments().remove(existAppointment);
        }
        appointmentRepository.deleteById(id);

    }
}
