package me.dalot.dailyrewards.hooks;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.dalot.dailyrewards.utils.VersionUtils;

@UtilityClass
public class Hooks {
    @Getter
    private static PlaceholderAPIHook PLACEHOLDER_API_HOOK;
    @Getter private static OraxenHook ORAXEN_HOOK;
    @Getter private static ItemsAdderHook ITEMS_ADDER_HOOK;


    public static void hook(){
        if (VersionUtils.checkPlugin("PlaceholderAPI")) PLACEHOLDER_API_HOOK = new PlaceholderAPIHook();
        ORAXEN_HOOK = new OraxenHook();
        ITEMS_ADDER_HOOK = new ItemsAdderHook();
    }
}
