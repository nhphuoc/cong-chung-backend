package vn.com.panda.learncardriving.service.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.com.panda.learncardriving.configuration.ApplicationConfigProperties;
import vn.com.panda.learncardriving.controller.exception.UnauthorizedException;
import vn.com.panda.learncardriving.dto.auth.AuthUserDTO;
import vn.com.panda.learncardriving.dto.auth.LoginRequestDTO;
import vn.com.panda.learncardriving.dto.auth.LoginResponseDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final ApplicationConfigProperties appConfig;


    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        authenticate(requestDTO.getUsername(), requestDTO.getPassword());
        var user = (AuthUserDTO) userDetailsService.loadUserByUsername(requestDTO.getUsername());
        LocalDateTime expirationDate = LocalDateTime.now().plusHours(appConfig.getJwtExpired());
        final String token = jwtService.generateToken(user.getUsername(), expirationDate);

        return LoginResponseDTO.builder()
                .id(user.getId())
                .username(requestDTO.getUsername())
                .fullName(user.getFullName())
                .expired(expirationDate)
                .token(token)
                .department(user.getDepartment())
                .build();
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new UnauthorizedException("Can not login", e);
        }
    }
}
