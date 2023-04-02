package vn.com.panda.learncardriving.dto.auth;

import java.util.Collection;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import vn.com.panda.learncardriving.dto.department.DepartmentDTO;

@Getter
@Setter
public class AuthUserDTO extends org.springframework.security.core.userdetails.User {
    private UUID id;
    private String fullName;
    private DepartmentDTO department;

    public AuthUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
