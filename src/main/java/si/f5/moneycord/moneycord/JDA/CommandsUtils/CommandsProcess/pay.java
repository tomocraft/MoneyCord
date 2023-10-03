package si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsProcess;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import si.f5.moneycord.moneycord.Configuration.Blacklists.CheckBlackList;
import si.f5.moneycord.moneycord.Configuration.Messages.Messages;
import si.f5.moneycord.moneycord.JDA.MessageUtils.EmbedUtils.Color.EmbedColor;
import si.f5.moneycord.moneycord.JDA.MessageUtils.PlaneUtils.Sender.SendMessages;
import si.f5.moneycord.moneycord.LinkedData.Checker.CheckLinkedData;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.util.List;
import java.util.UUID;

public class pay extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("pay")) {
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
            if (event.getSubcommandName().equals("mcid")) {
                String targetPlayerName = event.getOption("mcid").getAsString();
                double payAmount = event.getOption("amount").getAsDouble();
                if (!(payAmount > 0)) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.InvalidAmount"), event);
                    return;
                }
                OfflinePlayer player;
                UUID uuid;
                try {
                    uuid = new CheckLinkedData().CheckUserIdToUUID(event.getUser().getId());
                    player = Bukkit.getOfflinePlayer(uuid);
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.accountIsNotLinked"), event);
                    return;
                }
                OfflinePlayer targetPlayer;
                try {
                    targetPlayer = Bukkit.getOfflinePlayer(targetPlayerName);
                    if (!MoneyCord.economy.hasAccount(targetPlayer)) {
                        new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.playerBalanceNotFound"), event);
                        return;
                    }
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.playerNotFound"), event);
                    return;
                }
                if (!targetPlayer.hasPlayedBefore()) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.playerNotFound"), event);
                    return;
                }
                if (targetPlayer == player) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.cannotPayYourself"), event);
                    return;
                }
                try {
                    double balance = MoneyCord.economy.getBalance(player);
                    if (balance < payAmount) {
                        new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.moreThanBalance"), event);
                        return;
                    }
                }catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.playerBalanceNotFound"), event);
                    return;
                }
                try {
                    MoneyCord.economy.withdrawPlayer(player, payAmount);
                    MoneyCord.economy.depositPlayer(targetPlayer, payAmount);
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(new EmbedColor().RGBEmbedColor("Messages.PayCommand.Color"))
                            .setTitle(new Messages().getMessage("Messages.PayCommand.Title"))
                            .setDescription(new Messages().getMessage("Messages.PayCommand.Description-MCID")
                                    .replace("{MinecraftName}", player.getName())
                                    .replace("{targetMinecraftName}", targetPlayerName)
                                    .replace("{amount}", MoneyCord.economy.format(payAmount))
                                    .replace("{user}", "<@" + event.getUser().getId() + ">")
                                    .replace("{userName}", event.getUser().getName())
                                    .replace("{userDisplayName}", event.getUser().getEffectiveName()));
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Pay-Money.Ephemeral")).queue();
                    if (targetPlayer.isOnline()) {
                        Player targetOnlinePlayer = targetPlayer.getPlayer();
                        targetOnlinePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', new Messages().getMessage("Messages.paidByDiscordUser")
                                .replace("{discordUserDisplayName}", event.getUser().getEffectiveName())
                                .replace("{discordUserName}", event.getUser().getName())
                                .replace("{amount}", MoneyCord.economy.format(payAmount))));
                    }
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.errorDuringCommandExecution"), event);
                }
            } else if (event.getSubcommandName().equals("user")) {
                User targetUser = event.getOption("user").getAsUser();
                double payAmount = event.getOption("amount").getAsDouble();
                if (!(payAmount > 0)) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.InvalidAmount"), event);
                    return;
                }
                String targetUserId = targetUser.getId();
                OfflinePlayer player;
                OfflinePlayer targetPlayer;
                UUID uuid;
                UUID targetUuid;
                try {
                    uuid = new CheckLinkedData().CheckUserIdToUUID(event.getUser().getId());
                    player = Bukkit.getOfflinePlayer(uuid);
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.accountIsNotLinked"), event);
                    return;
                }
                try {
                    targetUuid = new CheckLinkedData().CheckUserIdToUUID(targetUserId);
                    targetPlayer = Bukkit.getOfflinePlayer(targetUuid);
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.targetAccountIsNotLinked"), event);
                    return;
                }
                try {
                    if (!MoneyCord.economy.hasAccount(player) || !MoneyCord.economy.hasAccount(targetPlayer)) {
                        new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.playerBalanceNotFound"), event);
                        return;
                    }
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.playerNotFound"), event);
                    return;
                }
                if (player == targetPlayer) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.cannotPayYourself"), event);
                    return;
                }
                try {
                    double balance = MoneyCord.economy.getBalance(player);
                    if (balance < payAmount) {
                        new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.moreThanBalance"), event);
                        return;
                    }
                }catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"),
                            new Messages().getMessage("Messages.playerBalanceNotFound"),
                            event);
                    return;
                }
                try {
                    MoneyCord.economy.withdrawPlayer(player, payAmount);
                    MoneyCord.economy.depositPlayer(targetPlayer, payAmount);
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(new EmbedColor().RGBEmbedColor("Messages.PayCommand.Color"))
                            .setTitle(new Messages().getMessage("Messages.PayCommand.Title"))
                            .setDescription(new Messages().getMessage("Messages.PayCommand.Description-User")
                                    .replace("{MinecraftName}", player.getName()).replace("{targetMinecraftName}", targetPlayer.getName())
                                    .replace("{amount}", MoneyCord.economy.format(payAmount))
                                    .replace("{user}", "<@" + event.getUser().getId() + ">")
                                    .replace("{userName}", event.getUser().getName())
                                    .replace("{userDisplayName}", event.getUser().getEffectiveName())
                                    .replace("{targetUser}", "<@" + targetUserId + ">")
                                    .replace("{targetUserName}", targetUser.getName())
                                    .replace("{targetUserDisplayName}", targetUser.getEffectiveName()));
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Pay-Money.Ephemeral")).queue();
                    if (targetPlayer.isOnline()) {
                        Player targetOnlinePlayer = targetPlayer.getPlayer();
                        targetOnlinePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', new Messages().getMessage("Messages.paidByDiscordUser")
                                .replace("{discordUserDisplayName}", event.getUser().getEffectiveName())
                                .replace("{discordUserName}", event.getUser().getName())
                                .replace("{amount}", MoneyCord.economy.format(payAmount))));
                    }
                } catch (Exception e) {
                    new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.errorDuringCommandExecution"), event);
                }
            }
        }
    }

}
