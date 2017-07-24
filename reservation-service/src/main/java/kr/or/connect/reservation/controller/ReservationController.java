package kr.or.connect.reservation.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import kr.or.connect.reservation.domain.dto.ProductSummaryDto;
import kr.or.connect.reservation.interceptor.Authentication;
import kr.or.connect.reservation.service.impl.ReserveServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ODOL on 2017. 7. 20..
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    ReserveServiceImpl reserveService;

    @GetMapping("/{id}")
    public ProductSummaryDto getProductSummary(@PathVariable("id") Long id, HttpSession httpSession) {
        return reserveService.getProductSummary(httpSession, id);
    }

}
