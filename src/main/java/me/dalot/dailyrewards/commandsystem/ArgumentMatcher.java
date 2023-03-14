package me.dalot.dailyrewards.commandsystem;

import java.util.List;

public interface ArgumentMatcher {
    List<String> filter(List<String> tabCompletions, String argument);
}
