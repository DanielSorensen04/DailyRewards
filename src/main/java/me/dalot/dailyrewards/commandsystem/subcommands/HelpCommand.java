package me.dalot.dailyrewards.commandsystem.subcommands;

import me.dalot.dailyrewards.commandsystem.SubCommand;
import me.dalot.dailyrewards.enums.Lang;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class HelpCommand implements SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Sender en liste med kommandoer og instruktioner";
    }

    @Override
    public String getSyntax() {
        return "/reward help";
    }

    @Override
    public String getPermission() {
        return "dailyreward.help";
    }

    @Override
    public List<String> getTabCompletion(CommandSender sender, int index, String[] args) {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Lang.HELP_MESSAGE.asReplacedList(Collections.emptyMap()).forEach(sender::sendMessage);
    }
}
