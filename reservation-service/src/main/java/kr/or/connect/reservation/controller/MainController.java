package kr.or.connect.reservation.controller;

import kr.or.connect.reservation.interceptor.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;




/**
 * Created by ODOL on 2017. 7. 12..
 */
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/main")
    public String index() {
        return "mainpage";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id) {
        return "detail";
    }

    @GetMapping("/review/{id}")
    public String review(@PathVariable("id") Long id) {
        return "review";
    }

    @GetMapping("/myReservation")
    @Authentication
    public String myReservation() {return "myreservation"; }

    @GetMapping("/reserve/{id}")
    @Authentication
    public String reserve() {
        return "reserve";
    }


}
