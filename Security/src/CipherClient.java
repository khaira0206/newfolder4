import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CipherClient {
	public static void main(String[] args) throws Exception {
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "localhost";
		int port = 1115;
		Socket s = new Socket(host, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		File f = new File("DESKEY.txt");
		// YOU NEED TO DO THESE STEPS:
		// -Generate a DES key.
		KeyGenerator generator;
		generator = KeyGenerator.getInstance("DES");
		generator.init(new SecureRandom());
		SecretKey key = generator.generateKey();
		// -Store it in a file.
		FileOutputStream f1 = new FileOutputStream(f);
		ObjectOutputStream outf = new ObjectOutputStream(f1);
		try {
			outf.writeObject(key);
		} finally {
			outf.close();
		}
		System.out.println(key.toString());
		// -Use the key to encrypt the message above and send it over socket s
		// to the server.

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		// Gets the raw bytes to encrypt, UTF8 is needed for
		// having a standard character set
		byte[] stringBytes = message.getBytes("UTF8");

		// encrypt using the cipher
		byte[] raw = cipher.doFinal(stringBytes);

		os.writeObject(raw);

	}
}