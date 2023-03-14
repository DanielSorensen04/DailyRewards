package me.dalot.dailyrewards.commandsystem.commands;

import me.dalot.dailyrewards.commandsystem.AbstractCommand;
import me.dalot.dailyrewards.commandsystem.argumentmatchers.StartingWithStringAM;
import me.dalot.dailyrewards.commandsystem.subcommands.ClaimCommand;
import me.dalot.dailyrewards.commandsystem.subcommands.HelpCommand;
import me.dalot.dailyrewards.commandsystem.subcommands.ReloadCommand;
import me.dalot.dailyrewards.commandsystem.subcommands.ResetCommand;
import me.dalot.dailyrewards.enums.Lang;
import me.dalot.dailyrewards.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class RewardMainCommand extends AbstractCommand {
    public RewardMainCommand() {
        super(Lang.PERMISSION_MESSAGE.asColoredString(), new StartingWithStringAM());
    }

    @Override
    protected void registerSubCommands() {
        subCommands.add(new ClaimCommand());
        subCommands.add(new HelpCommand());
        subCommands.add(new ReloadCommand());
        subCommands.add(new ResetCommand());
    }

    @Override
    protected void perform(CommandSender sender) {
        StringUtils.sendListToPlayer(sender, Lang.HELP_MESSAGE.asReplacedList(Collections.emptyMap()));
    }
}
