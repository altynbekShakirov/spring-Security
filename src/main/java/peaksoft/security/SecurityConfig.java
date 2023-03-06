package peaksoft.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;




@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Bean
    public UserDetailsService userDetails() {
        User.UserBuilder userBuilder =User.withDefaultPasswordEncoder();
        UserDetails admin = userBuilder.
                username("admin").
                password("123").
                roles("ADMIN").
                build();

        UserDetails doctor = userBuilder.
                username("doctor").
                password("1234").
                roles("DOCTOR").
                build();

        UserDetails user = userBuilder.
                username("user").
                password("321").
                roles("USER").
                build();


        return new InMemoryUserDetailsManager(admin, doctor, user);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().
                requestMatchers("/hospitals").permitAll().
                requestMatchers("/hospitals/addHospital").hasRole("ADMIN").
                requestMatchers("/hospitals/saveHospital").hasRole("ADMIN").
                requestMatchers("/hospitals/{id}/edit").hasRole("ADMIN").
                requestMatchers("/hospitals/{id}/updateHospital").hasRole("ADMIN").
                requestMatchers("/hospitals/{hospitalId}/deleteHospital").hasRole("ADMIN").
                requestMatchers("/hospitals/profile/{hospitalId}").permitAll().


                requestMatchers("/doctors/{hospitalId}").permitAll().
                requestMatchers("/doctors/addDoctor/{hospitalId}").hasRole("ADMIN").
                requestMatchers("/doctors/saveDoctor/{hospitalId}").hasRole("ADMIN").
                requestMatchers("/doctors/deleteDoctor/{doctorId}").hasRole("ADMIN").
                requestMatchers("/doctors/{doctorId}/editDoctor").hasRole("ADMIN").
                requestMatchers("/doctors/updateSaveDoctor/{doctorId}").hasRole("ADMIN").
                requestMatchers("/doctors/{doctor}/assignDoctor").hasRole("ADMIN").


                requestMatchers("/departments/{hospitalId}").permitAll().
                requestMatchers("/departments/addDepartment/{hospitalId}").hasRole("ADMIN").
                requestMatchers("/departments/saveDepartment/{hospitalId}").hasRole("ADMIN").
                requestMatchers("/departments/deleteDepartment/{departmentId}").hasRole("ADMIN").
                requestMatchers("/departments/{department}/editDepartment").hasRole("ADMIN").
                requestMatchers("/departments/updateSaveDepartment/{department}").hasRole("ADMIN").


                requestMatchers("/patients/{hospitalId}").permitAll().
                requestMatchers("/patients/addPatients/{hospitalId}").hasAnyRole("ADMIN","USER").
                requestMatchers("/patients/savePatients/{hospitalId}").hasAnyRole("ADMIN","USER").
                requestMatchers("/patients/deletePatients/{hospitalId}").hasAnyRole("ADMIN","USER").
                requestMatchers("/patients/{patientId}/editPatients").hasAnyRole("ADMIN","USER").
                requestMatchers("/patients/updateSavePatient/{patientId}").hasAnyRole("ADMIN","USER").


                requestMatchers("/appointments/{hospitalId}").permitAll().
                requestMatchers("/appointments/addAppointment/{hospitalId}").hasAnyRole("ADMIN","DOCTOR").
                requestMatchers("/appointments/saveAppointment/{hospitalId}").hasAnyRole("ADMIN","DOCTOR").
                requestMatchers("/appointments/editAppointment/{hospitalId}/{appointmentId}").hasAnyRole("ADMIN","DOCTOR").
                requestMatchers("/appointments/update/{hospitalId}/{appointmentId}").hasAnyRole("ADMIN","DOCTOR").
                requestMatchers("/appointments/deleteAppointment/{hospitalId}/{appointmentId}").hasAnyRole("ADMIN","DOCTOR").
                and()
                .formLogin()
                .defaultSuccessUrl("/hospitals")
                .permitAll();
        return http.build();
    }
}
