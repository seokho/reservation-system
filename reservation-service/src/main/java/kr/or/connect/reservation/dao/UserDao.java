package kr.or.connect.reservation.dao;

import kr.or.connect.reservation.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static kr.or.connect.reservation.dao.sql.UserSqls.SELECT_USER_BY_EMAIL;

@Repository
public class UserDao {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public User getUserByEmail(String email) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("email", email);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL, paramsMap, userRowMapper);
    }
}
