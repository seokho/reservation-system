package kr.or.connect.reservation.service.impl;

import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.domain.ProductPrice;
import kr.or.connect.reservation.domain.dto.ProductDto;
import kr.or.connect.reservation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ODOL on 2017. 7. 12..
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsUseLimit(Integer offset, Integer limit) {
        return productDao.selectProductDtoListUseLimit(offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategoryId(Integer category, Integer offset, Integer limit) {
        return productDao.selectProductDtoListByCategoryId(category, offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductDtoByProductId(Long id) {
        return productDao.selectProductDtoByProductId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPrice> getProductPricesByProductId(Long id) {
        return productDao.selectProductPriceListById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getProductsCount() {
        return productDao.count();
    }
}
