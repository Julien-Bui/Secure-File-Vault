package crypto;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CodeAES
{
    static Cipher cipher;

    public static void main(String[] args) throws Exception
    {
        testAES();
    }

    public static void testAES() throws Exception
    {
        // génération d'une clé AES de 128bits
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        // instanciation d'un objet chiffreur implémentant le chiffrement AES
        cipher = Cipher.getInstance("AES");

        // Test 1
        String plainText2 = "Hello, World!";
        testEncryptDecrypt(plainText2, secretKey);

        // Test 2
        String plainText3 = "Secure File Vault";
        testEncryptDecrypt(plainText3, secretKey);

    }

    private static void testEncryptDecrypt(String plainText, SecretKey secretKey) throws Exception
    {
        System.out.println("Test Text: " + plainText);

        // le chiffré
        String encryptedText = encrypt(plainText, secretKey);
        System.out.println("Encrypted: " + encryptedText);

        // de nouveau le clair
        String decryptedText = decrypt(encryptedText, secretKey);
        System.out.println("Decrypted: " + decryptedText);

        // vérification
        if (plainText.equals(decryptedText))
        {
            System.out.println("Succès\n");
        }
        else 
        {
            System.out.println("Erreur!\n");
        }
    }

    private static String decrypt(String encryptedText, SecretKey secretKey) throws Exception
	{
		// chargement du chiffré dans un tableau d'octets
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
 
		// initialisation du chiffreur en mode déchiffrement avec clé secrete
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
 
		// déchiffrement du tableau d'octets
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
 
		// chaine de caractères représentant le clair
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}
 
	private static String encrypt(String plainText, SecretKey secretKey) throws Exception
	{
		// chargement du clair dans un tableau d'octets
		byte[] plainTextByte = plainText.getBytes();
 
		// initialisation du chiffreur en mode chiffrement avec clé secrete
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
 
		// chiffrement du tableau d'octets
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
 
		// chaine de caractères représentant le chiffré 
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}
    
}
