package peaksoft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Department;
import peaksoft.models.Doctor;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.HospitalService;

/**
 * The golden boy
 */
@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;
    private Long hospitalId;


    @GetMapping("/{hospitalId}")
    String getAllDoctors(@PathVariable("hospitalId") Long id, Model model,
                         @ModelAttribute("department") Department department) {
        model.addAttribute("departments", departmentService.getAll(id));
        model.addAttribute("hospitalId", id);
        model.addAttribute("doctors", doctorService.getAll(id));
        model.addAttribute("departments", departmentService.getAll(id));
        hospitalId = id;
        return "doctor/doctors";
    }

    @GetMapping("/addDoctor/{hospitalId}")
    String addDepartment(@PathVariable("hospitalId") Long id, Model model) {

        model.addAttribute("newDoctor", new Doctor());
        model.addAttribute("hospitalId", id);
        model.addAttribute("hospital", hospitalService.getById(id));
        return "doctor/doctorSave";
    }

    @PostMapping("/saveDoctor/{hospitalId}")
    String saveDepartment(@PathVariable("hospitalId") Long id, @Valid Doctor doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "doctor/doctorSave";
        }
        doctorService.save(id, doctor);
        return "redirect:/doctors/" + id;
    }

    @GetMapping("/deleteDoctor/{doctorId}")
    String deleteHospital(@PathVariable("doctorId") Long id) {
        doctorService.delete(id);
        return "redirect:/doctors/" + hospitalId;
    }

    @GetMapping("/{doctorId}/editDoctor")
    String updateHospital(@PathVariable("doctorId") Long id, Model model) {
        model.addAttribute("oldDoctor", doctorService.getById(id));
        model.addAttribute("hospitalId", hospitalId);
        return "doctor/updateDoctor";
    }

    @PostMapping("/updateSaveDoctor/{doctorId}")
    String updateSaveDepartment(@PathVariable("doctorId") Long id, @Valid Doctor doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "doctor/updateDoctor";
        doctorService.update(id, doctor);
        return "redirect:/doctors/" + hospitalId;
    }


    @PostMapping("/{doctorId}/assignDoctor")
    private String assignDoctor(@PathVariable("doctorId") Long doctorId,
                                @ModelAttribute("department")Department department) {
        Long id = department.getId();
        departmentService.assignDepartment(doctorId, id);
        return "redirect:/doctors/" + hospitalId;
    }


}
