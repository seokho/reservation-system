package kr.or.connect.reservation.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductPrice {
    private Long id;
    private Integer priceType;
    private Integer price;
    private Double discountRate;
}
