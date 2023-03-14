package me.dalot.dailyrewards.commandsystem.argumentmatchers;

import me.dalot.dailyrewards.commandsystem.ArgumentMatcher;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class StartingWithStringAM implements ArgumentMatcher {
    @Override
    public List<String> filter (List<String> tabCompletions, String argument) {
        List<String> result = new ArrayList<>();

        StringUtil.copyPartialMatches(argument, tabCompletions, result);

        return result;
    }
}
