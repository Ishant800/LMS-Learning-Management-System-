package com.example.product_service.modules.product.service;


import com.example.product_service.modules.product.entity.Product;
import com.example.product_service.modules.product.repository.ProductRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public Product createProduct(Product product){
        return productRepo.save(product);
    }

    public List<Product> getAllProduct(){
        return productRepo.findAll();
    }


    public Product getProductById(Long id){
        System.out.println("Fetching from database...");
        return productRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));
    }
}
