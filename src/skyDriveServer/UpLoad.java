package SkyDriveServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 将文件下载到用户端
 * @author lichuang
 * 
 * @date 2015/8/30
 *
 */

public class UpLoad {
	
	private FileInputStream fileInputStream = null;
	private OutputStream outputStream = null;
	private byte[] data = new byte[1024];
	
	public UpLoad(Socket socket,String file) {
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			close();
		}
		try {
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}
	
	public boolean start() {
		try {
			while((fileInputStream.read(data)) != -1) {
				outputStream.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
			close();
			return false;
		}
		close();
		return true;
	}
	private void close() {
		if(fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
