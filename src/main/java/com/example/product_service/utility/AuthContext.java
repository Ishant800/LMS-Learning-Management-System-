package com.example.product_service.utility;

public class AuthContext {
    private static final ThreadLocal<String> tenantHolder = new ThreadLocal<>();
    private static final ThreadLocal<Long> userHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> roleHolder = new ThreadLocal<>();

    public static void set(String tenantId,Long userId,String role){
        tenantHolder.set(tenantId);
        userHolder.set(userId);
        roleHolder.set(role);
    }

    public static String getTenantId(){
        return tenantHolder.get();
    }
    public static Long getUserId(){
        return userHolder.get();
    }

    public static String getRole(){
        return roleHolder.get();
    }

    public static void clear(){
        tenantHolder.remove();
        userHolder.remove();
        roleHolder.remove();
    }
}
