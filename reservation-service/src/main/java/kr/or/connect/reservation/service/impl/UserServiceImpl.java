package kr.or.connect.reservation.service.impl;

import kr.or.connect.reservation.dao.UserDao;
import kr.or.connect.reservation.domain.User;
import kr.or.connect.reservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
}
