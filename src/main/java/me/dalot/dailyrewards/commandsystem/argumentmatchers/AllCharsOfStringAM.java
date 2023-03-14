package me.dalot.dailyrewards.commandsystem.argumentmatchers;

import me.dalot.dailyrewards.commandsystem.ArgumentMatcher;

import java.util.ArrayList;
import java.util.List;

public class AllCharsOfStringAM implements ArgumentMatcher {
    @Override
    public List<String> filter(List<String> tabCompletions, String argument) {
        List<String> result = new ArrayList<>();

        for (String tabCompletion : tabCompletions) {
            boolean passes = true;

            for (char c : argument.toCharArray()) {
                passes = tabCompletion.contains(String.valueOf(c));

                if (!passes)
                    break;
            }

            if (passes)
                result.add(tabCompletion);
        }

        return result;
    }
}
