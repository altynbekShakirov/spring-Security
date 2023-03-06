package peaksoft.service;

import peaksoft.models.Appointment;

import java.util.List;

/**
 * The golden boy
 */
public interface AppointmentService {
    void save(Long id,Appointment appointment);
    Appointment getById(Long id);
    List<Appointment> getAll(Long id);
    void  update(Long id,Appointment newAppointment);
    void  delete(Long id );
}
