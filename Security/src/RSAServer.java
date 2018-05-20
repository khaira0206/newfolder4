
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;


public class RSAServer {

	private static RSAPublicKey s_pb_key;
	private static RSAPrivateKey s_pr_key;

	static void generationofKeys() {

		KeyPairGenerator genKeyPair = null;
		try {
			genKeyPair = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		genKeyPair.initialize(1024, new SecureRandom());
		KeyPair keyPair = genKeyPair.genKeyPair();
		s_pb_key = (RSAPublicKey) keyPair.getPublic();
		s_pr_key = (RSAPrivateKey) keyPair.getPrivate();

	}

	public static void main(String[] args) throws Exception {

		generationofKeys();

		// sharing server's public key via File
		File f = new File("s_p_key.txt");
		FileOutputStream fout = new FileOutputStream(f);
		ObjectOutputStream str = new ObjectOutputStream(fout);
		str.writeObject(s_pb_key);

		int port = 1113;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();
		System.out.println("The client is connected:"+client);
		ObjectInputStream client_data = new ObjectInputStream(client.getInputStream());

		
		File file1 = new File("c_p_key.txt");
		FileInputStream file2 = new FileInputStream(file1);
		ObjectInputStream file3 = new ObjectInputStream(file2);
		RSAPublicKey c_pb_key = (RSAPublicKey) file3.readObject();

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		
		int choice = client_data.readInt();

		switch (choice) {
		case 1:
			System.out.println("confidentiality");
			byte[] case1 = (byte[]) client_data.readObject();
			System.out.println(new String(case1));
			cipher.init(Cipher.DECRYPT_MODE, s_pr_key);
			byte[] Text1 = cipher.doFinal(case1);
			System.out.println("Text: " + new String(Text1));
			break;

		case 2:
			
			System.out.println("Integrity/Authentication:");
			byte[] case2 = (byte[]) client_data.readObject();
			System.out.println(new String(case2));
			cipher.init(Cipher.DECRYPT_MODE, c_pb_key);
			byte[] Text2 = cipher.doFinal(case2);
			System.out.println("Text: " + new String(Text2));
			break;

		}

	}
}
