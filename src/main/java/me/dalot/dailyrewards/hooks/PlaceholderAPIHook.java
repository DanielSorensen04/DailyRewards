package me.dalot.dailyrewards.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dalot.dailyrewards.DailyRewardsPlugin;
import me.dalot.dailyrewards.papiresolvers.AvailableRewardsResolver;
import me.dalot.dailyrewards.papiresolvers.PlaceholderResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class PlaceholderAPIHook extends PlaceholderExpansion implements HookModel<PlaceholderExpansion> {
    private final HashSet<PlaceholderResolver> resolvers = new HashSet<>();
    private boolean isHooked = false;

    PlaceholderAPIHook() {
        tryToHook();
    }

    private void tryToHook() {
        this.register();
        isHooked = true;
        registerDefaultResolvers();
    }

    private void registerDefaultResolvers() {
        registerResolver(new AvailableRewardsResolver());
    }

    public void registerResolver(PlaceholderResolver resolver) {
        resolvers.add(resolver);
    }

    @Override
    public boolean isOn() {
        return isHooked;
    }

    @Override
    public PlaceholderExpansion getApi() {
        return isHooked ? this : null;
    }

    @Override
    public String getIdentifier() {
        return "dailyrewards";
    }

    @Override
    public String getAuthor() {
        return DailyRewardsPlugin.get().getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return DailyRewardsPlugin.get().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        String resolvedStr = null;

        for (PlaceholderResolver resolver : resolvers) {
            if (resolver.canResolve(identifier)) {
                resolvedStr = resolver.resolve(player, identifier);
                break;
            }
        }

        return resolvedStr;
    }
}
