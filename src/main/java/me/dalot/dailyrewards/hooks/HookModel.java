package me.dalot.dailyrewards.hooks;

import org.jetbrains.annotations.Nullable;

public interface HookModel<T> {
    boolean isOn();

    @Nullable
    T getApi();
}
