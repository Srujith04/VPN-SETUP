import java.io.*;
import java.net.*;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class VPNServer{
    private static final int PORT = 12345; // Server port
    private static final String SECRET_KEY = "227r1a66a0667187"; // 16-byte key for AES

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("VPN Server started on port " + PORT);

            // Accept client connection
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Client connected.");

                // Set up streams
                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                // Receive encrypted message
                String encryptedMessage = reader.readLine();
                System.out.println("Received encrypted message: " + encryptedMessage);

                // Decrypt message
                String decryptedMessage = decryptMessage(encryptedMessage);
                System.out.println("Decrypted message: " + decryptedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String decryptMessage(String encryptedMessage) throws Exception {
        byte[] decodedMessage = Base64.getDecoder().decode(encryptedMessage);
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(decodedMessage);
        
        return new String(decryptedBytes);
    }
}
