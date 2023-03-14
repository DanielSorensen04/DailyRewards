package me.dalot.dailyrewards.commandsystem.argumentmatchers;

import me.dalot.dailyrewards.commandsystem.ArgumentMatcher;

import java.util.List;
import java.util.stream.Collectors;

public class ContainingStringAM implements ArgumentMatcher {
    @Override
    public List<String> filter (List<String> tabCompletions, String argument) {
        return tabCompletions.stream().filter(tabCompletion -> tabCompletion.contains(argument)).collect(Collectors.toList());
    }
}
