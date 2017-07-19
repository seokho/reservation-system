package kr.or.connect.reservation.controller;

import kr.or.connect.reservation.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public ModelAndView login(HttpSession httpSession) {
        String state = loginService.generateState();
        httpSession.setAttribute("state", state);
        return new ModelAndView("redirect:" + loginService.getRequestURL() + state);
    }

    @GetMapping("/naverCallback")
    public ModelAndView callback(@RequestParam String state, @RequestParam String code, HttpServletRequest request) throws UnsupportedEncodingException {
        ModelAndView mv = new ModelAndView("redirect:/main");

        String storedState = (String) request.getSession().getAttribute("state");
        if (!state.equals(storedState)) {
            System.out.println("401 unauthorized");
            return mv;
        }
        Map<String, String> userMap = loginService.getUserDataMap(state, code);

        System.out.println(userMap.get("email"));

        mv.addObject("userMap", userMap);
        return mv;
    }


}
