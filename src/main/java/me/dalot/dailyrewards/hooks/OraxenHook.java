package me.dalot.dailyrewards.hooks;

import me.dalot.dailyrewards.utils.VersionUtils;
import org.jetbrains.annotations.Nullable;

public class OraxenHook implements HookModel<Void> {

    private final boolean isHooked;
    OraxenHook(){
        isHooked = VersionUtils.checkPlugin("Oraxen");
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
