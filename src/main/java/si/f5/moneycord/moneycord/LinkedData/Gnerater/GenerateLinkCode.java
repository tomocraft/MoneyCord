package si.f5.moneycord.moneycord.LinkedData.Gnerater;

import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class GenerateLinkCode {

    public String generateRandomCode(UUID uuid) {
        for (Map.Entry<UUID, String> entry : MoneyCord.playerCodeMap.entrySet()) {
            if (entry.getKey().equals(uuid)) {
                return entry.getValue();
            }
        }
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        for (Map.Entry<UUID, String> entry : MoneyCord.playerCodeMap.entrySet()) {
            if (entry.getValue().equals(String.valueOf(randomNumber))) {
                return generateRandomCode(uuid);
            }
        }
        return String.valueOf(randomNumber);
    }

}
