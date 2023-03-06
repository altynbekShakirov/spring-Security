package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "doctors")
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_id_gen")
    @SequenceGenerator(name = "doctor_id_gen", sequenceName = "doctor_id_seq", allocationSize = 1)
    private Long id;
    @NotBlank(message = "First name must not be empty!")
    @Size(min = 3, max = 30, message = "Should not be less than 2 more than 30 characters!!")
    @Column(name = "first_name")
    private String firstName;
    @Size(min = 3, max = 30, message = "Should not be less than 2 more than 30 characters!!")
    @NotBlank(message = "Last name must not be empty!")
    @Column(name = "last_name")
    private String lastName;
    @Size(min = 3, max = 30, message = "Should not be less than 2 more than 30 characters!!")
    @NotBlank(message = "Position must not be empty!")
    private String position;
    @Size(min = 3, max = 30, message = "Should not be less than 2 more than 30 characters!!")
    @NotBlank(message = "Email must not be empty!")
    @Email(message = "Invalid email address")
    private String email;
    @ManyToMany(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private List<Department> departments;

    public void addDepartment(Department department) {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        departments.add(department);
    }

    @OneToMany(mappedBy = "doctor", cascade = ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();
    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Hospital hospital;
    @Transient
    private List<Long> departmentIdes = new ArrayList<>();

    public Doctor(String firstName, String lastName, String position, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
    }
}
