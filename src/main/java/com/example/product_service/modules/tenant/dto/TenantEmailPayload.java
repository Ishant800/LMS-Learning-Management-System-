package com.example.product_service.modules.tenant.dto;

import java.io.Serializable;

public record TenantEmailPayload(String email, String orgName) implements Serializable {
}
