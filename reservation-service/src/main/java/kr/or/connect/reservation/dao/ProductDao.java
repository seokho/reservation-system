package kr.or.connect.reservation.dao;

import kr.or.connect.reservation.domain.ProductPrice;
import kr.or.connect.reservation.domain.dto.ProductDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.reservation.dao.sql.ProductSqls.*;
/**
 * Created by ODOL on 2017. 7. 12..
 */
@Repository
public class ProductDao {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<ProductPrice> productPriceRowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
    private RowMapper<ProductDto> productDtoRowMapper = BeanPropertyRowMapper.newInstance(ProductDto.class);

    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ProductDto> selectProductDtoListUseLimit(Integer offset, Integer limit) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("offset", offset);
        paramsMap.put("limit", limit);
        return jdbcTemplate.query(SELECT_PRODUCT_DTO_ALL_USE_LIMIT, paramsMap, productDtoRowMapper);
    }

    public List<ProductDto> selectProductDtoListByCategoryId(Integer category, Integer offset, Integer limit) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("category", category);
        paramsMap.put("offset", offset);
        paramsMap.put("limit", limit);
        return jdbcTemplate.query(SELECT_PRODUCT_DTO_LIST_BY_CATEGORY, paramsMap, productDtoRowMapper);
    }

    public ProductDto selectProductDtoByProductId(Long id) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", id);
        return jdbcTemplate.queryForObject(SELECT_PRODUCT_DTO_BY_PRODUCT_ID, paramsMap, productDtoRowMapper);
    }

    public List<ProductPrice> selectProductPriceListById(Long id) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", id);
        return jdbcTemplate.query(SELECT_PRODUCT_PRICE_LIST_BY_PRODUCT_ID, paramsMap, productPriceRowMapper);
    }


    public Integer count() {
        return jdbcTemplate.query(SELECT_PRODUCT_DTO_ALL, productDtoRowMapper).size();
    }
}
