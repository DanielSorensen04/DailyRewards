package me.dalot.dailyrewards.commandsystem.subcommands;

import me.dalot.dailyrewards.DailyRewardsPlugin;
import me.dalot.dailyrewards.commandsystem.SubCommand;
import me.dalot.dailyrewards.enums.Lang;
import me.dalot.dailyrewards.reward.RewardType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResetCommand implements SubCommand {
    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public String getDescription() {
        return "Reset en reward";
    }

    @Override
    public String getSyntax() {
        return "/reward reset <spiller> <daily|weekly|monthly|all>";
    }

    @Override
    public String getPermission() {
        return "dailyreward.manage";
    }

    @Override
    public List<String> getTabCompletion(CommandSender sender, int index, String[] args) {
        switch (index){
            case 0: return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
            case 1: return Arrays.stream(RewardType.values()).map(RewardType::toString).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length != 2){
            sender.sendMessage(Lang.VALID_COMMAND_USAGE.asColoredString().replace("%usage%", getSyntax()));
            return;
        }
        sender.sendMessage(DailyRewardsPlugin.getRewardManager().resetPlayer(Bukkit.getOfflinePlayer(args[0]), args[1]));
    }
}
