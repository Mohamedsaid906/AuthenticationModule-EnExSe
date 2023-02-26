package AuthenticationModule.EnExSe.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
@Table(name="EnExSeUSER")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(unique = true)
    @NotNull
    private String username;

    private Boolean enabled;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String gender;


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idRole"))
    private List<Role> roles = new ArrayList<>();

}
