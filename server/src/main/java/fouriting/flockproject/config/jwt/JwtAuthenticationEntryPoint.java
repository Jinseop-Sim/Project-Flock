package fouriting.flockproject.config.jwt;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import fouriting.flockproject.exception.ErrorCode;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");

        if(exception == null){
            setResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
        } else if (exception.equals(ErrorCode.EXPIRED_JWT.getErrorCode())) {
            setResponse(response, ErrorCode.EXPIRED_JWT);
        } else{
            setResponse(response, ErrorCode.ACCESS_DENIED);
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", errorCode.getMessage());
        responseJson.put("code", errorCode.getErrorCode());

        response.getWriter().println(responseJson);
    }
}
