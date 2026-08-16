package com.mycompany.campusconnect.config;


import com.mycompany.campusconnect.model.Faculty;
import com.mycompany.campusconnect.model.Student;
import com.mycompany.campusconnect.repository.FacultyRepository;
import com.mycompany.campusconnect.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * Authenticates admin (fixed), and real students/faculty by email.
 *
 * PASSWORD FORMAT: Student/Faculty passwords are now hashed with BCrypt
 * whenever save/update runs (see StudentController, FacultyController).
 * The delegating PasswordEncoder stores hashed values with a "{bcrypt}"
 * prefix, so it can recognize its own format automatically.
 *
 * BACKWARD COMPATIBILITY: accounts created before hashing was added still
 * have a raw plaintext password with no prefix at all. withEncoderPrefix()
 * below detects that case and wraps it as "{noop}..." so those accounts
 * keep working exactly as before, without forcing every existing user to
 * reset their password immediately. Once an account is edited/resaved,
 * its password becomes a real bcrypt hash going forward.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Fixed admin account
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("{noop}admin123")
                    .roles("ADMIN")
                    .build();
        }

        // Try a real faculty account first
        Faculty faculty = facultyRepository.findByEmail(username);

        if (faculty != null) {

            if (faculty.getPassword() == null) {
                throw new UsernameNotFoundException(
                        "Faculty account has no password set: " + username);
            }

            return User.builder()
                    .username(faculty.getEmail())
                    .password(withEncoderPrefix(faculty.getPassword()))
                    .roles("FACULTY")
                    .build();
        }

        // Otherwise try a real student account
        Student student = studentRepository.findByEmail(username);

        if (student != null) {
            return User.builder()
                    .username(student.getEmail())
                    .password(withEncoderPrefix(student.getPassword()))
                    .roles("STUDENT")
                    .build();
        }

        throw new UsernameNotFoundException("No account found for: " + username);
    }


    // If the stored value already has an encoder-id prefix like "{bcrypt}"
    // (i.e. it was saved after hashing was added), use it as-is. Otherwise
    // it's a legacy plaintext password from before this change - wrap it
    // as "{noop}" so the delegating encoder still knows how to compare it.
    private String withEncoderPrefix(String storedPassword) {
        if (storedPassword != null && storedPassword.startsWith("{")) {
            return storedPassword;
        }
        return "{noop}" + storedPassword;
    }

}