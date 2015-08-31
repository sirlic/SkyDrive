package skyDriveServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 有这个线程处理客户端请求
 * @author lichuang
 * 
 * @date 2015/8/30
 *
 */

public class HandleThread implements Runnable{
	
	private Socket socket = null;
	private static BufferedReader bufReader = null;
	private static String strTalk = "";
	private static PrintWriter printWriter= null;
	private String file;
	public HandleThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			bufReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream());
			while((strTalk = bufReader.readLine()) != null) {
				switch(strTalk) {
					case "文件路径":
						printWriter.write("好的");
						strTalk = bufReader.readLine();
						file = strTalk;
						break;
					case "文件上传":
						printWriter.write("好的");
						printWriter.flush();
						new DownLoad(socket,file).start();
						break;
					case "文件下载":
						printWriter.write("好的");
						printWriter.flush();
						new UpLoad(socket,file).start();
						break;
					default :
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	private void close() {
		if(bufReader != null) {
			try {
				bufReader.close();
			} catch (IOException e) {	
				e.printStackTrace();
			}
		}
		if(printWriter != null) {
			printWriter.close();
		}
	}
}
