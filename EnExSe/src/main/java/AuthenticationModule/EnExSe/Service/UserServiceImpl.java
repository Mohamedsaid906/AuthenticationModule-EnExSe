package AuthenticationModule.EnExSe.Service;

import AuthenticationModule.EnExSe.Entities.Role;
import AuthenticationModule.EnExSe.Entities.User;
import AuthenticationModule.EnExSe.Repository.RoleRepository;
import AuthenticationModule.EnExSe.Repository.UserRepository;
import AuthenticationModule.EnExSe.Security.Request.SignUpForm;
import AuthenticationModule.EnExSe.Service.UserService;
import AuthenticationModule.EnExSe.Utils.ErrorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Configuration
public class  UserServiceImpl implements UserService {
    @Autowired
   private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    @Override
    public Role addRole(Role rolename) {
        return roleRepository.save(rolename);
    }


    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRepository.findByUsername(username);
        Role rol = roleRepository.findByRole(rolename);
        usr.getRoles().add(rol);
        return usr;
    }


    public Boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserByIdUser(id);
    }

    @Override
    public User UpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void DeletUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<?> signup(SignUpForm signUpForm) {
        if(userRepository.existsByUsername(signUpForm.getUsername())==true){
            return new ResponseEntity<>(new ErrorModel("Username is used "), HttpStatus.BAD_REQUEST);
        }
        if(signUpForm.getUsername().length()==0){
            return new ResponseEntity<>(new ErrorModel("Username should not be empty"),HttpStatus.BAD_REQUEST);
        }
        if(signUpForm.getPassword().length()==0){
            return new ResponseEntity<>(new ErrorModel("Password should not be empty"),HttpStatus.BAD_REQUEST);
        }
        if (signUpForm.getPassword().length()<5){
            return new ResponseEntity<>(new ErrorModel("Short PassWord"),HttpStatus.BAD_REQUEST);

        }
        if(signUpForm.getEmail().length()==0){
            return new ResponseEntity<>(new ErrorModel("Email should not be empty"),HttpStatus.BAD_REQUEST);
        }
        if(!isValidEmailAddress(signUpForm.getEmail())){
            return new ResponseEntity<>(new ErrorModel("Invalid email"),HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpForm.getEmail())==true){
            return new ResponseEntity<>(new ErrorModel("email is used"),HttpStatus.BAD_REQUEST);
        }
        signUpForm.setPassword(bCryptPasswordEncoder.encode(signUpForm.getPassword()));
        User userBd = new User();
        userBd.setUsername(signUpForm.getUsername());
        userBd.setEmail(signUpForm.getEmail());
        userBd.setPassword(signUpForm.getPassword());
        userBd.setGender(signUpForm.getGender());
        userBd.setEnabled(signUpForm.getEnabled());
        List<Role> roleListe = new ArrayList<>();
        roleListe.add(roleRepository.findByRole(signUpForm.getRole_Name()));
        userBd.setRoles(roleListe);
        userRepository.save(userBd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
