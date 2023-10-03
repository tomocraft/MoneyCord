package si.f5.moneycord.moneycord.Configuration.Blacklists;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CheckBlackList {

    public boolean checkBlacklist(String userId) {
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
        if (config.contains("Blacklist." + userId)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBlacklist(UUID uuid) {
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
        if (config.contains("Blacklist." + uuid)) {
            return true;
        } else {
            return false;
        }
    }

}
