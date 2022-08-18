package fouriting.flockproject.config.jwt;

import fouriting.flockproject.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    // 여기서 토큰이 있는지 없는 지 거름망 역할을 한다.
    // JWT 토큰의 인증 정보를 현재 Thread의 SecurityContext에 저장하는 역할
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtoken = resolveToken(request);

        // Validation 단계.
        // 정상적인 토큰이면 해당 토큰으로 권한을 가져와 Context에 올린다.
        try {
            if (StringUtils.hasText(jwtoken) && tokenProvider.validateToken(jwtoken)) {
                Authentication authentication = tokenProvider.getAuthentication(jwtoken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (SecurityException | MalformedJwtException e){
            request.setAttribute("exception", ErrorCode.WRONG_TYPE_TOKEN.getErrorCode());
        } catch (ExpiredJwtException e){
            request.setAttribute("exception", ErrorCode.EXPIRED_JWT.getErrorCode());
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        // Header에서 Token꺼냄
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        // Header에 해당 Token이 존재하고, Bearer로 시작하는 토큰이면
        // 앞 7자리만 빼고 추출해서 토큰 정보를 꺼내온다.
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }
}
