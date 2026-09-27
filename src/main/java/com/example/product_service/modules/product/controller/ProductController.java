package com.example.product_service.modules.product.controller;


import com.example.product_service.modules.product.dto.ProductRequestDto;
import com.example.product_service.modules.product.dto.ProductResponseDto;
import com.example.product_service.modules.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto){
        ProductResponseDto response = productService.createProduct(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id, 
            @RequestBody ProductRequestDto requestDto){
        ProductResponseDto response = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id){
        ProductResponseDto response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
