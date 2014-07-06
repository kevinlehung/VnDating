package vn.dating.util;

import java.io.InputStream;
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
	
	public static void closeQuietly (InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
				
			}
		}
	}
}
