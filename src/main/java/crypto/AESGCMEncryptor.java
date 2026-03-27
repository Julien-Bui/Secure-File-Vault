import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESGCMEncryptor
{
    private static byte[] generateIV()
    {
        byte[] iv = new byte[12]; 
        SecureRandom random = new SecureRandom(); 
        random.nextBytes(iv);
        return iv;
    }

    public static byte[] encrypt(byte[] data, SecretKey key)
    {
        byte[] iv = generateIV(); // Génération d'un IV aléatoire pour chaque chiffrement

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // Initialisation du chiffreur pour AES-GCM
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv); 
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

        byte[] cipherText = cipher.doFinal(data);

        byte[] combined = new byte[iv.length + cipherText.length]; 
        System.arraycopy(iv, 0, combined, 0, iv.length); // Combinaison de l'IV et du texte chiffré
        System.arraycopy(cipherText, 0, combined, iv.length, cipherText.length);

        return combined;
    }

    public static byte[] decrypt(byte[] encryptedData, SecretKey key)
    {
        byte[] iv = new byte[12];
        System.arraycopy(encryptedData, 0, iv, 0, 12);

        byte[] cipherText = new byte[encryptedData.length - 12];
        System.arraycopy(encryptedData, 12, cipherText, 0, cipherText.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // Initialisation du chiffreur pour AES-GCM

        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv); // Création d'un paramètre GCM avec l'IV extrait

        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

        return cipher.doFinal(cipherText); // Déchiffrement des données
    }


}