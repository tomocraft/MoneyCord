package si.f5.moneycord.moneycord.Minecraft.CommandsProcess.Linking;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import si.f5.moneycord.moneycord.Configuration.Blacklists.CheckBlackList;
import si.f5.moneycord.moneycord.Configuration.Messages.Messages;
import si.f5.moneycord.moneycord.LinkedData.Gnerater.GenerateLinkCode;
import si.f5.moneycord.moneycord.LinkedData.Process.LinkingProcess;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class linking implements CommandExecutor , TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!MoneyCord.plugin.getConfig().getBoolean("Minecraft-Commands.Account-Link.Enable")) {
            String message = new Messages().getMessage("Messages.commandIsDisabled");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        if (!(commandSender instanceof Player)) {
            String message = new Messages().getMessage("Messages.commandExecutorIsNotPlayer");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        if (!commandSender.hasPermission("moneycord.commands.linking")) {
            String message = new Messages().getMessage("Messages.doNotHavePermission");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        if (new CheckBlackList().checkBlacklist(((Player) commandSender).getUniqueId())) {
            String message = new Messages().getMessage("Messages.blacklistedPlayer");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        Player p = (Player) commandSender;
        UUID uuid = p.getUniqueId();
        if (new LinkingProcess().accountLinked(uuid)) {
            String message = new Messages().getMessage("Messages.linkedAccountOnMinecraft");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        String code = new GenerateLinkCode().generateRandomCode(uuid);
        MoneyCord.playerCodeMap.put(uuid, code);
        String message = new Messages().getMessage("Messages.generatedCode");
        TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&', message).replace("{code}", code));
        p.spigot().sendMessage(component);
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList();
    }
}
