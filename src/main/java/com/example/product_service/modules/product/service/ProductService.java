package com.example.product_service.modules.product.service;


import com.example.product_service.modules.product.dto.ProductRequestDto;
import com.example.product_service.modules.product.dto.ProductResponseDto;
import com.example.product_service.modules.product.entity.Product;
import com.example.product_service.modules.product.repository.ProductRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto){
        Product product = new Product();
        product.setProductName(requestDto.getProductName());
        product.setQuantity(requestDto.getQuantity());
        product.setPrice(requestDto.getPrice());
        
        Product savedProduct = productRepo.save(product);
        return mapToResponseDto(savedProduct);
    }

    public List<ProductResponseDto> getAllProduct(){
        return productRepo.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long id){
        System.out.println("Fetching from database...");
        Product product = productRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found with id: " + id));
        return mapToResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto){
        Product product = productRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found with id: " + id));
        
        product.setProductName(requestDto.getProductName());
        product.setQuantity(requestDto.getQuantity());
        product.setPrice(requestDto.getPrice());
        
        Product updatedProduct = productRepo.save(product);
        return mapToResponseDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found with id: " + id));
        productRepo.delete(product);
    }

    private ProductResponseDto mapToResponseDto(Product product){
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setProductName(product.getProductName());
        responseDto.setQuantity(product.getQuantity());
        responseDto.setPrice(product.getPrice());
        return responseDto;
    }
}
