package kr.or.connect.reservation.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String username;
    private String email;
    private String tel;
    private String nickname;
}
