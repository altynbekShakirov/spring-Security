package peaksoft.service;

import peaksoft.models.Patient;

import java.util.List;

/**
 * The golden boy
 */
public interface PatientService {
    Patient save(Long hospitalId, Patient patients);
    List<Patient> getAll(Long id);
    Patient getById(Long id);
    void  updatePatients(Long id, Patient newPatient);
    void deleteByPatientsId(Long id);

}
