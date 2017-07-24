package kr.or.connect.reservation.dao.sql;

public class UserSqls {
    public static final String SELECT_USER_BY_EMAIL =
            "SELECT id, username, email, tel, nickname" +
                    " FROM users" +
                    " WHERE email = :email";
}
