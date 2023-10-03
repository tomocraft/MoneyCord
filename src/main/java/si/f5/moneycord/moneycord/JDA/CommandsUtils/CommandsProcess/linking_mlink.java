package si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsProcess;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import si.f5.moneycord.moneycord.Configuration.Blacklists.CheckBlackList;
import si.f5.moneycord.moneycord.Configuration.Messages.Messages;
import si.f5.moneycord.moneycord.JDA.MessageUtils.EmbedUtils.Sender.SendEmbeds;
import si.f5.moneycord.moneycord.JDA.MessageUtils.PlaneUtils.Sender.SendMessages;
import si.f5.moneycord.moneycord.JDA.MessageUtils.PlaneUtils.UserGetProcess.GetString;
import si.f5.moneycord.moneycord.LinkedData.Process.LinkingProcess;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.util.List;
import java.util.UUID;

public class linking_mlink extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("linking") || event.getName().equals("mlink")) {
            if (MoneyCord.plugin.getConfig().getBoolean("Discord-Bot.CommandChannelRestriction.Enable")) {
                List<String> channelIdList = MoneyCord.plugin.getConfig().getStringList("Discord-Bot.CommandChannelRestriction.Channel-ID");
                if (!channelIdList.contains(event.getChannel().getId())) {
                    new SendMessages().sendMessage(true, new Messages().getMessage("Messages.cannotUseCommandTheChannel"), event);
                    return;
                }
            }
            if (new CheckBlackList().checkBlacklist(event.getUser().getId())) {
                new SendMessages().sendMessage(true, new Messages().getMessage("Messages.blacklistedUser"), event);
                return;
            }
            String code = String.valueOf((int) Math.floor(event.getOption("code").getAsDouble()));
            UUID uuid = new LinkingProcess().getUUIDByCode(code);
            String userId = event.getUser().getId();
            if (uuid == null) {
                new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.notExistCode"), event);
                return;
            }
            if (!new LinkingProcess().checkLinkedData(uuid, userId)) {
                new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.linkedAccountOnDiscord"), event);
                return;
            }
            String minecraftName = new GetString().getMinecraftName(uuid);
            String userName = event.getUser().getName();
            String userDisplayName = event.getUser().getEffectiveName();
            String userMention = "<@" + userId + ">";
            String title = new Messages().getMessage("Messages.LinkCommand.Title");
            String description = new Messages().getMessage("Messages.LinkCommand.Description")
                    .replace("{MinecraftName}", minecraftName)
                    .replace("{userName}", userName)
                    .replace("{userDisplayName}", userDisplayName)
                    .replace("{user}", userMention);
            new SendEmbeds().sendSimpleEmbed(
                    "Messages.LinkCommand.Color",
                    MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Account-Link.Ephemeral"),
                    event,
                    title,
                    description
            );
            new LinkingProcess().saveLinkedData(uuid, userId);
            if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                String message = new Messages().getMessage("Messages.linkingWasCompleted");
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
    }

}
