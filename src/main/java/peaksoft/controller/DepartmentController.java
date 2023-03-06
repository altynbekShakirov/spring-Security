package peaksoft.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import peaksoft.models.Department;
import peaksoft.service.DepartmentService;
import peaksoft.service.HospitalService;

/**
 * The golden boy
 */
@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private  final DepartmentService departmentService;
    private  final HospitalService hospitalService;
    private Long hospitalId;


    @Autowired
    public DepartmentController(DepartmentService departmentService, HospitalService hospitalService) {
        this.departmentService = departmentService;

        this.hospitalService = hospitalService;
    }


    @GetMapping("/{hospitalId}")
    String  getAll(Model model,@PathVariable("hospitalId")Long id ){
        model.addAttribute("id",id);
            model.addAttribute("departments", departmentService.getAll(id));
        model.addAttribute("hospital",hospitalService.getById(id));
        hospitalId=id;
        return "department/departments";
    }
    @GetMapping("/addDepartment/{hospitalId}")
    String addDepartment(@PathVariable("hospitalId") Long id ,Model model){
        model.addAttribute("newDepartment",new Department());
        model.addAttribute("hospitalId",id);
        model.addAttribute("hospital",hospitalService.getById(id));
        return "department/save";
    }
    @PostMapping("/saveDepartment/{hospitalId}")
    String saveDepartment(@PathVariable("hospitalId")Long id, @Valid Department department, BindingResult result){
        if (result.hasErrors())
            return "department/save";
        departmentService.save(id,department);
        return "redirect:/departments/"+id;
    }
    @GetMapping("/deleteDepartment/{departmentId}")
    String deleteHospital(@PathVariable("departmentId") Long id) {
        departmentService.deleteById(id);
        return "redirect:/departments/"+hospitalId;
    }
    @GetMapping("/{departmentId}/editDepartment")
    String updateHospital(@PathVariable("departmentId") Long id, Model model) {
        model.addAttribute("oldDepartment",departmentService.getById(id));
        model.addAttribute("hospitalId",hospitalId);
        return "department/updateDepartment";
    }
    @PostMapping("/updateSaveDepartment/{departmentId}")
    String updateSaveDepartment( @PathVariable("departmentId")Long id,@Valid Department department,BindingResult result){
        if (result.hasErrors()) {
            return "department/updateDepartment";
        }
        departmentService.update(id, department);
        return "redirect:/departments/"+hospitalId;
    }







}
