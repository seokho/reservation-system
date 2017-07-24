package kr.or.connect.reservation.service.impl;

import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dao.UserDao;
import kr.or.connect.reservation.domain.ProductPrice;
import kr.or.connect.reservation.domain.User;
import kr.or.connect.reservation.domain.dto.ProductDto;
import kr.or.connect.reservation.domain.dto.ProductSummaryDto;
import kr.or.connect.reservation.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class ReserveServiceImpl implements ReserveService {
    @Autowired
    ProductDao productDao;
    @Autowired
    UserDao userDao;

    @Override
    public ProductSummaryDto getProductSummary(HttpSession httpSession, Long id) {
        ProductDto productDto = productDao.selectProductDtoByProductId(id);
        List<ProductPrice> productPriceList = productDao.selectProductPriceListById(id);
        Map<String, String> userMap = (Map)httpSession.getAttribute("userMap");
        User user = userDao.getUserByEmail(userMap.get("email"));

        return new ProductSummaryDto(productDto, productPriceList, user);
    }
}
