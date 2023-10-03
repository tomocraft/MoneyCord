package si.f5.moneycord.moneycord.Minecraft.CommandsProcess.MoneyCord;

import net.dv8tion.jda.api.entities.User;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.io.File;
import java.io.IOException;

public class Utils {

    public void blacklistAdd(User user, String reason) {
        String userName = user.getName();
        String userId = user.getId();
        String addReason = "none";
        if (!reason.isEmpty()) {
            addReason = reason;
        }
        File dir = MoneyCord.plugin.getDataFolder();
        File file = new File(dir, "linking.yml");
        if (!dir.exists()) dir.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration blacklist = YamlConfiguration.loadConfiguration(file);
        blacklist.set("Blacklist." + userId, "UserName:" + userName + ", Reason:" + addReason);
        try {
            blacklist.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void blacklistAdd(OfflinePlayer player, String reason) {
        String playerName = player.getName();
        String playerUuid = String.valueOf(player.getUniqueId());
        String addReason = "none";
        if (!reason.isEmpty()) {
            addReason = reason;
        }
        File dir = MoneyCord.plugin.getDataFolder();
        File file = new File(dir, "linking.yml");
        if (!dir.exists()) dir.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration blacklist = YamlConfiguration.loadConfiguration(file);
        blacklist.set("Blacklist." + playerUuid, "PlayerName:" + playerName + ", Reason:" + addReason);
        try {
            blacklist.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void blacklistRemove(User user) {
        String userId = user.getId();
        File dir = MoneyCord.plugin.getDataFolder();
        File file = new File(dir, "linking.yml");
        if (!dir.exists()) dir.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration blacklist = YamlConfiguration.loadConfiguration(file);
        blacklist.set("Blacklist." + userId, null);
        try {
            blacklist.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void blacklistRemove(OfflinePlayer player) {
        String playerUuid = String.valueOf(player.getUniqueId());
        File dir = MoneyCord.plugin.getDataFolder();
        File file = new File(dir, "linking.yml");
        if (!dir.exists()) dir.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration blacklist = YamlConfiguration.loadConfiguration(file);
        blacklist.set("Blacklist." + playerUuid, null);
        try {
            blacklist.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void linkedDataDelete(String userId) {
        File dir = MoneyCord.plugin.getDataFolder();
        File file = new File(dir, "linking.yml");
        if (!dir.exists()) dir.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        String uuid = config.getString("LinkedDataUserToUuid." + userId);
        config.set("LinkedDataUserToUuid." + userId, null);
        config.set("LinkedDataUuidToUser." + uuid, null);
        try {
            config.save(file);
        } catch (IOException e) {
        }
    }

    public void linkedDataDelete(OfflinePlayer player) {
        String playerUuid = String.valueOf(player.getUniqueId());
        File dir = MoneyCord.plugin.getDataFolder();
        File file = new File(dir, "linking.yml");
        if (!dir.exists()) dir.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        String userId = config.getString("LinkedDataUuidToUser." + playerUuid);
        config.set("LinkedDataUuidToUser." + playerUuid, null);
        config.set("LinkedDataUserToUuid." + userId, null);
        try {
            config.save(file);
        } catch (IOException e) {
        }
    }

}
