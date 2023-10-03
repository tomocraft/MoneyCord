package si.f5.moneycord.moneycord.Economy.Setup;

import org.bukkit.plugin.RegisteredServiceProvider;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

public class CheckEconomy {

    public boolean setupEconomy() {
        RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> economyProvider = MoneyCord.plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            MoneyCord.economy = economyProvider.getProvider();
        }
        return (MoneyCord.economy != null);
    }

}
