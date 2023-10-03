package si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsProcess;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import si.f5.moneycord.moneycord.Configuration.Blacklists.CheckBlackList;
import si.f5.moneycord.moneycord.Configuration.Messages.Messages;
import si.f5.moneycord.moneycord.Economy.BalanceChecker.CheckBalance;
import si.f5.moneycord.moneycord.JDA.MessageUtils.EmbedUtils.Sender.SendEmbeds;
import si.f5.moneycord.moneycord.JDA.MessageUtils.PlaneUtils.Sender.SendMessages;
import si.f5.moneycord.moneycord.JDA.MessageUtils.PlaneUtils.UserGetProcess.GetString;
import si.f5.moneycord.moneycord.LinkedData.Checker.CheckLinkedData;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.util.List;
import java.util.UUID;

public class money extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("money") || event.getName().equals("balance") || event.getName().equals("bal")) {
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
            UUID uuid = new CheckLinkedData().CheckUserIdToUUID(event.getUser().getId());
            if (uuid == null) {
                new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.didNotLinkAccount"), event);
                return;
            }
            double balance = new CheckBalance().CheckBalance(uuid);
            if (Double.isNaN(balance)) {
                new SendMessages().sendMessage(MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.ErrorMessageEphemeral"), new Messages().getMessage("Messages.cannotCheckBalance"), event);
                return;
            }
            String title = new Messages().getMessage("Messages.MoneyCommand.Title");
            String minecraftName = new GetString().getMinecraftName(uuid);
            String userId = event.getUser().getId();
            User user = event.getJDA().retrieveUserById(userId).complete();
            String userName = user.getName();
            String userDisplayName = user.getEffectiveName();
            title = title.replace("{MinecraftName}", minecraftName)
                    .replace("{userName}", userName)
                    .replace("{userDisplayName}", userDisplayName);
            String description = new Messages().getMessage("Messages.MoneyCommand.Description");
            String balanceFormatted = MoneyCord.economy.format(balance);
            description = description.replace("{MinecraftName}", minecraftName)
                    .replace("{userName}", userName)
                    .replace("{userDisplayName}", userDisplayName)
                    .replace("{balance}", balanceFormatted)
                    .replace("{user}", "<@" + userId + ">");
            new SendEmbeds().sendSimpleEmbed("Messages.MoneyCommand.Color", MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Check-Money.Ephemeral"), event, title, description);
        }
    }

}
