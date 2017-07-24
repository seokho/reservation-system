package kr.or.connect.reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

/**
 * Created by ODOL on 2017. 7. 13..
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private String placeName;
    private String placeStreet;
    private String observationTime;
    private Date displayStart;
    private Date displayEnd;
    private String fileName;
    private String saveFileName;

}
