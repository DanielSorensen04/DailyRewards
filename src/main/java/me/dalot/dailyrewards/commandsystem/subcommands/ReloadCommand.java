package me.dalot.dailyrewards.commandsystem.subcommands;

import me.dalot.dailyrewards.commandsystem.SubCommand;
import me.dalot.dailyrewards.enums.Config;
import me.dalot.dailyrewards.enums.Lang;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloader pluginnets konfiguration";
    }

    @Override
    public String getSyntax() {
        return "/reward reload";
    }

    @Override
    public String getPermission() {
        return "dailyreward.manage";
    }

    @Override
    public List<String> getTabCompletion(CommandSender commandSender, int index, String[] args) {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Config.reload();
        Lang.reload();
        sender.sendMessage(Lang.RELOAD_MESSAGE.asColoredString());
    }
}
