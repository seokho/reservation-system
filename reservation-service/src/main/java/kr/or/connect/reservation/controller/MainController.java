package kr.or.connect.reservation.controller;

import kr.or.connect.reservation.nid.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;

import static kr.or.connect.reservation.nid.Login.*;

/**
 * Created by ODOL on 2017. 7. 12..
 */
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/main")
    public String index() {
        System.out.println();
        return "mainpage";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id){
        return "detail";
    }

    @GetMapping("/review/{id}")
    public String review(@PathVariable("id") Long id){
        return "review";
    }

//    @GetMapping("/login")
//    public String login(HttpSession httpSession) {
//
//        String state = Login.generateState();
//        httpSession.setAttribute("state", state);
//        return "redirect:" + REQUEST_URL + state;
//    }
//
//    @GetMapping("/callback")
//    public String callback(@RequestParam String state, @RequestParam String code, HttpServletRequest request) throws UnsupportedEncodingException {
//        String storedState = (String) request.getSession().getAttribute("state");
//        if (!state.equals(storedState)) {
//            System.out.println("401 unauthorized");
//            return "redirect:/main";
//        }
//        //AccessToken 요청 및 파싱할 부분
//        System.out.println(request.getSession().getAttribute("state"));
//
//        return "redirect:/main";
//    }
}
