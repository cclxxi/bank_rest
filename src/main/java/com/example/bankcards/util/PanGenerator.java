package com.example.bankcards.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PanGenerator {
    public String generate() {
        return RandomStringUtils.randomNumeric(16);
    }
}
