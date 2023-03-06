package peaksoft.service.serviceimpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Department;
import peaksoft.models.Doctor;
import peaksoft.models.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;

import java.util.List;

/**
 * The golden boy
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public Department save(Long hospitalId, Department department) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow();
        departmentRepository.save( department);
        department.setHospital(hospital);
        return department;
    }

    @Override
    public List<Department> getAll(Long id) {

        return departmentRepository.findAllByHospitalId(id);

    }

    @Override
    public String assignDepartment(Long doctorId, Long departmentId)  {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        department.addDoctor(doctor);
        doctor.addDepartment(department);
        return "Successfully assigned!! ";

    }

    @Override
    public void update(Long id, Department newDepartment) {
        Department department = departmentRepository.findById(id).orElseThrow();
        department.setName(newDepartment.getName());



    }

    @Override
    public void deleteById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow();
        List<Hospital> hospitals = hospitalRepository.findAll();


        for (Hospital hospital : hospitals) {
            List<Appointment> appointments = hospital.getAppointments();
            if (appointments != null) {
                List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
                appointmentList.forEach(appointments::remove);
                appointmentList.forEach(s -> appointmentRepository.deleteById(s.getId()));
            }
        }

        List<Department> departments = department.getHospital().getDepartments();
        departments.removeIf(s -> s.getId().equals(id));

        List<Doctor> doctors = department.getDoctors();
        if (doctors != null) {
            doctors.forEach(d -> d.getDepartments().removeIf(s -> s.getId().equals(id)));
        }

        departmentRepository.deleteById(id);

    }

    @Override
    public Department getById(Long id) {
        return departmentRepository.findById(id).orElseThrow();
    }
}
