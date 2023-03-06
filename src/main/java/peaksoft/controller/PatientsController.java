package peaksoft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Patient;
import peaksoft.models.enums.Gender;
import peaksoft.service.HospitalService;
import peaksoft.service.PatientService;

/**
 * The golden boy
 */
@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientsController {
    private final PatientService patientService;
    private final HospitalService hospitalService;
    private Long hospitalId;


    @GetMapping("/{hospitalId}")
    String getAllPatient(@PathVariable("hospitalId")Long id, Model model){
        model.addAttribute("hospitalId",id);
        model.addAttribute("patients", patientService.getAll(id));
        hospitalId=id;
        return "patients/patients";
    }
    @GetMapping("/addPatients/{hospitalId}")
    String addPatient(@PathVariable("hospitalId") Long id ,Model model){
        Patient patients = new Patient();
        model.addAttribute("newPatient",patients);
        model.addAttribute("hospitalId",id);
        model.addAttribute("hospital",hospitalService.getById(id));
        model.addAttribute("male", Gender.MALE.name());
        model.addAttribute("female",Gender.FEMALE.name());
        return "patients/addPatients";
    }
    @PostMapping("/savePatients/{hospitalId}")
    String savePatient(@PathVariable("hospitalId")Long id,  @Valid Patient patients, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "patients/addPatients";
        }
        patientService.save(id,patients);
        return "redirect:/patients/"+id;
    }
    @GetMapping("/deletePatients/{patientId}/{hospitalId}")
    String delete(@PathVariable("patientId") Long id,@PathVariable Long hospitalId) {
        patientService.deleteByPatientsId(id);
        return "redirect:/patients/"+hospitalId;
    }
    @GetMapping("/{patientId}/editPatients")
    String update(@PathVariable("patientId") Long id, Model model) {
        model.addAttribute("oldPatient", patientService.getById(id));
        model.addAttribute("hospitalId",hospitalId);
        model.addAttribute("male", Gender.MALE.name());
        model.addAttribute("female",Gender.FEMALE.name());
        return "patients/updatePatient";
    }
    @PostMapping("/updateSavePatient/{patientId}")
    String updateSavePatient(@PathVariable("patientId")Long id,@Valid Patient patients,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "patients/updatePatient";
        }
        patientService.updatePatients(id, patients);
        return "redirect:/patients/"+hospitalId;
    }




}
