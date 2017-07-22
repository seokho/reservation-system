package kr.or.connect.reservation.controller;

import kr.or.connect.reservation.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by ODOL on 2017. 7. 19..
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/naverLogin")
    public ModelAndView login(HttpServletRequest httpServletRequest) {
        String state = loginService.generateState();
        String prevPage = httpServletRequest.getHeader("referer");
        ModelAndView mv = new ModelAndView("redirect:" + loginService.getRequestURL() + state);
        httpServletRequest.getSession().setAttribute("state", state);
        httpServletRequest.getSession().setAttribute("prevPage", prevPage);
        return mv;
    }

    @GetMapping("/naverCallback")
    public ModelAndView callback(@RequestParam String state, @RequestParam String code, HttpServletRequest request) throws UnsupportedEncodingException {
        ModelAndView mv;
        String storedState = (String) request.getSession().getAttribute("state");
        String prevPage = (String) request.getSession().getAttribute("prevPage");

        if (!state.equals(storedState)) {
            mv = new ModelAndView("redirect:/api/login/naverLogin");
            return mv;
        }
        mv = new ModelAndView("redirect:"+prevPage);
        Map<String, String> userMap = loginService.getUserDataMap(state, code);
        request.getSession().setAttribute("userMap", userMap);
        return mv;
    }

    @GetMapping("/naverLogout")
    public void logout(HttpServletRequest httpServletRequest) {
        loginService.logout(httpServletRequest.getSession());
    }

}
