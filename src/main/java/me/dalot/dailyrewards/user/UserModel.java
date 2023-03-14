package me.dalot.dailyrewards.user;

import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class UserModel {
    private final Player player;
    private short availableRewards;

    public UserModel(Player player, short availableRewards){
        this.player = player;
        this.availableRewards = availableRewards;
    }
}
