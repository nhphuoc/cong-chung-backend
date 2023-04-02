package vn.com.panda.learncardriving.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.panda.learncardriving.dto.auth.LoginRequestDTO;
import vn.com.panda.learncardriving.dto.auth.LoginResponseDTO;
import vn.com.panda.learncardriving.service.authentication.AuthService;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@OpenAPIDefinition(tags = @Tag(name = "authentication"))
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login", tags = "authentication")
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }
}
