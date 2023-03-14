package me.dalot.dailyrewards.utils;

import io.github.g00fy2.versioncompare.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import me.dalot.dailyrewards.DailyRewardsPlugin;
import org.bukkit.Bukkit;

@UtilityClass
public class VersionUtils {
    @Getter @Setter private static boolean hexSupport;
    @Getter @Setter private static boolean legacyVersion;
    @Getter @Setter public static boolean latestVersion;

    static {
        final String serverVersion = Bukkit.getBukkitVersion();
        Version version = new Version(serverVersion);
        setHexSupport(version.isAtLeast("1.16"));
        setLegacyVersion(version.isLowerThan("1.13"));
    }

    public static boolean checkPlugin(String pluginName){
        return DailyRewardsPlugin.getPluginManager().getPlugin(pluginName) != null;
    }
}
