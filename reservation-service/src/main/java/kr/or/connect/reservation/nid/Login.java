package kr.or.connect.reservation.nid;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class Login {
    private static final String CALLBACK_URL = "http%3A%2F%2Flocalhost%3A8080%2Fcallback";
    private static final String CLIENT_ID = "KEf5YOJku5sJltnJIuJ7";
    private static final String CLIENT_PW = "YMgOkKSKe6";
    public static final String REQUEST_URL = "https://nid.naver.com/oauth2.0/authorize?client_id=" + CLIENT_ID + "&response_type=code&redirect_uri=" + CALLBACK_URL + "&state=";

    public static String generateState() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }



}