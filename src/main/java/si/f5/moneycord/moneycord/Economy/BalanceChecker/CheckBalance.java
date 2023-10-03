package si.f5.moneycord.moneycord.Economy.BalanceChecker;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.util.UUID;

public class CheckBalance {

    public double CheckBalance(UUID uuid) {
        double balance = 0;
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            balance = MoneyCord.economy.getBalance(player);
        } catch (Exception e) {
            balance = Double.NaN;
        }
        return balance;
    }

}
