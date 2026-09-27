package com.example.product_service.modules.auth.service;

import com.example.product_service.modules.auth.entity.User;
import com.example.product_service.modules.auth.repository.UserRepository;
import com.example.product_service.modules.tenant.entity.Tenant;
import com.example.product_service.modules.tenant.repository.TenantRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;

    public CustomUserDetailsService(TenantRepository tenantRepository, UserRepository userRepository) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //1.check if logging email belongs to tenant admin
        Tenant tenant = tenantRepository.findByAdminEmail(email);
        if(tenant != null){
            return org.springframework.security.core.userdetails.User
                    .withUsername(tenant.getAdminEmail())
                    .password(tenant.getAdminPassword())
                    .roles("Tenant_ADMIN")
                    .build();
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "User not found: "+ email
                ));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }
}
