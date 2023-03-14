package me.dalot.dailyrewards.papiresolvers;

import me.dalot.dailyrewards.user.UserHandler;
import me.dalot.dailyrewards.user.UserModel;
import org.bukkit.entity.Player;

import java.util.Optional;

public class AvailableRewardsResolver implements PlaceholderResolver {
    @Override
    public boolean canResolve(String rawPlaceholder) {
        return rawPlaceholder.startsWith("available");
    }

    @Override
    public String resolve(Player p, String rawPlaceholder) {
        Optional<UserModel> user = UserHandler.getUser(p.getUniqueId());
        return user.map(value -> String.valueOf(value.getAvailableRewards())).orElse("0");
    }
}
