package com.example.product_service.service;

import com.example.product_service.Dto.TenantLoginDto;
import com.example.product_service.exception.UserNotFoundException;
import com.example.product_service.rabbitmq.MessageProducer;
import com.example.product_service.repository.TenantRepository;
import com.example.product_service.entity.Tenant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TenantService {
    private final MessageProducer messageProducer;
    private final TenantRepository tenantRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public TenantService(MessageProducer messageProducer, TenantRepository tenantRepository){
        this.messageProducer = messageProducer;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public Tenant createTenant(Tenant tenant){
        //Tenant UUID
        String tenantId = UUID.randomUUID().toString();

        //saved tenant data in master in DB
        Tenant tenants = new Tenant();
        tenants.setId(tenantId);
        tenants.setOrgName(tenant.getOrgName());
        tenants.setTenantName(tenant.getTenantName());
        tenants.setAdminEmail(tenant.getAdminEmail());
        tenants.setAdminPassword(encoder.encode(tenant.getAdminPassword()));
        tenants.setAddress(tenant.getAddress());
        tenants.setContactPhone(tenant.getContactPhone());
        tenants.setContactEmail(tenant.getContactEmail());
        Tenant savedTenant = tenantRepository.save(tenants);
//        messageProducer.(savedTenant);
        return savedTenant;
    }


    public String tenantLogin(TenantLoginDto dto){
        Tenant tenant = tenantRepository.findByAdminEmail(dto.getAdminEmail());
        if(tenant == null) throw new UserNotFoundException("Email not matched!");
        if(!encoder.matches(dto.getAdminPassword(), tenant.getAdminPassword())){
            throw new UserNotFoundException("Invalid password!");
        }

        return "user login successfully ";
    }

    public String tenantDelete(String id){
        if(tenantRepository.existsById(id)){
            tenantRepository.deleteById(id);

        }
        return "deleted sucessfully";
    }

}

