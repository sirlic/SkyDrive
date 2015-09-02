package SkyDriveClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
*实现文件上传
*@author lichaung
*@date  2015/8/29
*
*/

public class UpLoad {
	
	private FileInputStream fileInputStream = null;
	private OutputStream outputStream = null;
	private Communication con = null;
	private File file;
	private Socket socket;
	private byte[] data = new byte[1024];
	
	public UpLoad(String file, Socket socket) {
		this.file = new File(file);
		this.socket = socket;
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
		con = Communication.getInstance();
	}
	
	public void start() {
        con.printRemote("文件上传");
        if(con.readRemote().matches("好的")) {
            con.printLocal("开始上传:  " + file.getName() + "到 " 
                    + socket.getInetAddress() + ":" + socket.getPort());
            try {
                while(fileInputStream.read(data) != -1) {
                    outputStream.write(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
            con.printLocal("文件上传完毕。");
        }
		close();
	}
	
	private void close() {
		try {
			if(fileInputStream != null)
				fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(outputStream != null)
				outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
