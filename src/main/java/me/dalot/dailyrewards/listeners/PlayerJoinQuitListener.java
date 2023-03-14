package me.dalot.dailyrewards.listeners;

import lombok.Getter;
import me.dalot.dailyrewards.DailyRewardsPlugin;
import me.dalot.dailyrewards.configuration.DataManager;
import me.dalot.dailyrewards.enums.Config;
import me.dalot.dailyrewards.enums.Lang;
import me.dalot.dailyrewards.reward.RewardType;
import me.dalot.dailyrewards.user.UserHandler;
import me.dalot.dailyrewards.user.UserModel;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.HashMap;

public class PlayerJoinQuitListener implements Listener {

    @Getter public static final PlayerJoinQuitListener instance = new PlayerJoinQuitListener();

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        DataManager.createPlayer(player);
        final Collection<RewardType> availableRewards = DataManager.getAvailableRewards(player);

        UserModel user = UserHandler.addUser(
                new UserModel(
                        player,
                        (short) availableRewards.size()
                )
        );

        if (user.getAvailableRewards() <= 0) return;

        if (Config.AUTO_CLAIM_REWARDS_ON_JOIN.asBoolean()) {
            if (!player.hasPermission("dailyreward.autoclaim")) return;
            Bukkit.getScheduler().runTaskLater(DailyRewardsPlugin.get(), () ->
                    DailyRewardsPlugin.getRewardManager().autoClaim(player, availableRewards), 3);
            return;
        }

        if (!Config.ENABLE_JOIN_NOTIFICATION.asBoolean()) return;
        Bukkit.getScheduler().runTaskLater(
                DailyRewardsPlugin.get(),
                () -> Lang.JOIN_NOTIFICATION.asReplacedList(new HashMap<String, String>() {{put("%rewards%", String.valueOf(user.getAvailableRewards()));}})
                        .stream()
                        .map(TextComponent::new)
                        .forEach(joinMsg -> {
                            joinMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rewards"));
                            joinMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    new ComponentBuilder(Lang.JOIN_HOVER_MESSAGE.asPlaceholderReplacedText(player)).create()));

                            player.spigot().sendMessage(joinMsg);
                        }), Config.JOIN_NOTIFICATION_DELAY.asInt() * 20L);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onQuit(final PlayerQuitEvent event) {
        UserHandler.removeUser(event.getPlayer().getUniqueId());
    }
}
