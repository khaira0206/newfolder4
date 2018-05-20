import java.io.*;
import java.net.*;
import java.security.*;

import javax.crypto.*;

public class X509Server {

	public static void main(String[] args) throws Exception 
	{   
		
		String alias="Harman";
        char[] password="computer".toCharArray();
        int port = 1234;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();
		System.out.println("Server is listening at 127.0.0.1:1234");
		ObjectInputStream is = new ObjectInputStream(s.getInputStream());
		//Read the keystore and retrieve the server's private key
        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(new FileInputStream("testKeystore.jks"), password);
        PrivateKey dServer = (PrivateKey)ks.getKey(alias, password);
        //Decrypt: server's private key 
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        byte[] in = (byte[]) is.readObject();
		cipher.init(Cipher.DECRYPT_MODE, dServer);
		byte[] plaintText = cipher.doFinal(in);
		System.out.println("The TEXT FROM CLIENT  is: " + new String(plaintText));
		server.close();
	}

}