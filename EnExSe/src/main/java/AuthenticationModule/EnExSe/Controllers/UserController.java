package AuthenticationModule.EnExSe.Controllers;

import AuthenticationModule.EnExSe.Entities.User;
import AuthenticationModule.EnExSe.Security.JwtUtils;
import AuthenticationModule.EnExSe.Security.MyUserDetailsService;
import AuthenticationModule.EnExSe.Security.Request.SignUpForm;
import AuthenticationModule.EnExSe.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final JwtUtils jwtUtil;


    private final MyUserDetailsService myUserDetailsService;


    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(JwtUtils jwtUtil, MyUserDetailsService myUserDetailsService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtUtil = jwtUtil;
        this.myUserDetailsService = myUserDetailsService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @GetMapping("all")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
    @PostMapping("/signup")
    public ResponseEntity<?> AddUser(@Valid @RequestBody SignUpForm signUpForm) {

        return userService.signup(signUpForm);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @GetMapping("/{username}")
    public User getUserByUsername( @PathVariable String username ){
        return userService.findUserByUsername(username);

    }
}
