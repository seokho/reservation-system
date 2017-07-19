package kr.or.connect.reservation.service;

import java.util.Map;

/**
 * Created by ODOL on 2017. 7. 19..
 */
public interface LoginService {
    String generateState();
    String getRequestURL();
    Map<String, String> getUserDataMap(String state, String code);
    //    Map<String, String> JSONStringToMap(String str);
//    String getHtml(String url, String authorization);
//    String getAccessUrl(String state, String code);
//    String getUserProfileUrl();

}
