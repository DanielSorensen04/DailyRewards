package me.dalot.dailyrewards.configuration;

import com.tchristofferson.configupdater.ConfigUpdater;
import lombok.Getter;
import lombok.SneakyThrows;
import me.dalot.dailyrewards.DailyRewardsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.File;
import java.util.Collections;

public class YamlFile {

    @Getter
    private final File yamlFile;

    @SneakyThrows
    public YamlFile(final String filePath, final File folder) {
        yamlFile = new File(folder, filePath);
        FileConfiguration configuration;

        try {
            configuration = this.getConfiguration();

        } catch (ParserException exception) {
            Bukkit.getLogger().info(String.format("Format exception i filen %s.", yamlFile.getName()));
            return;
        }

        configuration.save(yamlFile);
        configuration.options().copyDefaults(true);
        ConfigUpdater.update(DailyRewardsPlugin.get(),
                filePath,
                yamlFile,
                Collections.emptyList());
    }

    public YamlConfiguration getConfiguration() {
        return YamlConfiguration.loadConfiguration(yamlFile);
    }
}
