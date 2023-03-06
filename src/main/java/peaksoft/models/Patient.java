package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

/**
 * The golden boy
 */
@Entity
@Table(name = "patients")
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patients_id_gen")
    @SequenceGenerator(name = "patients_id_gen", sequenceName = "patients_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    @Size(min = 3,max = 30,message = "Should not be less than 2 more than 30 characters!!")
    @NotBlank(message = "First name must not be empty!")
    private String firstName;
    @Column(name = "last_name")
    @Size(min = 3,max = 30,message = "Should not be less than 2 more than 30 characters!!")
    @NotBlank(message = "Last name must not be empty!")
    private String lastName;
    @NotBlank(message = "Phone number must not be empty!")
    @Column(name = "phone_number", unique = true)
    @Size(min = 3,max = 30,message = "Should not be less than 2 more than 30 characters!!")
    @Pattern(regexp = "^\\+996\\d{9}$", message = "The phone number must be 12 digits long and start with +996 !!!")
    private String phoneNumber;
    private String gender;

    @NotBlank(message = "Email number must not be empty!!")
    @Email(message = "Invalid email address")
    @Size(min = 3,max = 30,message = "Should not be less than 2 more than 30 characters!!")
    private String email;
    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Hospital hospital;

    @OneToMany(mappedBy = "patients", cascade = ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();
    @Transient
    private Long hospitalId;

    public Patient(String firstName, String lastName, String phoneNumber, String gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
    }
}
