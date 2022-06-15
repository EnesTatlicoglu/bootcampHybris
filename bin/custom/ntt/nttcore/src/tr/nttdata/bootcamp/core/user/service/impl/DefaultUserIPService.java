package tr.nttdata.bootcamp.core.user.service.impl;

import de.hybris.platform.servicelayer.session.SessionService;
import tr.nttdata.bootcamp.core.user.service.UserIPService;

import javax.servlet.http.HttpServletRequest;

public class DefaultUserIPService implements UserIPService {

    private String USER_IP_SESSION_PARAMETER = "User_IP";

    private SessionService sessionService;

    public DefaultUserIPService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void storeUserIP(String userIP) {
        if (userIP == null){
            sessionService.removeAttribute(USER_IP_SESSION_PARAMETER);
        }else {
            sessionService.setAttribute(USER_IP_SESSION_PARAMETER, userIP);
        }
    }

    @Override
    public String getUserIP() {
        return sessionService.getAttribute(USER_IP_SESSION_PARAMETER);
    }

    @Override
    public void storeUserIPFromRequest(HttpServletRequest request) {
        storeUserIP(getClientIpAddr(request));
    }

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
