package si.f5.moneycord.moneycord.Minecraft.CommandsProcess.MoneyCord;

import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import si.f5.moneycord.moneycord.Configuration.Blacklists.CheckBlackList;
import si.f5.moneycord.moneycord.Configuration.Messages.Messages;
import si.f5.moneycord.moneycord.Languages.ChangeLanguageProcess;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

public class CoreProcess implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!MoneyCord.plugin.getConfig().getBoolean("Minecraft-Commands.Account-Link.Enable")) {
            String message = new Messages().getMessage("Messages.commandIsDisabled");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        if (!commandSender.hasPermission("moneycord.commands.moneycord")) {
            String message = new Messages().getMessage("Messages.doNotHavePermission");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        if (commandSender instanceof Player && new CheckBlackList().checkBlacklist(((Player) commandSender).getUniqueId())) {
            String message = new Messages().getMessage("Messages.blacklistedPlayer");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        if (strings.length == 0) {
            String help_description = new Messages().getMessage("Messages.MoneyCord.help.description");
            String reload_description = new Messages().getMessage("Messages.MoneyCord.reload.description");
            String disable_description = new Messages().getMessage("Messages.MoneyCord.disable.description");
            String delete_description = new Messages().getMessage("Messages.MoneyCord.delete.description");
            String blacklist_description = new Messages().getMessage("Messages.MoneyCord.blacklist.description");
            String language_description = new Messages().getMessage("Messages.MoneyCord.language.description");
            String prefix = "/moneycord ";
            String help = ChatColor.AQUA + prefix + "help\n - " + ChatColor.translateAlternateColorCodes('&', help_description);
            String reload = ChatColor.AQUA + prefix + "reload\n - " + ChatColor.translateAlternateColorCodes('&', reload_description);
            String disable = ChatColor.AQUA + prefix + "disable\n - " + ChatColor.translateAlternateColorCodes('&', disable_description);
            String delete = ChatColor.AQUA + prefix + "delete <minecraft | discord> <Player | UserId>\n - " + ChatColor.translateAlternateColorCodes('&', delete_description);
            String blacklist = ChatColor.AQUA + prefix + "blacklist <add | remove> <minecraft | discord> <Player | UserId> <reason>\n - " + ChatColor.translateAlternateColorCodes('&', blacklist_description);
            String language = ChatColor.AQUA + prefix + "language <language>\n - " + ChatColor.translateAlternateColorCodes('&', language_description);
            commandSender.sendMessage(ChatColor.AQUA + "----- MoneyCord Command Help -----\n" + help + "\n" + reload + "\n" + disable + "\n" + delete + "\n" + blacklist + "\n" + language);
            return true;
        } else if (strings[0].equalsIgnoreCase("language")) {
            if (!(strings[1] == null)) {
                new ChangeLanguageProcess().changeLanguage(strings[1], commandSender);
                commandSender.sendMessage(ChatColor.GREEN + "Successfully changed language!");
            } else {
                String message = new Messages().getMessage("Messages.argumentIsInvalid");
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
            return true;
        } else if (strings[0].equalsIgnoreCase("help")) {
            String help_description = new Messages().getMessage("Messages.MoneyCord.help.description");
            String reload_description = new Messages().getMessage("Messages.MoneyCord.reload.description");
            String disable_description = new Messages().getMessage("Messages.MoneyCord.disable.description");
            String delete_description = new Messages().getMessage("Messages.MoneyCord.delete.description");
            String blacklist_description = new Messages().getMessage("Messages.MoneyCord.blacklist.description");
            String language_description = new Messages().getMessage("Messages.MoneyCord.language.description");
            String prefix = "/moneycord ";
            String help = ChatColor.AQUA + prefix + "help\n - " + ChatColor.translateAlternateColorCodes('&', help_description);
            String reload = ChatColor.AQUA + prefix + "reload\n - " + ChatColor.translateAlternateColorCodes('&', reload_description);
            String disable = ChatColor.AQUA + prefix + "disable\n - " + ChatColor.translateAlternateColorCodes('&', disable_description);
            String delete = ChatColor.AQUA + prefix + "delete <minecraft | discord> <Player | UserId>\n - " + ChatColor.translateAlternateColorCodes('&', delete_description);
            String blacklist = ChatColor.AQUA + prefix + "blacklist <add | remove> <minecraft | discord> <Player | UserId> <reason>\n - " + ChatColor.translateAlternateColorCodes('&', blacklist_description);
            String language = ChatColor.AQUA + prefix + "language <language>\n - " + ChatColor.translateAlternateColorCodes('&', language_description);
            commandSender.sendMessage(ChatColor.AQUA + "----- MoneyCord Command Help -----\n" + help + "\n" + reload + "\n" + disable + "\n" + delete + "\n" + blacklist + "\n" + language);
            return true;
        } else if (strings[0].equalsIgnoreCase("reload")) {
            String message = new Messages().getMessage("Messages.reload");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            MoneyCord.plugin.reloadConfig();
            String completed_message = new Messages().getMessage("Messages.successReload");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', completed_message));
            return true;
        } else if (strings[0].equalsIgnoreCase("disable")) {
            String message = new Messages().getMessage("Messages.disablePlugin");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            MoneyCord.jda.shutdown();
            MoneyCord.plugin.getServer().getPluginManager().disablePlugin(MoneyCord.plugin);
        } else if (strings[0].equalsIgnoreCase("blacklist")) {
            if (strings[1].equalsIgnoreCase("add")) {
                if (strings[2].equalsIgnoreCase("minecraft")) {
                    if (!strings[3].isEmpty()) {
                        String playerName = strings[3];
                        OfflinePlayer player;
                        try {
                            player = Bukkit.getOfflinePlayer(playerName);
                        } catch (Exception e) {
                            String message = new Messages().getMessage("Messages.unknownMinecraftPlayer");
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            return true;
                        }
                        String reason = (strings.length > 4) ? strings[4] : "none";
                        new Utils().blacklistAdd(player, reason);
                        String message = new Messages().getMessage("Messages.additionBlacklistCompleted");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    } else {
                        String message = new Messages().getMessage("Messages.argumentIsInvalid");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    }
                } else if (strings[2].equalsIgnoreCase("discord")) {
                    if (!strings[3].isEmpty() && strings[3].length() > 10) {
                        String blacklistUserId = strings[3];
                        User user;
                        try {
                            user = MoneyCord.jda.getUserById(blacklistUserId);
                        } catch (Exception e) {
                            String message = new Messages().getMessage("Messages.unknownDiscordUser");
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            return true;
                        }
                        String reason = (strings.length > 4) ? strings[4] : "none";
                        new Utils().blacklistAdd(user, reason);
                        String message = new Messages().getMessage("Messages.additionBlacklistCompleted");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    } else {
                        String message = new Messages().getMessage("Messages.argumentIsInvalid");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    }
                } else {
                    String message = new Messages().getMessage("Messages.argumentIsInvalid");
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return true;
                }
            } else if (strings[1].equalsIgnoreCase("remove")) {
                if (strings[2].equalsIgnoreCase("minecraft")) {
                    if (!strings[3].isEmpty()) {
                        String playerName = strings[3];
                        OfflinePlayer player;
                        try {
                            player = Bukkit.getOfflinePlayer(playerName);
                        } catch (Exception e) {
                            String message = new Messages().getMessage("Messages.unknownMinecraftPlayer");
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            return true;
                        }
                        new Utils().blacklistRemove(player);
                        String message = new Messages().getMessage("Messages.removalBlacklistCompleted");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    } else {
                        String message = new Messages().getMessage("Messages.argumentIsInvalid");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    }
                } else if (strings[2].equalsIgnoreCase("discord")) {
                    if (!strings[3].isEmpty() && strings[3].length() > 10) {
                        String blacklistUserId = strings[3];
                        User user;
                        try {
                            user = MoneyCord.jda.getUserById(blacklistUserId);
                        } catch (Exception e) {
                            String message = new Messages().getMessage("Messages.unknownDiscordUser");
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            return true;
                        }
                        new Utils().blacklistRemove(user);
                        String message = new Messages().getMessage("Messages.removalBlacklistCompleted");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    } else {
                        String message = new Messages().getMessage("Messages.argumentIsInvalid");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    }
                } else {
                    String message = new Messages().getMessage("Messages.argumentIsInvalid");
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return true;
                }
            } else {
                String message = new Messages().getMessage("Messages.argumentIsInvalid");
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                return true;
            }
        } else if (strings[0].equalsIgnoreCase("delete")) {
            if (strings[1].equalsIgnoreCase("minecraft")) {
                if (!strings[2].isEmpty()) {
                    String playerName = strings[2];
                    OfflinePlayer player;
                    try {
                        player = Bukkit.getOfflinePlayer(playerName);
                    } catch (Exception e) {
                        String message = new Messages().getMessage("Messages.unknownMinecraftPlayer");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return true;
                    }
                    new Utils().linkedDataDelete(player);
                    String message = new Messages().getMessage("Messages.deletionLinkedCompleted");
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return true;
                } else {
                    String message = new Messages().getMessage("Messages.argumentIsInvalid");
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return true;
                }
            } else if (strings[1].equalsIgnoreCase("discord")) {
                if (!strings[2].isEmpty() && strings[2].length() > 10) {
                    String deleteUserId = strings[2];
                    new Utils().linkedDataDelete(deleteUserId);
                    String message = new Messages().getMessage("Messages.deletionLinkedCompleted");
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return true;
                } else {
                    String message = new Messages().getMessage("Messages.argumentIsInvalid");
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return true;
                }
            } else {
                String message = new Messages().getMessage("Messages.argumentIsInvalid");
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                return true;
            }
        } else {
            String help_description = new Messages().getMessage("Messages.MoneyCord.help.description");
            String reload_description = new Messages().getMessage("Messages.MoneyCord.reload.description");
            String disable_description = new Messages().getMessage("Messages.MoneyCord.disable.description");
            String delete_description = new Messages().getMessage("Messages.MoneyCord.delete.description");
            String blacklist_description = new Messages().getMessage("Messages.MoneyCord.blacklist.description");
            String language_description = new Messages().getMessage("Messages.MoneyCord.language.description");
            String prefix = "/moneycord ";
            String help = ChatColor.AQUA + prefix + "help\n - " + ChatColor.translateAlternateColorCodes('&', help_description);
            String reload = ChatColor.AQUA + prefix + "reload\n - " + ChatColor.translateAlternateColorCodes('&', reload_description);
            String disable = ChatColor.AQUA + prefix + "disable\n - " + ChatColor.translateAlternateColorCodes('&', disable_description);
            String delete = ChatColor.AQUA + prefix + "delete <minecraft | discord> <Player | UserId>\n - " + ChatColor.translateAlternateColorCodes('&', delete_description);
            String blacklist = ChatColor.AQUA + prefix + "blacklist <add | remove> <minecraft | discord> <Player | UserId> <reason>\n - " + ChatColor.translateAlternateColorCodes('&', blacklist_description);
            String language = ChatColor.AQUA + prefix + "language <language>\n - " + ChatColor.translateAlternateColorCodes('&', language_description);
            commandSender.sendMessage(ChatColor.AQUA + "----- MoneyCord Command Help -----\n" + help + "\n" + reload + "\n" + disable + "\n" + delete + "\n" + blacklist + "\n" + language);
            return true;
        }
        return true;
    }

}
