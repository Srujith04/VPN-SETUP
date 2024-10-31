import java.io.*;
import java.net.*;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class VPNClient {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // Localhost
    private static final int PORT = 12345;
    private static final String SECRET_KEY = "227r1a66a0667187"; // Same 16-byte key for AES

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            System.out.println("Connected to VPN Server.");

            // Set up message and encryption
            String message = "this is a secret meassage!";
            String encryptedMessage = encryptMessage(message);
            System.out.println("Sending encrypted message: " + encryptedMessage);

            // Send message
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(encryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String encryptMessage(String message) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
