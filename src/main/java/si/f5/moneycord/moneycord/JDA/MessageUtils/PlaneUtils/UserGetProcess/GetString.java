package si.f5.moneycord.moneycord.JDA.MessageUtils.PlaneUtils.UserGetProcess;

import org.bukkit.Bukkit;

import java.util.UUID;

public class GetString {

    public String getMinecraftName(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

}
