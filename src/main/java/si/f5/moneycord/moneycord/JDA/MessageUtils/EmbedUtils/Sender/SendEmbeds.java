package si.f5.moneycord.moneycord.JDA.MessageUtils.EmbedUtils.Sender;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import si.f5.moneycord.moneycord.JDA.MessageUtils.EmbedUtils.Color.EmbedColor;

public class SendEmbeds {

    public void sendSimpleEmbed(String path, Boolean ephemeral, SlashCommandInteractionEvent event, String title, String description) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(new EmbedColor().RGBEmbedColor(path))
                .setTitle(title)
                .setDescription(description);
        event.replyEmbeds(embed.build()).setEphemeral(ephemeral).queue();
    }

}
