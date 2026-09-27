package com.example.product_service.modules.tenant.service;

import com.example.product_service.common.messaging.NotificationEvent;
import com.example.product_service.common.messaging.NotificationProducer;
import com.example.product_service.common.messaging.NotificationType;
import com.example.product_service.modules.tenant.dto.TenantLoginDto;
import com.example.product_service.common.exception.UserNotFoundException;
import com.example.product_service.common.messaging.MessageProducer;
import com.example.product_service.modules.tenant.dto.TenantRequestDto;
import com.example.product_service.modules.tenant.dto.TenantResponseDto;
import com.example.product_service.modules.tenant.repository.TenantRepository;
import com.example.product_service.modules.tenant.entity.Tenant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TenantService {
    private final MessageProducer messageProducer;
    private final TenantRepository tenantRepository;

    private final NotificationProducer producer;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public TenantService(MessageProducer messageProducer, TenantRepository tenantRepository, NotificationProducer producer){
        this.messageProducer = messageProducer;
        this.tenantRepository = tenantRepository;
        this.producer = producer;
    }

    @Transactional
    public TenantResponseDto createTenant(TenantRequestDto tenant){

        Tenant tenants = Tenant.builder()
                .id(UUID.randomUUID().toString())
                .orgName(tenant.orgName())
                .tenantName(tenant.tenantName())
                .adminEmail(tenant.adminEmail())
                .adminPassword(encoder.encode(tenant.adminPassword()))
                .address(tenant.address())
                .contactEmail(tenant.contactEmail())
                .contactPhone(tenant.contactPhone())
                .build();
        Tenant savedTenant = tenantRepository.save(tenants);
//        messageProducer.
        Map<String, String> data = Map.of(
                "orgName", savedTenant.getOrgName(),
                "adminName", savedTenant.getTenantName()
        );
        producer.sendNotification(new NotificationEvent(savedTenant.getAdminEmail(), NotificationType.TENANT_REGISTRATION,data));
        return new TenantResponseDto(savedTenant);
    }


    public String tenantLogin(TenantLoginDto dto){
        Tenant tenant = tenantRepository.findByAdminEmail(dto.getAdminEmail());
        if(tenant == null) throw new UserNotFoundException("Email not matched!");
        if(!encoder.matches(dto.getAdminPassword(), tenant.getAdminPassword())){
            throw new UserNotFoundException("Invalid password!");
        }

        return "user login successfully ";
    }

    public List<Tenant> getTenant(){
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(String id){
        return tenantRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Tenant not found with id: " + id));
    }

    public String tenantDelete(String id){
        if(tenantRepository.existsById(id)){
            tenantRepository.deleteById(id);

        }
        return "deleted sucessfully";
    }

}
