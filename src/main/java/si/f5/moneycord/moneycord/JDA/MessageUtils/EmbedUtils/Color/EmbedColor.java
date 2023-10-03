package si.f5.moneycord.moneycord.JDA.MessageUtils.EmbedUtils.Color;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EmbedColor {

    public Color RGBEmbedColor(String path) {
        try {
            String colorText;
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
            FileConfiguration colorDataFile = YamlConfiguration.loadConfiguration(file);
            colorText = colorDataFile.getString(path);
            String[] rgbValues = colorText.split(",");
            if (rgbValues.length == 3) {
                int red = Integer.parseInt(rgbValues[0]);
                int green = Integer.parseInt(rgbValues[1]);
                int blue = Integer.parseInt(rgbValues[2]);
                Color color = new Color(red, green, blue);
                return color;
            }
            return Color.BLACK;
        } catch (Exception e) {
            return Color.BLACK;
        }
    }

}
