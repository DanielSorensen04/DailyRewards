package me.dalot.dailyrewards.commandsystem.subcommands;

import me.dalot.dailyrewards.DailyRewardsPlugin;
import me.dalot.dailyrewards.commandsystem.SubCommand;
import me.dalot.dailyrewards.enums.Lang;
import me.dalot.dailyrewards.reward.RewardType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ClaimCommand implements SubCommand {
    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getDescription() {
        return "Claim en reward";
    }

    @Override
    public String getSyntax() {
        return "/reward claim <daily|weekly|monthly>";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public List<String> getTabCompletion(CommandSender sender, int index, String[] args) {
        List<String> commands = Arrays.stream(RewardType.values()).map(reward -> reward.toString()).collect(Collectors.toList());
        commands.remove("all");
        return commands;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        RewardType rewardType;
        try {
            rewardType = RewardType.valueOf(args[0].toUpperCase(Locale.ENGLISH));
        } catch (Exception exception){
            sender.sendMessage(Lang.VALID_COMMAND_USAGE.asColoredString().replace("%usage%", getSyntax()));
            return;
        }
        if (!(sender instanceof Player)){
            sender.sendMessage("[DailyRewards] Denne kommando kan kun bruges som spiller!");
            return;
        }
        DailyRewardsPlugin.getRewardManager().claim((Player) sender, rewardType, true, true);
    }
}
