package kr.or.connect.reservation.controller;

import kr.or.connect.reservation.domain.dto.ProductDto;
import kr.or.connect.reservation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by ODOL on 2017. 7. 12..
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/count")
    public Integer getProductsCount() {
        return productService.getProductsCount();
    }

    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam Integer offset,
            @RequestParam Integer limit) {
        return productService.getProductsUseLimit(offset, limit);
    }

    @GetMapping("/{category}")
    public List<ProductDto> getProductsByCategory(
            @PathVariable("category") Integer category,
            @RequestParam Integer offset,
            @RequestParam Integer limit) {
        return productService.getProductsByCategoryId(category, offset, limit);
    }
}
