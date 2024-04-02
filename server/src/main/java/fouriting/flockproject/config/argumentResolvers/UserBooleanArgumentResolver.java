package fouriting.flockproject.config.argumentResolvers;

import fouriting.flockproject.config.SessionConstant;
import fouriting.flockproject.domain.dto.response.session.UserBooleanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserBooleanArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserBooleanDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String currLoginId = (String) webRequest.getAttribute(SessionConstant.LOGIN_MEMBER, RequestAttributes.SCOPE_SESSION);

        if(currLoginId == null)
            return new UserBooleanDto(false, null);
        return new UserBooleanDto(true, currLoginId);
    }
}
