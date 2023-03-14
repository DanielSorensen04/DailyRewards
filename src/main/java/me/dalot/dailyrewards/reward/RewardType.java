package me.dalot.dailyrewards.reward;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.dalot.dailyrewards.enums.Config;

import java.util.Arrays;

@RequiredArgsConstructor
public enum RewardType {
    DAILY(Config.DAILY_COOLDOWN.asLong(), Config.DAILY_ENABLED.asBoolean(), Config.DAILY_COOLDOWN_FORMAT.asString()),
    WEEKLY(Config.WEEKLY_COOLDOWN.asLong(), Config.WEEKLY_ENABLED.asBoolean(), Config.WEEKLY_COOLDOWN_FORMAT.asString()),
    MONTHLY(Config.MONTHLY_COOLDOWN.asLong(), Config.MONTHLY_ENABLED.asBoolean(), Config.MONTHLY_COOLDOWN_FORMAT.asString());

    @Getter private final long cooldown;
    @Getter private final boolean enabled;
    @Getter private final String cooldownFormat;

    public static RewardType findByCooldown(long cooldown) {
        return Arrays.stream(RewardType.values())
                .filter(value -> value.getCooldown() == cooldown).findFirst()
                .orElse(null);
    }

    public static RewardType findByName(String name) {
        return Arrays.stream(RewardType.values())
                .filter(value -> value.name().equalsIgnoreCase(name)).findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
