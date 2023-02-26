package AuthenticationModule.EnExSe.Repository;

import AuthenticationModule.EnExSe.Entities.Role;
import AuthenticationModule.EnExSe.Enum.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);

    RoleName findByRole(Role name);

}
