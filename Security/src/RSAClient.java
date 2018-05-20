
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAClient {

	private static RSAPublicKey c_pb_key;
	private static RSAPrivateKey c_pr_key;

	static void generationofKeys() {

		KeyPairGenerator keypair1 = null;
		try {
			keypair1 = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {

			System.out.println(e.getMessage());
		}
		keypair1.initialize(1024, new SecureRandom());
		KeyPair keypair2 = keypair1.genKeyPair();
		c_pb_key = (RSAPublicKey) keypair2.getPublic();
		c_pr_key = (RSAPrivateKey) keypair2.getPrivate();

	}

	public static void main(String[] args)
			throws UnknownHostException, IOException, InvalidKeyException, ClassNotFoundException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		generationofKeys();

		

		File f = new File("c_p_key.txt");
		FileOutputStream str = new FileOutputStream(f);
		ObjectOutputStream obout = new ObjectOutputStream(str);
		obout.writeObject(c_pb_key);

		Scanner in = new Scanner(System.in);
		String host = "127.0.0.1";
		int port = 1113;
		Socket s = new Socket(host, port);
		ObjectOutputStream serverStream = new ObjectOutputStream(s.getOutputStream());
		

		File sf = new File("s_p_key.txt");
		FileInputStream str1 = new FileInputStream(sf);
		ObjectInputStream str2 = new ObjectInputStream(str1);
		RSAPublicKey s_pb_key = (RSAPublicKey) str2.readObject();

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		System.out.println("Enter 1 for confidentiality 2 for integrity and confidentiality ");
		int c = in.nextInt();

		switch (c) {
		case 1:
			String case1 = " confidentiality using server's public key";
			cipher.init(Cipher.ENCRYPT_MODE, s_pb_key);
			byte[] Text1 = cipher.doFinal(case1.getBytes());
			System.out.println("The ciphertext " + Text1);
			serverStream.writeInt(1);
			serverStream.writeObject(Text1);
			serverStream.flush();
			serverStream.close();
			break;

		case 2:
			String case2 = " integrity and Authentication using client's private key";
			cipher.init(Cipher.ENCRYPT_MODE, c_pr_key);
			byte[] Text2 = cipher.doFinal(case2.getBytes());
			System.out.println("The ciphertext " + Text2);
			serverStream.writeInt(2);
			serverStream.writeObject(Text2);
			serverStream.flush();
			serverStream.close();
			break;

		}
		s.close();
		in.close();

	}

}
