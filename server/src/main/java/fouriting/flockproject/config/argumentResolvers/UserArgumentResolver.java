package fouriting.flockproject.config.argumentResolvers;

import fouriting.flockproject.config.SessionConstant;
import fouriting.flockproject.domain.dto.response.session.UserSessionDto;
import fouriting.flockproject.exception.ErrorCode;
import fouriting.flockproject.exception.custom.IdNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter){
        return parameter.getParameterType().equals(UserSessionDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String loginId = (String) webRequest.getAttribute(SessionConstant.LOGIN_MEMBER, WebRequest.SCOPE_SESSION);
        if(loginId == null)
            throw new IdNotExistException(ErrorCode.ACCESS_DENIED);

        return new UserSessionDto(loginId);
    }
}
