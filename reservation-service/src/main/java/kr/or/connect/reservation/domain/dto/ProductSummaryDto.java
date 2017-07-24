package kr.or.connect.reservation.domain.dto;

import kr.or.connect.reservation.domain.ProductPrice;
import kr.or.connect.reservation.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryDto {
    private ProductDto productDto;
    private List<ProductPrice> productPriceList;
    private User user;

}
