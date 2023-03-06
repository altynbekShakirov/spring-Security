package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;


/**
 * The golden boy
 */
@Entity
@Table(name = "hospitals")
@NoArgsConstructor
@Getter
@Setter
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "hospital_id_gen")
    @SequenceGenerator(name = "hospital_id_gen",sequenceName = "hospital_seq",allocationSize = 1)
    private  Long id ;
    @Column(unique = true)
    @NotEmpty(message = "Name must not be empty!!")
    @Size(min = 3,max = 30,message = "Should not be less than 2 more than 30 characters!!")
    private String name;
    @NotEmpty(message =  "Address must not be empty!!")
    @Size(min = 3,max = 30,message = "Should not be less than 2 more than 30 characters!!")
    private String address;
    @OneToMany(mappedBy = "hospital",cascade = ALL,fetch = FetchType.EAGER)
    private List<Doctor>doctors = new ArrayList<>();
    @OneToMany(mappedBy = "hospital",cascade = ALL,fetch = FetchType.EAGER)
    private List<Patient>patients = new ArrayList<>();
    @OneToMany(mappedBy = "hospital",cascade = ALL, fetch = FetchType.EAGER)
    private List<Department>departments = new ArrayList<>();
    @OneToMany(cascade = ALL,fetch = FetchType.EAGER)
    private List<Appointment>appointments = new ArrayList<>();
    public void addAppointment(Appointment appointment){
        if (appointments== null){
           appointments= new ArrayList<>();
        }
        appointments.add(appointment);

    }

    public Hospital(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
