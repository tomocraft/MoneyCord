package si.f5.moneycord.moneycord.JDA.SaveImage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.OfflinePlayer;
import si.f5.moneycord.moneycord.MainCore.MoneyCord;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class saveAvatarImage {

    public static String saveAvatarImage(OfflinePlayer player) {
        File avatarDir = null;
        try {
            String uuid = player.getUniqueId().toString();
            String name = player.getName();
            avatarDir = new File(MoneyCord.plugin.getDataFolder(), "Avatar");
            if (!avatarDir.exists()) {
                avatarDir.mkdir();
            }
            File avatarImage = new File(avatarDir,  name + ".png");
            String path = avatarImage.getPath();
            File file = new File(path);
            String imageUrl = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid;
            try {
                HttpClient httpClient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet(imageUrl);
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String json = EntityUtils.toString(entity);
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(json);
                    String base64 = rootNode.path("properties").get(0).path("value").asText();
                    byte[] decodedJson = Base64.getDecoder().decode(base64);
                    JsonNode jsonNode = objectMapper.readTree(decodedJson);
                    String skinUrl = jsonNode.path("textures").path("SKIN").path("url").asText();
                    URL url = new URL(skinUrl);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    try {
                        FileOutputStream outputStream = new FileOutputStream(file);
                        int len = 1024;
                        byte[] buf = new byte[len];
                        while ((len = inputStream.read(buf, 0, len)) != -1) {
                            outputStream.write(buf, 0, len);
                        }
                        outputStream.close();
                        inputStream.close();
                        BufferedImage skinImage = ImageIO.read(new File(path));
                        int faceX = 8;
                        int faceY = 8;
                        int faceWidth = 8;
                        int faceHeight = 8;
                        BufferedImage faceImage = skinImage.getSubimage(faceX, faceY, faceWidth, faceHeight);
                        ImageIO.write(faceImage, "png", new File(path));
                        BufferedImage original = ImageIO.read(new File(path));
                        BufferedImage resizeImage = new BufferedImage(128, 128, BufferedImage.TYPE_3BYTE_BGR);
                        resizeImage.createGraphics().drawImage(original.getScaledInstance(
                                        128, 128, Image.SCALE_AREA_AVERAGING)
                                ,0, 0, 128, 128, null);
                        ImageIO.write(resizeImage, "png", new File(path));
                        return path;
                    } catch (IOException e) {
                        File defaultAvatar = new File(avatarDir, "Alex.png");
                        return defaultAvatar.getPath();
                    }
                }
            } catch (IOException e) {
                File defaultAvatar = new File(avatarDir, "Alex.png");
                return defaultAvatar.getPath();
            }
        } catch (Exception e) {
            File defaultAvatar = new File(avatarDir, "Alex.png");
            return defaultAvatar.getPath();
        }
        File defaultAvatar = new File(avatarDir, "Alex.png");
        return defaultAvatar.getPath();
    }

}
