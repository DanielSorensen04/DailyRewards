package me.dalot.dailyrewards.configuration;

import lombok.SneakyThrows;
import me.dalot.dailyrewards.DailyRewardsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class PlayerData extends YamlConfiguration {

    private static final Map<UUID, PlayerData> configurations = new HashMap<>();

    private final UUID uuid;
    private final File file;

    public PlayerData(UUID uniqueId) {
        super();
        this.uuid = uniqueId;
        this.file = new File(
                DailyRewardsPlugin.get().getDataFolder(),
                String.format("userdata%s%s.yml", File.separator, uniqueId.toString()));
        if (file.exists()) this.reload();
    }

    public static boolean exists(UUID id) { return new File(DailyRewardsPlugin.get().getDataFolder(), "userdata" + File.separator + id.toString() + ".yml").exists();}

    public static PlayerData getConfig(UUID uniqueId) {
        synchronized (configurations) {
            if (configurations.containsKey(uniqueId)) return configurations.get(uniqueId);
            final PlayerData dataConfig = new PlayerData(uniqueId);

            configurations.put(uniqueId, dataConfig);
            return dataConfig;
        }
    }

    public static void removeConfigs() {
        final Collection<PlayerData> oldConfigurations = new ArrayList<>(configurations.values());
        synchronized (configurations) {
            oldConfigurations.forEach(PlayerData::discard);
        }
    }

    @SneakyThrows
    private void reload() {
        synchronized (this) {
            this.load(file);
        }
    }

    @SneakyThrows
    public void save() {
        synchronized (this) {
            this.save(file);
        }
    }

    public void discard(boolean save) {
        if (save) this.save();
        synchronized (configurations) {
            configurations.remove(uuid);
        }
    }

    public void discard() {
        this.discard(false);
    }
}

