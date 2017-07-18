package kr.or.connect.reservation.domain;

import lombok.*;

/**
 * Created by ODOL on 2017. 7. 19..
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisplayInfo {
    //"SELECT place_name, place_lot, place_street, tel" +
    private Long id;
    private String placeName;
    private String placeLot;
    private String placeStreet;
    private String tel;
}
