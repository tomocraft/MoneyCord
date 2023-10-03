package si.f5.moneycord.moneycord.JDA.MessageUtils.PlaneUtils.Sender;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SendMessages {

    public void sendMessage(Boolean ephemeral, String message, SlashCommandInteractionEvent event) {
        event.reply(message).setEphemeral(ephemeral).queue();
    }

}
