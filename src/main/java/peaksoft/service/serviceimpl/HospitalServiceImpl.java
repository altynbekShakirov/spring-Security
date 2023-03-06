package peaksoft.service.serviceimpl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;

/**
 * The golden boy
 */
@Service
@Transactional
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    @Override
    public Hospital save(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @Override
    public List<Hospital> getAll() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital getById(Long id) {
        return hospitalRepository.findById(id).orElseThrow();
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        Hospital hospital = hospitalRepository.findById(id).orElseThrow();
        hospital.setName(newHospital.getName());
        hospital.setAddress(newHospital.getAddress());

    }

    @Override
    public void deleteById(Long id) {
        hospitalRepository.deleteById(id);

    }
}
