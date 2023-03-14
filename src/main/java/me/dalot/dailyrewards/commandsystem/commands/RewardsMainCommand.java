package me.dalot.dailyrewards.commandsystem.commands;

import me.dalot.dailyrewards.DailyRewardsPlugin;
import me.dalot.dailyrewards.commandsystem.AbstractCommand;
import me.dalot.dailyrewards.commandsystem.argumentmatchers.StartingWithStringAM;
import me.dalot.dailyrewards.enums.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardsMainCommand extends AbstractCommand {
    public RewardsMainCommand() {
        super(Lang.PERMISSION_MESSAGE.asColoredString(), new StartingWithStringAM());
    }

    @Override
    protected void registerSubCommands() {

    }

    @Override
    protected void perform(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[DailyRewards] Denne kommando kan kun bruges som spiller!");
            return;
        }

        if (sender.hasPermission("dailyreward.use"))
            DailyRewardsPlugin.getMenuManager().openRewardsMenu((Player) sender);
        else
            sender.sendMessage(Lang.PERMISSION_MESSAGE.asColoredString());
    }
}

