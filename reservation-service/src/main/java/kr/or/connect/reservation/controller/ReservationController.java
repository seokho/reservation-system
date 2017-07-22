package kr.or.connect.reservation.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import kr.or.connect.reservation.interceptor.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ODOL on 2017. 7. 20..
 */
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @GetMapping("/myReservation")
    @Authentication
    public ModelAndView myReservation(HttpServletRequest request) {
        return new ModelAndView("myreservation");
    }

}
