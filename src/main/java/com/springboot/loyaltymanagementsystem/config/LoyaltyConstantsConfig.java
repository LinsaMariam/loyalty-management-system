package com.springboot.loyaltymanagementsystem.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class LoyaltyConstantsConfig {

    private final int BONUS_POINT = 50;
    private final String INITIAL_TIER = "Bronze";

    private final float GOLD_RATE = 0.03f;
    private final float SILVER_RATE = 0.025f;
    private final float DEFAULT_RATE = 0.02f;

    private final float GOLD_MAX_POINTS = 100f;
    private final float SILVER_MAX_POINTS = 75f;
    private final float DEFAULT_MAX_POINTS = 50f;

    private final float GOLD_MEMBER_POINTS = 200f;
    private final float SILVER_MEMBER_POINTS = 60f;
}