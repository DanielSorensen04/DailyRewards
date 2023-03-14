package me.dalot.dailyrewards;

import lombok.Getter;
import lombok.Setter;
import me.dalot.dailyrewards.commandsystem.commands.RewardMainCommand;
import me.dalot.dailyrewards.commandsystem.commands.RewardsMainCommand;
import me.dalot.dailyrewards.configuration.PlayerData;
import me.dalot.dailyrewards.database.MySQLManager;
import me.dalot.dailyrewards.enums.Config;
import me.dalot.dailyrewards.guis.MenuManager;
import me.dalot.dailyrewards.hooks.Hooks;
import me.dalot.dailyrewards.listeners.InventoryClickListener;
import me.dalot.dailyrewards.listeners.ItemsAdderLoadDataListener;
import me.dalot.dailyrewards.listeners.PlayerJoinQuitListener;
import me.dalot.dailyrewards.reward.RewardManager;
import me.dalot.dailyrewards.reward.RewardType;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DailyRewardsPlugin extends JavaPlugin {

    @Getter @Setter public static DailyRewardsPlugin plugin;
    @Getter @Setter private static ConsoleCommandSender console;

    @Getter @Setter private static RewardManager rewardManager;
    @Getter @Setter private static MenuManager menuManager;
    @Getter @Setter private static PluginManager pluginManager;

    public static DailyRewardsPlugin get() {
        return DailyRewardsPlugin.plugin;
    }

    @Override
    public void onEnable() {
        setPlugin(this);

        setConsole(getServer().getConsoleSender());
        setPluginManager(getServer().getPluginManager());

        Hooks.hook();

        Config.reload();

        MySQLManager.init();
        DailyRewardsPlugin.setRewardManager(new RewardManager());
        DailyRewardsPlugin.setMenuManager(new MenuManager());

        registerCommands();
        implementListeners();
    }

    @Override
    public void onDisable() {
        PlayerData.removeConfigs();
    }

    private void registerCommands() {
        new RewardMainCommand().registerMainCommand(this, "reward");
        new RewardsMainCommand().registerMainCommand(this, "rewards");
    }

    private void implementListeners() {
        getPluginManager().registerEvents(InventoryClickListener.getInstance(), this);
        getPluginManager().registerEvents(PlayerJoinQuitListener.getInstance(), this);
        getPluginManager().registerEvents(ItemsAdderLoadDataListener.getInstance(), this);
    }

    public static String isPremium(final Player player, final RewardType type) {
        return player.hasPermission(String.format("dailyreward.%s.premium", type)) ? "_PREMIUM" : "";
    }

}
