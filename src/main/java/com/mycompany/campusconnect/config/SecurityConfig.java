package com.mycompany.campusconnect.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.http.HttpMethod;



@Configuration
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http

            .authorizeHttpRequests(auth -> auth


                .requestMatchers(
        "/",
        "/index",
        "/login",
        "/register",
        "/css/**",
        "/js/**",
        "/images/**"
)
.permitAll()


                



                // Dashboards
                .requestMatchers("/admin-dashboard")
                .hasRole("ADMIN")


                .requestMatchers("/faculty-dashboard")
                .hasRole("FACULTY")


                .requestMatchers("/student-dashboard")
                .hasRole("STUDENT")
                    
                  



                // Student Management
                .requestMatchers(
                        "/students",
                        "/students/**",
                        "/add-student",
                        "/save-student",
                        "/edit-student/**",
                        "/update-student",
                        "/delete-student/**"
                )
                .hasRole("ADMIN")



                // Faculty Management
                .requestMatchers(
                        "/faculty",
                        "/faculty/**",
                        "/add-faculty",
                        "/save-faculty",
                        "/edit-faculty/**",
                        "/update-faculty",
                        "/delete-faculty/**"
                )
                .hasRole("ADMIN")

            // View Courses
          .requestMatchers("/courses")
           .hasAnyRole("ADMIN", "FACULTY", "STUDENT")

              // Manage Courses (admin only now - faculty can still VIEW
              // courses via the separate "/courses" matcher above)
                  .requestMatchers(
        "/courses/**",
        "/add-course",
        "/save-course",
        "/edit-course/**",
        "/update-course",
        "/delete-course/**"
)
.hasRole("ADMIN")

                // View Notices
.requestMatchers("/notices")
.hasAnyRole("ADMIN", "FACULTY", "STUDENT")

// Manage Notices
.requestMatchers(
        "/notices/**",
        "/add-notice",
        "/save-notice",
        "/edit-notice/**",
        "/update-notice",
        "/delete-notice/**"
)
.hasAnyRole("ADMIN", "FACULTY")

                // Student Profile
                .requestMatchers("/profile")
                .hasRole("STUDENT")


                // Manage Fees (admin only)
                .requestMatchers(
                        "/fees",
                        "/add-fee",
                        "/save-fee",
                        "/edit-fee/**",
                        "/update-fee",
                        "/delete-fee/**"
                )
                .hasRole("ADMIN")


                // View own fees (student only)
                .requestMatchers("/my-fees")
                .hasRole("STUDENT")


                // View Assignments (all roles)
                .requestMatchers("/assignments")
                .hasAnyRole("ADMIN", "FACULTY", "STUDENT")

                // Manage Assignments (admin + faculty)
                .requestMatchers(
                        "/add-assignment",
                        "/save-assignment",
                        "/edit-assignment/**",
                        "/update-assignment",
                        "/delete-assignment/**"
                )
                .hasAnyRole("ADMIN", "FACULTY")


                // View Attendance (all roles)
                .requestMatchers("/attendance")
                .hasAnyRole("ADMIN", "FACULTY", "STUDENT")

                // Manage Attendance (admin + faculty)
                .requestMatchers(
                        "/add-attendance",
                        "/save-attendance",
                        "/edit-attendance/**",
                        "/update-attendance",
                        "/delete-attendance/**"
                )
                .hasAnyRole("ADMIN", "FACULTY")


                // Remaining pages
                .anyRequest()
                .authenticated()

            )



            .formLogin(login -> login


                .loginPage("/login")


                .successHandler(
                        new CustomAuthenticationSuccessHandler()
                )


                .permitAll()

            )



            .logout(logout -> logout


                .logoutRequestMatcher(PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/logout"))


                .logoutSuccessUrl("/login?logout")


                .invalidateHttpSession(true)


                .deleteCookies("JSESSIONID")


                .permitAll()

            );



        return http.build();

    }






    // Delegating encoder: understands multiple formats via a stored prefix
    // like {bcrypt}... - newly hashed passwords use bcrypt, and it can
    // still validate old {noop}... (plaintext) values during the
    // transition period, without needing a second bean or config.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // The old @Bean InMemoryUserDetailsManager userDetailsService() has been
    // removed. CustomUserDetailsService (annotated @Service) is picked up
    // automatically by Spring Security's formLogin now, since it's the
    // only UserDetailsService bean in the app. See that class for how
    // admin/faculty/student accounts are each handled.

}