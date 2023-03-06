package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

/**
 * The golden boy
 */
@Entity
@Table(name = "appointments")
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "appointment_id_gen")
    @SequenceGenerator(name = "appointment_id_gen",allocationSize = 1)
    private Long id;
    @NotNull(message = "Data must not be empty!!")
    private LocalDate date;

    @ManyToOne(cascade = {REFRESH,DETACH,MERGE,PERSIST})
    private Patient patients;
    @ManyToOne(cascade = {REFRESH,DETACH,MERGE,PERSIST})
    private Doctor doctor;
    @ManyToOne(cascade = {REFRESH,DETACH,MERGE,PERSIST})
    private Department department;
    @Transient
     private  Long patientId;
    @Transient
     private Long doctorId;
    @Transient
     private Long departmentId;


    public Appointment(LocalDate date) {
        this.date = date;
    }
}
