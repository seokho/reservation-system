package kr.or.connect.reservation.service;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by ODOL on 2017. 7. 19..
 */
public interface LoginService {
    String generateState();
    String getRequestURL();
    void logout(HttpSession session);
    Map<String, String> getUserDataMap(String state, String code);


}
