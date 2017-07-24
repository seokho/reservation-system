package kr.or.connect.reservation.dao;

import kr.or.connect.reservation.domain.DisplayInfo;
import kr.or.connect.reservation.domain.dto.DetailDto;
import kr.or.connect.reservation.domain.dto.ReviewDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.reservation.dao.sql.DetailSqls.*;

/**
 * Created by ODOL on 2017. 7. 17..
 */
@Repository
public class DetailDao {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<DetailDto> detailRowMapper = BeanPropertyRowMapper.newInstance(DetailDto.class);
    private RowMapper<ReviewDto> reviewRowMapper = BeanPropertyRowMapper.newInstance(ReviewDto.class);
    private RowMapper<DisplayInfo> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);


    public DetailDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<DetailDto> selectFileListByProductId(Long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.query(SELECT_FILES_BY_PRODUCT_ID, paramMap, detailRowMapper);
    }


    public List<ReviewDto> selectReviewListByProductId(Long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        List<ReviewDto> reviewDtoList = jdbcTemplate.query(SELECT_REVIEWS_BY_PRODUCT_ID, paramMap, reviewRowMapper);
        reviewDtoList.forEach(dto -> dto.setUsername(blockUsername(dto.getUsername())));
        return reviewDtoList;
    }

    public DisplayInfo selectDisplayInfoByProductId(Long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.queryForObject(SELECT_DISPLAY_INFO_BY_PRODUCT_ID, paramMap, displayInfoRowMapper);
    }

    private String blockUsername(String username) {
        StringBuilder stringBuilder = new StringBuilder(username);
        stringBuilder.replace(stringBuilder.length()-4, stringBuilder.length(), "****");
        return stringBuilder.toString();
    }


}
