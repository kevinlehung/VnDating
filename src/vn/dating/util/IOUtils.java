package vn.dating.util;

import java.io.OutputStream;

public class IOUtils {
	public static void closeQuietly (OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (Exception e) {
				
			}
		}
	}
}
