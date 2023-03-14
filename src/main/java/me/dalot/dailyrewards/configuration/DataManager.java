package me.dalot.dailyrewards.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.dalot.dailyrewards.cooldown.CooldownManager;
import me.dalot.dailyrewards.database.MySQLManager;
import me.dalot.dailyrewards.enums.Config;
import me.dalot.dailyrewards.reward.RewardType;
import me.dalot.dailyrewards.user.UserHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DataManager {

    @Getter
    @Setter
    private static boolean usingMysql;

    @SneakyThrows
    public static void setValues(final UUID id, Map<String, Object> data) {
        if (isUsingMysql()) {
            try {
                MySQLManager.updateCooldown(id, data);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        final PlayerData playerData = PlayerData.getConfig(id);
        final ConfigurationSection rewardsSection = playerData.getConfigurationSection("rewards");
        for (Map.Entry<String, Object> entry : data.entrySet())
            rewardsSection.set(entry.getKey(), entry.getValue());

        playerData.save();
    }

    public static void createPlayer(final Player player){
        if (isUsingMysql()) MySQLManager.createPlayer(player.getUniqueId().toString());
        else
        if (!PlayerData.exists(player.getUniqueId())){
            final PlayerData playerData = PlayerData.getConfig(player.getUniqueId());
            final ConfigurationSection rewardsSection = playerData.createSection("rewards");

            final long currentTimeInMillis = System.currentTimeMillis();
            if (!Config.DAILY_AVAILABLE_AFTER_FIRST_JOIN.asBoolean()) rewardsSection.set(RewardType.DAILY.toString(), Config.DAILY_COOLDOWN.asLong() + currentTimeInMillis);
            if (!Config.WEEKLY_AVAILABLE_AFTER_FIRST_JOIN.asBoolean()) rewardsSection.set(RewardType.WEEKLY.toString(), Config.WEEKLY_COOLDOWN.asLong() + currentTimeInMillis);
            if (!Config.MONTHLY_AVAILABLE_AFTER_FIRST_JOIN.asBoolean()) rewardsSection.set(RewardType.MONTHLY.toString(), Config.MONTHLY_COOLDOWN.asLong() + currentTimeInMillis);
            playerData.save();
        }
    }

    public static Collection<RewardType> getAvailableRewards(final Player player) {
        final Collection<RewardType> availableRewards = new HashSet<>();

        Arrays.stream(RewardType.values()).collect(Collectors.toList()).forEach(rewardType -> {
            if (!rewardType.isEnabled()) return;
            if (!CooldownManager.isRewardAvailable(player, rewardType)) return;
            final String rewardName = rewardType.toString().toLowerCase();
            if ((!player.hasPermission(String.format("dailyreward.%s", rewardName))
                    && !player.hasPermission(String.format("dailyreward.%s.premium", rewardName))))
                return;

            availableRewards.add(rewardType);
        });

        UserHandler.setUsersAvailableRewards(player.getUniqueId(), (short) availableRewards.size());

        return availableRewards;
    }

    public static long getLong(final UUID id, final RewardType type) {
        return isUsingMysql()
                ? MySQLManager.getRewardsCooldown(id, type)
                : Optional.ofNullable(PlayerData.getConfig(id).getConfigurationSection("rewards"))
                .map(section -> section.getLong(type.toString()))
                .orElse(0L);
    }
}
