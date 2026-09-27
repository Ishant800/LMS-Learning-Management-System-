package com.example.product_service.modules.tenant.controller;

import com.example.product_service.modules.tenant.dto.TenantRequestDto;
import com.example.product_service.modules.tenant.dto.TenantResponseDto;
import com.example.product_service.modules.tenant.entity.Tenant;
import com.example.product_service.modules.tenant.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService service;
    public TenantController( TenantService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<TenantResponseDto> createTenants(@RequestBody TenantRequestDto tenant){
        return ResponseEntity.ok(service.createTenant(tenant));
    }

    @GetMapping
    public ResponseEntity<List<Tenant>> getTenants(){
        return ResponseEntity.ok(service.getTenant());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable String id){
        return ResponseEntity.ok(service.getTenantById(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTenant(@RequestParam String id){
       return ResponseEntity.ok(service.tenantDelete(id));
    }

}
