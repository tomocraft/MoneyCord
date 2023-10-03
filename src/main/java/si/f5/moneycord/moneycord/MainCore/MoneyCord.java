package si.f5.moneycord.moneycord.MainCore;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import si.f5.moneycord.moneycord.Configuration.Messages.Messages;
import si.f5.moneycord.moneycord.Economy.Setup.CheckEconomy;
import si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsProcess.info;
import si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsProcess.linking_mlink;
import si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsProcess.money;
import si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsProcess.pay;
import si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsRegister.SlashCommandRegister;
import si.f5.moneycord.moneycord.Minecraft.CommandsProcess.Linking.linking;
import si.f5.moneycord.moneycord.Minecraft.CommandsProcess.MoneyCord.CoreProcess;
import si.f5.moneycord.moneycord.Minecraft.CommandsProcess.MoneyCord.TabCompleter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MoneyCord extends JavaPlugin {

    public static MoneyCord plugin;

    public static Economy economy = null;

    public static JDA jda;

    public static Map<UUID, String> playerCodeMap = new HashMap<>();

    @Override
    public void onEnable() {
        loadConfig();
        plugin = this;
        saveResourceIfNotExists("linking.yml", false);
        saveResourceIfNotExists("message.yml", false);
        saveResourceIfNotExists("Languages/message-en.yml", false);
        saveResourceIfNotExists("Languages/message-ja.yml", false);
        saveResourceIfNotExists("Languages/message-es.yml", false);
        saveResourceIfNotExists("Languages/message-fr.yml", false);
        saveResourceIfNotExists("Languages/message-de.yml", false);
        saveResourceIfNotExists("Languages/message-it.yml", false);
        saveResourceIfNotExists("Languages/message-pt.yml", false);
        saveResourceIfNotExists("Languages/message-nl.yml", false);
        saveResourceIfNotExists("Languages/message-ru.yml", false);
        saveResourceIfNotExists("Languages/message-zh.yml", false);
        saveResourceIfNotExists("Languages/message-ko.yml", false);
        saveResourceIfNotExists("Avatar/Alex.png", false);

        try {
            try {
                jda = JDABuilder.createDefault(getConfig().getString("Discord-Bot.Token"))
                        .setStatus(new SlashCommandRegister().statusCheckConfig())
                        .setActivity(new SlashCommandRegister().activityCheckConfig())
                        .addEventListeners(new info(), new linking_mlink(), new money(), new pay())
                        .build().awaitReady();
            } catch (InvalidTokenException e) {
                Logger logger = getLogger();
                logger.log(Level.SEVERE, "The provided token is invalid!");
            }

            List<String> guildIdList = getConfig().getStringList("Discord-Bot.Guild-ID");

            for (String s : guildIdList) {
                try {
                    Guild guild = jda.getGuildById(s);
                    new SlashCommandRegister().registerSlashCommand(guild);
                } catch (Exception e) {
                    Logger logger = Bukkit.getLogger();
                    logger.warning(s + " is invalid guild ID.");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!new CheckEconomy().setupEconomy()) {
            getLogger().severe(String.format(new Messages().getMessage("Messages.didNotFindVault")));
            getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("linking").setExecutor(new linking());
        getCommand("linking").setTabCompleter(new linking());
        getCommand("moneycord").setExecutor(new CoreProcess());
        getCommand("moneycord").setTabCompleter(new TabCompleter());

    }

    @Override
    public void onDisable() {
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void saveResourceIfNotExists(String resourceFileName, Boolean replace) {
        File file = new File(getDataFolder(), resourceFileName);
        if (!file.exists()) {
            saveResource(resourceFileName, replace);
        }
    }

}
