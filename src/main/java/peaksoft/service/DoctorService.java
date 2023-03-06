package peaksoft.service;

import peaksoft.models.Doctor;

import java.util.List;

/**
 * The golden boy
 */
public interface DoctorService {
    Doctor  save(Long hospitalId, Doctor doctor);

    List<Doctor> getAll(Long id);
    Doctor getById(Long id);
    void  update(Long id,Doctor newDoctor);
    void delete(Long id);

}
