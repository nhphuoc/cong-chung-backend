package vn.com.panda.learncardriving.helper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.com.panda.learncardriving.dto.AuthUserDTO;
import vn.com.panda.learncardriving.enums.UserRole;

public class AuthHelper {

    public static AuthUserDTO getAuthUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        boolean isAnonymous = context.getAuthentication().getAuthorities().stream()
                .anyMatch(item -> "ROLE_ANONYMOUS".equals(item.getAuthority()));
        if(isAnonymous){
            AuthUserDTO authUserDTO = new AuthUserDTO("anonymous", StringUtils.EMPTY, List.of());
            authUserDTO.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
            authUserDTO.setFullName("Anonymous");
            return authUserDTO;
        }
        return (AuthUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static UUID getAuthUserId() {
        return getAuthUser().getId();
    }

    public static String getAuthUserName() {
        return getAuthUser().getUsername();
    }

    public static UUID getAuthPersonId() {
        return getAuthUserId();
    }

    public static List<UserRole> getAuthUserRoles() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(UserRole::valueOf).collect(Collectors.toList());
    }
}
