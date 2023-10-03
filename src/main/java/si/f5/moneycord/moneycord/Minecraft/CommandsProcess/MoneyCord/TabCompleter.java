package si.f5.moneycord.moneycord.Minecraft.CommandsProcess.MoneyCord;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = Arrays.asList("help", "reload", "disable", "delete", "blacklist", "language");
        String input = strings[0].toLowerCase();
        List<String> completions = null;
        if (strings.length == 1 && (strings[0].equalsIgnoreCase("help") || strings[0].equalsIgnoreCase("reload") || strings[0].equalsIgnoreCase("disable"))) {
            completions = new ArrayList();
        } else if (strings.length > 2 && strings[0].equalsIgnoreCase("delete")) {
            if (strings[2].equalsIgnoreCase("minecraft")) {
                return null;
            } else {
                completions = new ArrayList();
            }
        } else if (strings.length > 3 && strings[0].equalsIgnoreCase("blacklist")) {
            if (strings[3].equalsIgnoreCase("minecraft")) {
                return null;
            } else {
                completions = new ArrayList();
            }
        } else if (strings.length == 2 && strings[0].equalsIgnoreCase("blacklist")) {
            completions = Arrays.asList("add", "remove");
        } else if ((strings.length == 2 && strings[0].equalsIgnoreCase("delete")) || (strings.length == 3 && (strings[1].equalsIgnoreCase("add") || strings[1].equalsIgnoreCase("remove")))) {
            completions = Arrays.asList("minecraft", "discord");
        } else if (strings.length == 2 && strings[0].equalsIgnoreCase("language")) {
            completions = Arrays.asList("german", "english", "spanish", "french", "italian", "japanese", "korean", "dutch", "portuguese", "russian", "chinese");
        } else {
            for (String str : list) {
                if (str.startsWith(input)) {
                    if (completions == null) {
                        completions = new ArrayList();
                    }
                    completions.add(str);
                }
            }
        }
        if (completions != null) Collections.sort(completions);
        return completions;
    }

}
