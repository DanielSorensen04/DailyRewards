package me.dalot.dailyrewards.enums;

import com.google.common.base.Splitter;
import dev.dbassett.skullcreator.SkullCreator;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import lombok.RequiredArgsConstructor;
import me.dalot.dailyrewards.DailyRewardsPlugin;
import me.dalot.dailyrewards.configuration.YamlFile;
import me.dalot.dailyrewards.hooks.Hooks;
import me.dalot.dailyrewards.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import io.th0rgal.oraxen.api.OraxenItems;

import java.util.*;

@RequiredArgsConstructor
public enum Config {
    MENU_SIZE("menu-size"),
    FILL_BACKGROUND("fill-background-enabled"),
    BACKGROUND_ITEM("background-item"),
    ENABLE_JOIN_NOTIFICATION("enable-join-notification"),
    ANNOUNCE_ENABLED("announce-enabled"),
    JOIN_NOTIFICATION_DELAY("join-notification-delay"),
    UNAVAILABLE_REWARD_SOUND("unavailable-reward-sound"),
    AUTO_CLAIM_REWARDS_ON_JOIN("auto-claim-rewards-on-join"),

    USE_MYSQL("use-mysql"),
    MYSQL_IP("mysql-ip"),
    MYSQL_PORT("mysql-port"),
    MYSQL_DBNAME("mysql-database-name"),
    MYSQL_USERNAME("mysql-username"),
    MYSQL_PASSWORD("mysql-password"),

    DAILY_ENABLED("daily-enabled"),
    DAILY_COOLDOWN("daily-cooldown"),
    DAILY_COOLDOWN_FORMAT("daily-cooldown-format"),
    DAILY_AVAILABLE_AFTER_FIRST_JOIN("daily-available-after-first-join"),
    DAILY_PLACEHOLDER("daily-placeholder"),
    DAILY_POSITION("daily-position"),
    DAILY_SOUND("daily-sound"),
    DAILY_AVAILABLE_ITEM("daily-available-item"),
    DAILY_UNAVAILABLE_ITEM("daily-unavailable-item"),
    DAILY_REWARDS("daily-rewards"),
    DAILY_PREMIUM_REWARDS("daily-premium-rewards"),

    WEEKLY_ENABLED("weekly-enabled"),
    WEEKLY_COOLDOWN("weekly-cooldown"),
    WEEKLY_COOLDOWN_FORMAT("weekly-cooldown-format"),
    WEEKLY_AVAILABLE_AFTER_FIRST_JOIN("weekly-available-after-first-join"),
    WEEKLY_PLACEHOLDER("weekly-placeholder"),
    WEEKLY_AVAILABLE_ITEM("weekly-available-item"),
    WEEKLY_UNAVAILABLE_ITEM("weekly-unavailable-item"),
    WEEKLY_POSITION("weekly-position"),
    WEEKLY_SOUND("weekly-sound"),
    WEEKLY_REWARDS("weekly-rewards"),
    WEEKLY_PREMIUM_REWARDS("weekly-premium-rewards"),

    MONTHLY_ENABLED("monthly-enabled"),
    MONTHLY_COOLDOWN("monthly-cooldown"),
    MONTHLY_COOLDOWN_FORMAT("monthly-cooldown-format"),
    MONTHLY_AVAILABLE_AFTER_FIRST_JOIN("monthly-available-after-first-join"),
    MONTHLY_PLACEHOLDER("monthly-placeholder"),
    MONTHLY_AVAILABLE_ITEM("monthly-available-item"),
    MONTHLY_UNAVAILABLE_ITEM("monthly-unavailable-item"),
    MONTHLY_POSITION("monthly-position"),
    MONTHLY_SOUND("monthly-sound"),
    MONTHLY_REWARDS("monthly-rewards"),
    MONTHLY_PREMIUM_REWARDS("monthly-premium-rewards"),
    CHECK_FOR_FULL_INVENTORY("check-for-full-inventory");

    private static final Map<String, String> messages = new HashMap<>();
    private static final Map<String, String> listsStoredAsStrings = new HashMap<>();
    private static final Map<String, ItemStack> items = new HashMap<>();

    private final String text;

    public static void reload() {
        final YamlConfiguration configuration = new YamlFile("config.yml",
                DailyRewardsPlugin.get().getDataFolder())
                .getConfiguration();

        final ConfigurationSection configurationSection = configuration.getConfigurationSection("config");
        Objects.requireNonNull(configurationSection)
                .getKeys(false)
                .forEach(key -> {
                    if (key.endsWith("lore") || key.endsWith("rewards") || key.endsWith("notifications") || key.endsWith("help")) {
                        listsStoredAsStrings.put(key, String.join("⎶", configurationSection.getStringList(key)));
                    } else messages.put(key, configurationSection.getString(key));
                });

        loadItems(configurationSection);

        Lang.reload();
    }

    public static void loadItems(ConfigurationSection configurationSection){
        configurationSection
                .getKeys(false)
                .forEach(key -> {
                    if (key.endsWith("item")) {
                        final String itemName = configurationSection.getString(key);
                        if (itemName.length() > 64) {
                            items.put(key, SkullCreator.itemFromBase64(itemName));
                        } else if (Hooks.getITEMS_ADDER_HOOK().isOn() && ItemsAdder.isCustomItem(itemName)) {
                            items.put(key, CustomStack.getInstance(itemName).getItemStack());
                        } else if (Hooks.getORAXEN_HOOK().isOn() && OraxenItems.exists(itemName)) {
                            items.put(key, OraxenItems.getItemById(itemName).build());
                        } else {
                            try {
                                items.put(key, new ItemStack(Material.valueOf(itemName.toUpperCase(Locale.ENGLISH))));
                            } catch (IllegalArgumentException ex){
                                items.put(key, new ItemStack(Material.STONE));
                            }
                        }
                    }
                });
    }

    public List<String> asReplacedList(final Map<String, String> definitions) {
        return Splitter.on("⎶").splitToList(StringUtils.replaceString(listsStoredAsStrings.get(this.text), definitions));
    }

    public String asString() {
        return messages.get(text);
    }
    public ItemStack asAnItem(){
        return items.get(this.text);
    }
    public String asUppercase() {
        return this.asString().toUpperCase();
    }

    public boolean asBoolean() {
        return Boolean.parseBoolean(messages.get(text));
    }

    public long asLong() {
        return Long.parseLong(messages.get(text)) * 3600000;
    }

    public int asInt() {
        return Integer.parseInt(messages.get(text));
    }
}
