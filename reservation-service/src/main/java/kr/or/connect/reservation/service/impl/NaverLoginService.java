package kr.or.connect.reservation.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.reservation.service.LoginService;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ODOL on 2017. 7. 19..
 */
@Service
public class NaverLoginService implements LoginService {
    private final String callbackURL = "http%3A%2F%2Flocalhost%3A8080%2Fapi%2Flogin%2FnaverCallback";
    private final String clientId = "KEf5YOJku5sJltnJIuJ7";
    private final String clientPW = "YMgOkKSKe6";
    private final String requestURL = "https://nid.naver.com/oauth2.0/authorize?client_id=" + clientId + "&response_type=code&redirect_uri=" + callbackURL + "&state=";
    private final String userProfileUrl = "https://apis.naver.com/nidlogin/nid/getUserProfile.xml";


    @Override
    public String generateState() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(130, secureRandom).toString(32);
    }

    @Override
    public String getRequestURL() {
        return requestURL;
    }


    @Override
    public void logout(HttpSession session) {
        String state = (String) session.getAttribute("state");
        String code = (String) session.getAttribute("code");
        String accessToken = (String) session.getAttribute("accessToken");
        String data = getHtml(getAccessUrl(state, code, "delete", accessToken), null);
        Map<String, String> map = JSONStringToMap(data);
        System.out.println(map.get("result"));
        session.removeAttribute("state");
        session.removeAttribute("accesToken");
        session.removeAttribute("code");
    }

    @Override
    public Map<String, String> getUserDataMap(String state, String code) {
        String data = getHtml(getAccessUrl(state, code, "auth", null), null);

        Map<String, String> map = JSONStringToMap(data);
        String accessToken = map.get("access_token");
        String tokenType = map.get("token_type");

        String profileDataXml = getHtml(userProfileUrl, tokenType + " " + accessToken);

        JSONObject jsonObject = XML.toJSONObject(profileDataXml);
        JSONObject responseData = jsonObject.getJSONObject("data");
        Map<String, String> userMap = JSONStringToMap(responseData.get("response").toString());
        return userMap;
    }


    private Map<String, String> JSONStringToMap(String str) {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(str, new TypeReference<HashMap<String, String>>() {
            });
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    private String getHtml(String url, String authorization) {
        HttpURLConnection httpRequest = null;
        String resultValue = null;
        try {
            URL u = new URL(url);
            httpRequest = (HttpURLConnection) u.openConnection();
            httpRequest.setRequestProperty("Content-type", "text/xml; charset=UTF-8");

            if (authorization != null) {
                httpRequest.setRequestProperty("Authorization", authorization);
            }
            httpRequest.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpRequest.getInputStream(), "UTF-8"));

            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            resultValue = sb.toString();
        } catch (IOException e) {

        } finally {
            if (httpRequest != null) {
                httpRequest.disconnect();
            }
        }
        return resultValue;
    }

    private String getAccessUrl(String state, String code, String grantType, String accessToken) {
        String accessURL = null;
        if (grantType.equals("auth")) {
            accessURL = "https://nid.naver.com/oauth2.0/token?client_id=" + clientId + "&client_secret=" + clientPW
                    + "&grant_type=authorization_code&state=" + state + "&code=" + code;
        } else if (grantType.equals("delete")) {
            accessURL = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=" + clientId + "&client_secret=" + clientPW
                    + "&access_token=" + accessToken + "&service_provider=NAVER";
        }
        return accessURL;
    }


}
