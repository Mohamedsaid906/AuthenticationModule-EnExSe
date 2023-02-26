package AuthenticationModule.EnExSe.Service;

import AuthenticationModule.EnExSe.Entities.Role;
import AuthenticationModule.EnExSe.Entities.User;
import AuthenticationModule.EnExSe.Security.Request.SignUpForm;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    List<User> getAllUser();

    Role addRole(Role role);

    User addRoleToUser(String username, String rolename);

    public Boolean isValidEmailAddress(String email);

    User getUserById(Long id);

    User UpdateUser(User user);

    void DeletUserById(Long id);

    ResponseEntity<?> signup(SignUpForm signUpForm);


}
