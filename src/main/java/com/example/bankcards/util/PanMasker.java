package com.example.bankcards.util;

import org.springframework.stereotype.Component;

@Component
public class PanMasker {
    public String mask(String pan) {
        return "**** **** **** " + pan.substring(12);
    }
}
