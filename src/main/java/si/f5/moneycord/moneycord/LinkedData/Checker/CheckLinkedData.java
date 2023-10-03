package si.f5.moneycord.moneycord.LinkedData.Checker;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class CheckLinkedData {

    public UUID CheckUserIdToUUID(String userId) {
        UUID uuid = null;
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
        try {
            uuid = UUID.fromString(config.getString("LinkedDataUserToUuid." + userId));
        } catch (Exception e) {
        }
        return uuid;
    }

    public String CheckUUIDToUserId(UUID uuid) {
        String userId = null;
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
        try {
            userId = config.getString("LinkedDataUuidToUser." + uuid.toString());
        } catch (Exception e) {
        }
        return userId;
    }

}
