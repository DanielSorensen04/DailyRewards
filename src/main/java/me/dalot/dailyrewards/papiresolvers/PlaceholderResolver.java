package me.dalot.dailyrewards.papiresolvers;

import org.bukkit.entity.Player;

public interface PlaceholderResolver {
    boolean canResolve(String rawPlaceholder);
    String resolve(Player p, String rawPlaceholder);
}
