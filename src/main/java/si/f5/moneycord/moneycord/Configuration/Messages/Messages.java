package si.f5.moneycord.moneycord.Configuration.Messages;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.io.File;
import java.io.IOException;

public class Messages {

    public String getMessage(String path) {
        String message;
        File dir = MoneyCord.plugin.getDataFolder();
        File file = new File(dir, "message.yml");
        if (!dir.exists()) dir.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration messageData = YamlConfiguration.loadConfiguration(file);
        message = messageData.getString(path);
        return message;
    }

}
