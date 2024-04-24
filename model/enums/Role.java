package com.ordermanagement.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    ADMIN("Администратор"),
    SUP("Поставщик"),
    MANAGER("Клиент"),
    ;

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}

