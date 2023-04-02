package vn.com.panda.learncardriving.service.authentication;

import java.util.ArrayList;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.panda.learncardriving.dto.auth.AuthUserDTO;
import vn.com.panda.learncardriving.dto.department.DepartmentDTO;
import vn.com.panda.learncardriving.entity.User;
import vn.com.panda.learncardriving.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole().toString());
        authorities.add(role);
        AuthUserDTO userDTO = new AuthUserDTO(user.getUsername(), user.getPassword(), authorities);
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getUsername());

        if(user.getDepartment() != null) {
            DepartmentDTO departmentDTO = DepartmentDTO.builder()
                    .id(user.getId())
                    .name(user.getDepartment().getName())
                    .address(user.getDepartment().getAddress())
                    .build();
            userDTO.setDepartment(departmentDTO);
        }
        return userDTO;
    }
}
