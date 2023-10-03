package si.f5.moneycord.moneycord.Languages;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.moneycord.moneycord.Configuration.Messages.Messages;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ChangeLanguageProcess {

    public void changeLanguage(String language, CommandSender sender) {
        if (language.equalsIgnoreCase("english")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-en.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("japanese")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-ja.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("spanish")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-es.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("french")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-fr.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("german")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-de.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("italian")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-it.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("portuguese")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-pt.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("dutch")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-nl.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("russian")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-ru.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("chinese")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-zh.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (language.equalsIgnoreCase("korean")) {
            File sourceFile = new File(MoneyCord.plugin.getDataFolder(), "Languages/message-ko.yml");
            File destinationFile = new File(MoneyCord.plugin.getDataFolder(), "message.yml");
            String color1 = new Messages().getMessage("Messages.MoneyCommand.Color");
            String color2 = new Messages().getMessage("Messages.LinkCommand.Color");
            String color3 = new Messages().getMessage("Messages.PayCommand.Color");
            String color4 = new Messages().getMessage("Messages.InfoCommand.Color");
            destinationFile.delete();
            Path sourcePath = Paths.get(sourceFile.getPath());
            Path targetPath = Paths.get(destinationFile.getPath());
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                Logger.getLogger("MoneyCord").warning("Language settings file not found.");
            }
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
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("Messages.MoneyCommand.Color", color1);
            config.set("Messages.LinkCommand.Color", color2);
            config.set("Messages.PayCommand.Color", color3);
            config.set("Messages.InfoCommand.Color", color4);
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            String message = new Messages().getMessage("Messages.argumentIsInvalid");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            sender.sendMessage(ChatColor.RED + "The specified language is not supported.");
        }
    }

}
