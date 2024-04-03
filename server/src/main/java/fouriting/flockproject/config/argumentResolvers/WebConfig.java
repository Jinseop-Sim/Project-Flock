package fouriting.flockproject.config.argumentResolvers;

import fouriting.flockproject.config.argumentResolvers.UserArgumentResolver;
import fouriting.flockproject.config.argumentResolvers.UserBooleanArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final UserArgumentResolver userArgumentResolver;
    private final UserBooleanArgumentResolver userBooleanArgumentResolver;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(userArgumentResolver);
        resolvers.add(userBooleanArgumentResolver);
    }
}
