package kr.or.connect.reservation.service;

import kr.or.connect.reservation.domain.dto.ProductSummaryDto;

import javax.servlet.http.HttpSession;

public interface ReserveService {
    ProductSummaryDto getProductSummary(HttpSession httpSession, Long id);
}
