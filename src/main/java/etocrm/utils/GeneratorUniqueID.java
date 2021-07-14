package etocrm.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GeneratorUniqueID {

    private static String[] chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9","A","B","C",
    "D","E","F","G","H","Q","W","R","T","Y","U","I","O","P","S","J","K","L","Z","V","X","N","M"};

    public String generateShortUuid() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 36]);
        }
        return shortBuffer.toString();
    }
}
