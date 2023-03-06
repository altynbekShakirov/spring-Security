package peaksoft.service;

import peaksoft.models.Hospital;

import java.util.List;

/**
 * The golden boy
 */
public interface HospitalService {
    Hospital save(Hospital hospital);
    List<Hospital> getAll();
    Hospital getById( Long id );
    void  update(Long id ,Hospital newHospital);
    void  deleteById(Long id);
}
