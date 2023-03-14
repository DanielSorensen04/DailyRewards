package me.dalot.dailyrewards.listeners;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import lombok.Getter;
import me.dalot.dailyrewards.DailyRewardsPlugin;
import me.dalot.dailyrewards.configuration.YamlFile;
import me.dalot.dailyrewards.enums.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class ItemsAdderLoadDataListener implements Listener {

    @Getter
    public static final InventoryClickListener instance = new InventoryClickListener();

    @EventHandler
    public void onItemsAdderLoad(final ItemsAdderLoadDataEvent event){
        Config.loadItems(Objects.requireNonNull(new YamlFile("config.yml",
                DailyRewardsPlugin.get().getDataFolder())
                .getConfiguration().getConfigurationSection("config")));
    }
}
