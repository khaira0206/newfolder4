import java.io.*;
import java.net.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Scanner;

import javax.crypto.*;

public class X509Client {

	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
		int port = 1234;
		Socket s = new Socket(host, port);

		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		InputStream in = new FileInputStream("mycert.cer");
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		in.close();
		// Read the certificate file

		System.out.println("The  certificate file  is:");
		System.out.println(cert.toString());

		Date date = cert.getNotAfter();
		// checking for certificate validation
		if (date.after(new Date()))

			System.out.println("The certificate is valid now.and is valid from " + cert.getNotBefore() + " to "
					+ cert.getNotAfter());

		else

			System.out.println("The certificate is expired.");

		// checking the signature

		try {
			cert.checkValidity();
			System.out.println("The certificate is valid.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Enter a string:");
		Scanner input = new Scanner(System.in);
		String message = input.nextLine();

		RSAPublicKey eServer = (RSAPublicKey) cert.getPublicKey();
		// Retrieve the public key from the certificate

		// Encrypt : server's public key
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, eServer);
		byte[] cipherText = cipher.doFinal(message.getBytes());
		System.out.println("The ciphertext is: " + cipherText);
		os.writeObject(cipherText);
		//os.flush();
		os.close();
		s.close();
		input.close();
	}

}