package fouriting.flockproject.config.security;

import fouriting.flockproject.config.jwt.JwtAccessDeniedHandler;
import fouriting.flockproject.config.jwt.JwtAuthenticationEntryPoint;
import fouriting.flockproject.config.jwt.JwtSecurityConfig;
import fouriting.flockproject.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico",
                        "/v3/api-docs",  "/configuration/ui",
                        "/swagger-resources", "/configuration/security",
                        "/swagger-ui/**", "/flock-api");
    }

    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                // CSRF 설정 Disable

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // 우리가 만든 예외처리 클래스들 삽입

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                // H2 - Console을 위한 설정 추가

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 기본적으로 스프링 시큐리티는 세션을 사용한다.
                // 하지만 우리는 세션 대신 토큰! 무상태로 설계하자.

                .and()
                .authorizeRequests()
                .antMatchers("/auth/**", "/hello",
                        "/swagger-resources/**", "/search", "/webtoons",
                        "/webtoons/{\\d+}").permitAll()
                .anyRequest().authenticated()
                // auth로 시작하는 경로만 접근허가. 나머진 모두 권한 획득 후 접근.
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}
