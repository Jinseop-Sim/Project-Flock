package fouriting.flockproject.utility;

import fouriting.flockproject.config.SessionConstant;
import fouriting.flockproject.exception.ErrorCode;
import fouriting.flockproject.exception.custom.IdNotExistException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static String getCurrentMemberId(HttpServletRequest request){
        HttpSession currSession = request.getSession(false);
        if(currSession == null)
            throw new IdNotExistException(ErrorCode.ACCESS_DENIED);

        return (String) currSession.getAttribute(SessionConstant.LOGIN_MEMBER);
    }
}
