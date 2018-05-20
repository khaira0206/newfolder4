import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;

import javax.crypto.Cipher;

public class CipherServer
{
	public static void main(String[] args) throws Exception 
	{
		int port = 1115;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();
		System.out.println("Server is listening at 127.0.0.1:1115");
		ObjectInputStream getin = new ObjectInputStream(s.getInputStream());
		byte[] buff=new byte[2048];
		buff=(byte[]) getin.readObject();
		System.out.println(new String(buff));
		// YOU NEED TO DO THESE STEPS:
		// -Read the key from the file generated by the client.
		File file1 = new File("DESKEY.txt");
	    FileInputStream file2 = new FileInputStream(file1);
	    Key key;
	    ObjectInputStream file3 = new ObjectInputStream(file2);
	    try {
	      key = (Key) file3.readObject();
	    } finally {
	      file3.close();
	    }  
		
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		byte[] stringBytes = cipher.doFinal(buff);
	 
		
		String clear = new String(stringBytes, "UTF8");
		
		System.out.println(clear);
	}
}