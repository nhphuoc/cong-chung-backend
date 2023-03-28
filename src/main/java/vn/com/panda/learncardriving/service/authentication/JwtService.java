package vn.com.panda.learncardriving.service.authentication;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import vn.com.panda.learncardriving.configuration.ApplicationConfigProperties;
import vn.com.panda.learncardriving.helper.LocalDateTimeHelper;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private static final long serialVersionUID = 3659109543858300494L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

   private final ApplicationConfigProperties appConfig;

    public String getDataFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims =   getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(appConfig.getJwtSecret()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(String username, LocalDateTime expirationDate) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username, expirationDate);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, LocalDateTime expirationDate) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(LocalDateTimeHelper.convert(LocalDateTime.now()))
                .signWith(SignatureAlgorithm.HS512, appConfig.getJwtSecret())
                .setExpiration(LocalDateTimeHelper.convert(expirationDate))
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        return (getDataFromToken(token).equals(username) && !isTokenExpired(token));
    }

    public String getUsername(HttpHeaders headers) {
        List<String> authorizations = headers.get("authorization");
        if (authorizations != null && !authorizations.isEmpty()) {
            String token = authorizations.get(0);
            if (StringUtils.length(token) >= 7) {
                return getDataFromToken(token.substring(7));
            }
        }
        return StringUtils.EMPTY;
    }

    public String getTokenWithoutPrefix(String authorization) {
        if (!StringUtils.startsWith(authorization, "Bearer")) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.length(authorization) < 7) {
            return StringUtils.EMPTY;
        }
        return authorization.substring(7);
    }
}
