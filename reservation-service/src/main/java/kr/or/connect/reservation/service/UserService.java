package kr.or.connect.reservation.service;

import kr.or.connect.reservation.domain.User;

public interface UserService {
    User getUserByEmail(String email);
}
