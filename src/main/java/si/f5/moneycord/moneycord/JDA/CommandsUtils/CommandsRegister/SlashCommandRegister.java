package si.f5.moneycord.moneycord.JDA.CommandsUtils.CommandsRegister;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.util.Objects;

public class SlashCommandRegister {

    public OnlineStatus statusCheckConfig() {
        OnlineStatus onlineStatus = OnlineStatus.ONLINE;
        if (MoneyCord.plugin.getConfig().getString("Discord-Bot.Status").equals("IDLE")) {
            onlineStatus = OnlineStatus.IDLE;
        } else if (MoneyCord.plugin.getConfig().getString("Discord-Bot.Status").equals("DO_NOT_DISTURB")) {
            onlineStatus = OnlineStatus.DO_NOT_DISTURB;
        } else if (MoneyCord.plugin.getConfig().getString("Discord-Bot.Status").equals("INVISIBLE")) {
            onlineStatus = OnlineStatus.INVISIBLE;
        }
        return onlineStatus;
    }

    public Activity activityCheckConfig() {
        String activityName;
        try {
            activityName = MoneyCord.plugin.getConfig().getString("Discord-Bot.Activity.Name");
        } catch (Exception e) {
            activityName = "Minecraft";
        }
        Activity activity = Activity.playing(activityName);
        if (MoneyCord.plugin.getConfig().getString("Discord-Bot.Activity.Name").equals("WATCHING")) {
            activity = Activity.watching(activityName);
        } else if (MoneyCord.plugin.getConfig().getString("Discord-Bot.Activity.Name").equals("LISTENING")) {
            activity = Activity.listening(activityName);
        } else if (MoneyCord.plugin.getConfig().getString("Discord-Bot.Activity.Name").equals("COMPETING")) {
            activity = Activity.competing(activityName);
        }
        return activity;
    }

    public void registerSlashCommand(Guild guild) {
        guild.updateCommands()
                .addCommands()
                .queue();
        if (MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Check-Money.Enable")) {
            SlashCommandData balance = Commands.slash("balance", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Check-Money.Description")));
            guild.upsertCommand(balance).queue();
            SlashCommandData bal = Commands.slash("bal", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Check-Money.Description")));
            guild.upsertCommand(bal).queue();
            SlashCommandData money = Commands.slash("money", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Check-Money.Description")));
            guild.upsertCommand(money).queue();
        }
        if (MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Player-Info.Enable")) {
            SubcommandData info1 = new SubcommandData("user", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Player-Info.Subcommands.user.Description")))
                    .addOption(OptionType.USER, "user", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Player-Info.Subcommands.user.Option.user.Description")), true);
            SubcommandData info2 = new SubcommandData("mcid", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Player-Info.Subcommands.mcid.Description")))
                    .addOption(OptionType.STRING, "mcid", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Player-Info.Subcommands.mcid.Option.mcid.Description")), true);
            SlashCommandData info = Commands.slash("info", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Player-Info.Description")))
                    .addSubcommands(info1, info2);
            guild.upsertCommand(info).queue();
        }
        if (MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Pay-Money.Enable")) {
            SubcommandData pay1 = new SubcommandData("user", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Pay-Money.Subcommands.user.Description")))
                    .addOption(OptionType.NUMBER, "amount", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Pay-Money.Subcommands.user.Options.user.Description")), true)
                    .addOption(OptionType.USER, "user", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Pay-Money.Subcommands.user.Options.amount.Description")), true);
            SubcommandData pay2 = new SubcommandData("mcid", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Pay-Money.Subcommands.mcid.Description")))
                    .addOption(OptionType.NUMBER, "amount", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Pay-Money.Subcommands.user.Options.amount.Description")), true)
                    .addOption(OptionType.STRING, "mcid", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Pay-Money.Subcommands.mcid.Options.mcid.Description")), true);
            SlashCommandData pay = Commands.slash("pay", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Pay-Money.Description")))
                    .addSubcommands(pay1, pay2);
            guild.upsertCommand(pay).queue();
        }
        if (MoneyCord.plugin.getConfig().getBoolean("Discord-Commands.Account-Link.Enable")) {
            SlashCommandData linking = Commands.slash("linking", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Account-Link.Description")))
                    .addOption(OptionType.NUMBER, "code", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Account-Link.Option.code.Description")), true);
            SlashCommandData mlink = Commands.slash("mlink", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Account-Link.Description")))
                    .addOption(OptionType.NUMBER, "code", Objects.requireNonNull(MoneyCord.plugin.getConfig().getString("Discord-Commands.Account-Link.Option.code.Description")), true);
            guild.upsertCommand(linking).queue();
            guild.upsertCommand(mlink).queue();
        }
    }

}
