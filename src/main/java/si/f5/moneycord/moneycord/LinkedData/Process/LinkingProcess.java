package si.f5.moneycord.moneycord.LinkedData.Process;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class LinkingProcess {

    public UUID getUUIDByCode(String code) {
        for (Map.Entry<UUID, String> entry : MoneyCord.playerCodeMap.entrySet()) {
            if (entry.getValue().equals(code)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean checkLinkedData(UUID uuid, String userId) {
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
        if (config.contains("LinkedDataUserToUuid." + userId) || config.contains("LinkedDataUuidToUser." + uuid.toString())) {
            return false;
        } else {
            return true;
        }
    }

    public boolean accountLinked(UUID uuid) {
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
        if (config.contains("LinkedDataUuidToUser." + uuid.toString())) {
            return true;
        } else {
            return false;
        }
    }

    public void saveLinkedData(UUID uuid, String userId) {
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
        config.set("LinkedDataUserToUuid." + userId, uuid.toString());
        config.set("LinkedDataUuidToUser." + uuid, userId);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MoneyCord.playerCodeMap.remove(uuid);
    }

}
