import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MD5_Hash {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub

		Scanner in = new Scanner(System.in);
		System.out.println("Enter a String");
		String str = in.next();
		System.out.println("MD5 Digest:: " + MD5Hash(str));
		System.out.println("SHA-256 Digest:: " + SHAHash(str));
		in.close();

	}

	public static String MD5Hash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes("UTF-8"));
		byte byteData[] = md.digest();
		StringBuffer strbuf = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			strbuf.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return strbuf.toString();

	}

	public static String SHAHash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(str.getBytes("UTF-8"));
		byte byteData[] = md.digest();
		StringBuffer strbuf = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			strbuf.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return strbuf.toString();
	}

}
