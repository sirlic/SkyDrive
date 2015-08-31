package skyDriveServer;

/**
*网盘服务器程序
*@author lichaung
*@date  2015/8/29
*
*/


import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.lang.Thread;

public class SkyDriveServer {
	
	private static int port = 10000;       //监听的端口号
	private static Socket socket = null;
	private static ServerSocket serverSocket = null;
	
	public static void main(String[] args) {
		parseArgs(args);
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		
		
		while(true) {
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				close();
			}
			new Thread(new HandleThread(socket)).start();
		}
	}
	
	private static void parseArgs(String[] args) {
		if(args.length == 0)
			return;
		if(args[0].matches("-(p|P)")) {
			port = Integer.parseInt(args[1]);
		}else {
			System.out.println("网盘服务器程序将监听在默认端口：10000");
		}
	}
	
	private static void close() {
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

