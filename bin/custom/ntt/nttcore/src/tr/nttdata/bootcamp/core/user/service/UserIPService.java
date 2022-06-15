package tr.nttdata.bootcamp.core.user.service;

import javax.servlet.http.HttpServletRequest;

public interface UserIPService {

    void storeUserIP(String userIP);
    String getUserIP();
    void storeUserIPFromRequest(HttpServletRequest request);
}
