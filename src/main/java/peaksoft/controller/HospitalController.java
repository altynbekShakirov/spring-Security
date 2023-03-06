package peaksoft.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Hospital;
import peaksoft.service.DoctorService;
import peaksoft.service.HospitalService;
import peaksoft.service.PatientService;


/**
 * The golden boy
 */
@Controller
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService service;
    private final DoctorService doctorService;
    private final PatientService patientService;

     @Autowired
    public HospitalController(HospitalService service, DoctorService doctorService, PatientService patientService) {
        this.service = service;
         this.doctorService = doctorService;
         this.patientService = patientService;
     }

     @GetMapping
     String getAll(Model model){
        model.addAttribute("hospitals",service.getAll());
        return "hospital/hospitals";
    }
    @GetMapping("/addHospital")
      String addHospital(Model model) {
        model.addAttribute( "newHospital", new Hospital());
        return "hospital/addHospital";
    }

    @PostMapping("/saveHospital")
    String saveHospital(@ModelAttribute("hospital") @Valid Hospital hospital,BindingResult result)  {
         if(result.hasErrors()){
             return "hospital/addHospital";
         }
        service.save(hospital);
        return "redirect:/hospitals";
    }


    @GetMapping("/{id}/edit")
     String updateHospital(@PathVariable("id") Long id, Model model) {
        model.addAttribute("oldHospital", service.getById(id));

        return "hospital/update";
    }

    @PostMapping("/{id}/updateHospital")
     String saveUpdate(@PathVariable("id")Long id,
            @ModelAttribute("hospital") @Valid Hospital hospital,BindingResult bindingResult) {
         if (bindingResult.hasErrors()){
             return "hospital/update";
         }
        service.update(id,hospital);
        return "redirect:/hospitals";
    }

    @GetMapping("{hospitalId}/deleteHospital")
     String deleteHospital(@PathVariable("hospitalId") Long hospitalId) {
        service.deleteById(hospitalId);
        return "redirect:/hospitals" ;
    }

    @GetMapping("/profile/{hospitalId}")
         String  profile(Model model,@PathVariable("hospitalId")Long id ){
         model.addAttribute("hospital",service.getById(id));
         model.addAttribute("hospitalId",id);
        model.addAttribute("countPatient", patientService.getAll(id).size());
        model.addAttribute("countDoctor",doctorService.getAll(id).size());

        return "hospital/profile";

    }




}
