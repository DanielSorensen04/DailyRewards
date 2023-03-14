package me.dalot.dailyrewards.utils;

import lombok.experimental.UtilityClass;
import me.dalot.dailyrewards.DailyRewardsPlugin;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

@UtilityClass
public class AudienceUtils {
    public static void playSound(Player player, String sound){
        Sound soundToPlay;
        try {
            soundToPlay = Sound.valueOf(sound);
        } catch (IllegalArgumentException | NullPointerException ex){
            soundToPlay = VersionUtils.isLegacyVersion() ? Sound.valueOf("NOTE_PLING") : Sound.valueOf("BLOCK_NOTE_BLOCK_HARP");
        }
        player.playSound(player.getLocation(), soundToPlay, 2f, 2f);
    }

    public static void spawnFirework(Location location){
        Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        fw.setMetadata("nodamage", new FixedMetadataValue(DailyRewardsPlugin.get(), true));
        FireworkMeta meta = fw.getFireworkMeta();
        meta.addEffect(FireworkEffect.builder().withColor(Color.ORANGE, Color.YELLOW).withFade(Color.WHITE).with(FireworkEffect.Type.BALL).build());
        meta.setPower(1);
        fw.setFireworkMeta(meta);
    }
}
