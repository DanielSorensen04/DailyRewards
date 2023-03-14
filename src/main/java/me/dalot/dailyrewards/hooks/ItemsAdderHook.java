package me.dalot.dailyrewards.hooks;

import me.dalot.dailyrewards.DailyRewardsPlugin;
import me.dalot.dailyrewards.listeners.ItemsAdderLoadDataListener;
import me.dalot.dailyrewards.utils.VersionUtils;
import org.jetbrains.annotations.Nullable;

public class ItemsAdderHook implements HookModel<Void>{
    private final boolean isHooked;
    ItemsAdderHook(){
        isHooked = hook();
        if (isHooked) {
            DailyRewardsPlugin.getPluginManager().registerEvents(new ItemsAdderLoadDataListener(), DailyRewardsPlugin.get());
        }
    }

    private boolean hook(){
        return VersionUtils.checkPlugin("ItemsAdder");
    }

    @Override
    public boolean isOn() {
        return isHooked;
    }

    @Override
    public @Nullable Void getApi() {
        return null;
    }
}
