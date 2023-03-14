package me.dalot.dailyrewards.user;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;


public class UserHandler {
    private static final HashMap<UUID, UserModel> usersHashMap = new HashMap<>();
    public static UserModel addUser(final UserModel user){
        usersHashMap.put(user.getPlayer().getUniqueId(), user);
        return user;
    }

    public static void setUsersAvailableRewards(final UUID uuid, short availableRewards){
        Optional<UserModel> userOptional = Optional.ofNullable(usersHashMap.getOrDefault(uuid, null));
        userOptional.ifPresent(user -> user.setAvailableRewards(availableRewards));
    }

    public static Optional<UserModel> getUser(final UUID uuid){
        return Optional.of(usersHashMap.get(uuid));
    }

    public static UserModel removeUser(final UUID uuid){
        return usersHashMap.remove(uuid);
    }
}
