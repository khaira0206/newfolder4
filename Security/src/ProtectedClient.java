import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Date;

public class ProtectedClient {
	public void sendAuthentication(String user, String password, OutputStream outStream)
			throws IOException, NoSuchAlgorithmException {
		DataOutputStream out = new DataOutputStream(outStream);

		// IMPLEMENT THIS FUNCTION.
		long t1 = (new Date()).getTime();
		double q1 = Math.random();
		byte[] digest1 = Protection.makeDigest(user, password, t1, q1);
		long t2 = (new Date()).getTime();
		double q2 = Math.random();
		byte[] digest2 = Protection.makeDigest(digest1, t2, q2);
		out.writeUTF(user);
		out.writeLong(t1);
		out.writeDouble(q1);
		out.writeLong(t2);
		out.writeDouble(q2);
		out.writeInt(digest2.length);
		out.write(digest2);
		out.flush();
	}

	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 4444;
		String user = "Harman";
		String password = "Harman  ";
		Socket s = new Socket(host, port);

		ProtectedClient client = new ProtectedClient();
		client.sendAuthentication(user, password, s.getOutputStream());

		s.close();
	}
}