package com.ordermanagement.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContractStatus {
    WAITING("Ожидание"),
    CONFIRMED("Подтверждено"),
    DELIVERING("В пути"),
    REJECT("Отклонено"),
    DELIVERED("Доставлено"),
    ;

    private final String name;
}

