import java.io.*;
import java.net.*;
import java.security.*;

public class ProtectedServer {
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException {
		DataInputStream in = new DataInputStream(inStream);

		// IMPLEMENT THIS FUNCTION.
		String user = in.readUTF();
		long t1 = in.readLong();
		double q1 = in.readDouble();
		long t2 = in.readLong();
		double q2 = in.readDouble();
		int length = in.readInt();
		byte[] digest2 = new byte[length];
		in.readFully(digest2);
		String password = lookupPassword(user);
		byte[] one_time = Protection.makeDigest(user, password, t1, q1);
		byte[] second_time = Protection.makeDigest(one_time, t2, q2);
		return MessageDigest.isEqual(digest2, second_time);

	}

	protected String lookupPassword(String user) {
		return "Harman";
	}

	public static void main(String[] args) throws Exception {
		int port = 4444;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();

		ProtectedServer server = new ProtectedServer();

		if (server.authenticate(client.getInputStream()))
			System.out.println("Client logged in.");
		else
			System.out.println("Client failed to log in.");

		s.close();
	}
}