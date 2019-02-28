package utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadXmlToString {
	
	private ReadXmlToString() {
	}
	private static final class Singleton{
		private static final ReadXmlToString INSTANCE = new ReadXmlToString();
	}
	public static  ReadXmlToString getInstance() {
		return Singleton.INSTANCE;
	} 

	public String readXml(String filePath) {
		
		if (filePath == null) {
			try {
				throw new Exception("file path is null");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		File file = null;
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String text = null;
			while((text = bufferedReader.readLine()) != null) {
				stringBuilder.append(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (inputStreamReader != null) {
			try {
				inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return stringBuilder.toString();
	}
	
}
