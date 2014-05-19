package se.kotlinski.imageRenamer.models;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by Simon on 2014-01-01.
 */
public class ImageDescriber {
	private String md5;
	private String filePaths;

	private String timeStamp;

	private int year;
	private int month;
	private int day;

	private int hour;
	private int minute;
	private int second;

	public ImageDescriber(File file) {
		md5 = generateMd5(file);
		System.out.println(file.getName() + " md5: " + md5);
	}


	private String generateMd5(final File file) {
		FileInputStream fis = null;
		byte[] hash = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("Md5");
			fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DigestInputStream dis = new DigestInputStream(bis, messageDigest);

			// read the file and update the hash calculation
			while (dis.read() != -1) {
				;
			}

			// get the hash value as byte array
			hash = messageDigest.digest();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}


		return byteArray2Hex(hash);
	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}


	public String getMd5() {
		return md5;
	}
}
