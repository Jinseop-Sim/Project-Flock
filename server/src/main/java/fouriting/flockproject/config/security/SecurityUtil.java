package fouriting.flockproject.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {
    private SecurityUtil(){}

    // Security Context에 유저가 올라가는 시점이다.
    public static Long getCurrnetMemberId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("Context에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}
