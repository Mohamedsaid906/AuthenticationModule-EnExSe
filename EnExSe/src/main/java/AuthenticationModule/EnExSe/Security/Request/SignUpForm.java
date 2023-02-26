package AuthenticationModule.EnExSe.Security.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {

    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private String gender;
    private String role_Name;


}
