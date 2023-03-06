package peaksoft.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import peaksoft.models.Appointment;
import peaksoft.service.AppointmentService;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.PatientService;

/**
 * The golden boy
 */
@Controller
@RequestMapping("/appointments")
@Transactional
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final PatientService patientService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, DepartmentService departmentService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.departmentService = departmentService;
        this.patientService = patientService;
    }
    @GetMapping("/{hospitalId}")
    String getAll(@PathVariable("hospitalId") Long id, Model model){
        model.addAttribute("appointments",appointmentService.getAll(id));
        return "appointment/appointments";
    }
    @GetMapping("/addAppointment/{hospitalId}")
    String addAppointment(@PathVariable("hospitalId") Long id ,Model model){
        Appointment appointment = new Appointment();
        model.addAttribute("newAppointment",appointment);
        model.addAttribute("doctors",doctorService.getAll(id));
        model.addAttribute("patients", patientService.getAll(id));
        model.addAttribute("departments",departmentService.getAll(id));
        model.addAttribute("hospitalId",id);
        return "appointment/saveAppointment";
    }
    @PostMapping("/saveAppointment/{hospitalId}")
    String saveAppointment(@PathVariable("hospitalId")Long id, @Valid Appointment appointment, BindingResult result){
        if (result.hasErrors())
            return "appointment/saveAppointment";
        appointmentService.save(id,appointment);
        return "redirect:/appointments/"+id;
    }
    @GetMapping("/editAppointment/{hospitalId}/{appointmentId}")
    String editAppointment(Model model,@PathVariable("hospitalId")Long hospitalId ,@PathVariable("appointmentId")Long id ){
        model.addAttribute("appointment",appointmentService.getById(hospitalId));
        model.addAttribute("doctors",doctorService.getAll(hospitalId));
        model.addAttribute("patients", patientService.getAll(hospitalId));
        model.addAttribute("departments",departmentService.getAll(hospitalId));
        model.addAttribute("appointmentId",id);
        model.addAttribute("hospitalId",hospitalId);
        return "appointment/updateAppointment";
    }
    @PostMapping("/update/{hospitalId}/{appointmentId}")
    String updateAppointment(@PathVariable("hospitalId")Long hospitalId,@PathVariable("appointmentId")Long appointmentId, @Valid Appointment appointment, BindingResult result){
        if (result.hasErrors())
            return "appointment/updateAppointment";
        appointmentService.update(appointmentId,appointment);
        return "redirect:/appointments/"+hospitalId;
    }
    @GetMapping("/deleteAppointment/{hospitalId}/{appointmentId}")
    String deleteAppointment(@PathVariable("hospitalId")Long hospitalId,@PathVariable("appointmentId")Long appointmentid){
        appointmentService.delete(appointmentid);
        return "redirect:/appointments/"+hospitalId;
    }
}