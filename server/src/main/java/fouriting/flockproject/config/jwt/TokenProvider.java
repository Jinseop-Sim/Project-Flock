package fouriting.flockproject.config.jwt;

import fouriting.flockproject.domain.dto.response.MemberTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXIPIRE_TIME = 1000 * 60 * 15;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 24 * 14;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public MemberTokenDto generateTokenDto(Authentication authentication){
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXIPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // 토큰 DTO 반환
        return MemberTokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken){
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // Claim에서 유저 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어 Authentication 반환.
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token){
        // 토큰 검증 함수
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e){
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e){
            log.info("잘못된 JWT 토큰입니다.");
        }

        return false;
    }

    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}