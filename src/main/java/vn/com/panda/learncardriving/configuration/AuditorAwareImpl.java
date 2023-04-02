package vn.com.panda.learncardriving.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import vn.com.panda.learncardriving.dto.auth.AuthUserDTO;
import vn.com.panda.learncardriving.helper.AuthHelper;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        AuthUserDTO authUser = AuthHelper.getAuthUser();
        if(authUser == null) {
            return Optional.of("Unknown");
        }
        return Optional.of(authUser.getUsername());
    }
}
