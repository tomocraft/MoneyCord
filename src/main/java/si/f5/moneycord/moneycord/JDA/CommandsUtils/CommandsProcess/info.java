package si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsProcess;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.AttachedFile;
import net.dv8tion.jda.api.utils.FileUpload;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import si.f5.moneycord.moneycord.Configuration.Blacklists.CheckBlackList;
import si.f5.moneycord.moneycord.Configuration.Messages.Messages;
import si.f5.moneycord.moneycord.JDA.MessageUtils.EmbedUtils.Color.EmbedColor;
import si.f5.moneycord.moneycord.JDA.MessageUtils.PlaneUtils.Sender.SendMessages;
import si.f5.moneycord.moneycord.LinkedData.Checker.CheckLinkedData;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static si.f5.moneycord.moneycord.JDA.SaveImage.saveAvatarImage.saveAvatarImage;

public class info extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("info")) {
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
            if (event.getSubcommandName().equals("user")) {
                User targetUser = event.getOption("user").getAsUser();
                String targetUserId = targetUser.getId();
                UUID targetUuid;
                try {
                    targetUuid = new CheckLinkedData().CheckUserIdToUUID(targetUserId);
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.targetAccountIsNotLinked"), event);
                    return;
                }
                if (targetUuid == null) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.targetAccountIsNotLinked"), event);
                    return;
                }
                event.deferReply().setEphemeral(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Player-Info.Ephemeral")).queue();
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(targetUuid);
                String avatarPath = saveAvatarImage(Bukkit.getOfflinePlayer(targetUuid));
                File avatarFile = new File(avatarPath);
                InputStream avatarInputStream = null;
                try {
                    avatarInputStream = new FileInputStream(avatarFile);
                } catch (FileNotFoundException e) {
                }
                FileUpload fileUpload = AttachedFile.fromData(avatarInputStream, "avatar.png");
                String balance = (MoneyCord.economy.hasAccount(targetPlayer)) ? MoneyCord.economy.format(MoneyCord.economy.getBalance(targetPlayer)) : new Messages().getMessage("Messages.InfoCommand.Field-LinkedAccount-User.Value-NotFoundBalance");
                EmbedBuilder embed = new EmbedBuilder()
                        .setColor(new EmbedColor().RGBEmbedColor("Messages.InfoCommand.Color"))
                        .setTitle(new Messages().getMessage("Messages.InfoCommand.Title-User").replace("{userName}", targetUser.getName()).replace("{userDisplayName}", targetUser.getEffectiveName()))
                        .setThumbnail("attachment://avatar.png")
                        .addField(new Messages().getMessage("Messages.InfoCommand.Field-LinkedAccount-User.Name"), new Messages().getMessage("Messages.InfoCommand.Field-LinkedAccount-User.Value").replace("{user}", "<@" + targetUserId + ">").replace("{userName}", targetUser.getName()).replace("{userDisplayName}", targetUser.getEffectiveName()).replace("{MinecraftName}", targetPlayer.getName()), false)
                        .addField(new Messages().getMessage("Messages.InfoCommand.Field-Balance.Name"), new Messages().getMessage("Messages.InfoCommand.Field-Balance.Value").replace("{user}", "<@" + targetUserId + ">").replace("{userName}", targetUser.getName()).replace("{userDisplayName}", targetUser.getEffectiveName()).replace("{MinecraftName}", targetPlayer.getName()).replace("{balance}", balance), false);
                event.getHook().sendMessageEmbeds(embed.build())
                        .setFiles(fileUpload)
                        .queue();
                if (!avatarPath.equals(MoneyCord.plugin.getDataFolder() + "Avatar/Alex.png")) {
                    avatarFile.delete();
                }
            } else if (event.getSubcommandName().equals("mcid")) {
                String targetMcid = event.getOption("mcid").getAsString();
                UUID targetUuid;
                try {
                    targetUuid = Bukkit.getOfflinePlayer(targetMcid).getUniqueId();
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.playerNotFound"), event);
                    return;
                }
                if (!Bukkit.getOfflinePlayer(targetUuid).hasPlayedBefore()) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.playerNotFound"), event);
                    return;
                }
                event.deferReply().setEphemeral(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Player-Info.Ephemeral")).queue();
                String linkedData;
                String userId = new CheckLinkedData().CheckUUIDToUserId(targetUuid);
                try {
                    User user = event.getJDA().retrieveUserById(userId).complete();
                    linkedData = new Messages().getMessage("Messages.InfoCommand.Field-LinkedAccount-MCID.Value")
                            .replace("{user}", "<@" + userId + ">")
                            .replace("{userName}", user.getName())
                            .replace("{userDisplayName}", user.getEffectiveName());
                } catch(Exception e){
                    linkedData = new Messages().getMessage("Messages.InfoCommand.Field-LinkedAccount-MCID.Value-NotFoundLinkedAccount");
                    e.printStackTrace();
                }
                try {

                } catch (Exception ignored) {
                }
                String balance;
                try {
                    balance = new Messages().getMessage("Messages.InfoCommand.Field-Balance.Value")
                            .replace("{MinecraftName}", Bukkit.getOfflinePlayer(targetUuid).getName())
                            .replace("{balance}", MoneyCord.economy.format(MoneyCord.economy.getBalance(Bukkit.getOfflinePlayer(targetUuid))));
                } catch (Exception e) {
                    balance = new Messages().getMessage("Messages.InfoCommand.Field-Balance.Value-NotFoundBalance");
                }
                String avatarPath = saveAvatarImage(Bukkit.getOfflinePlayer(targetUuid));
                File avatarFile = new File(avatarPath);
                InputStream avatarInputStream = null;
                try {
                    avatarInputStream = new FileInputStream(avatarFile);
                } catch (FileNotFoundException e) {
                }
                FileUpload fileUpload = AttachedFile.fromData(avatarInputStream, "avatar.png");
                EmbedBuilder embed = new EmbedBuilder()
                        .setColor(new EmbedColor().RGBEmbedColor("Messages.InfoCommand.Color"))
                        .setTitle(new Messages().getMessage("Messages.InfoCommand.Title-MCID").replace("{MinecraftName}", Bukkit.getOfflinePlayer(targetUuid).getName()))
                        .setThumbnail("attachment://avatar.png")
                        .addField(new Messages().getMessage("Messages.InfoCommand.Field-LinkedAccount-MCID.Name"), linkedData, false)
                        .addField(new Messages().getMessage("Messages.InfoCommand.Field-Balance.Name"), balance, false);
                event.getHook().sendMessageEmbeds(embed.build())
                        .setFiles(fileUpload)
                        .queue();
                if (!avatarPath.equals(MoneyCord.plugin.getDataFolder() + "Avatar/Alex.png")) {
                    avatarFile.delete();
                }
            }
        }
    }

}
