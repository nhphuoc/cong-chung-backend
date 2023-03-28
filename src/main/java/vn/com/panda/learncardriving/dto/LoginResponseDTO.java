package vn.com.panda.learncardriving.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import vn.com.panda.learncardriving.configuration.ObjectMapperConfig;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private UUID id;
    private String username;
    private String fullName;
    private String token;
    @JsonFormat(pattern = ObjectMapperConfig.DATETIME_FORMAT)
    private LocalDateTime expired;
}
