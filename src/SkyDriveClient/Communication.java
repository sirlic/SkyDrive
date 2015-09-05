package SkyDriveClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
*实现控制台输入输出，与服务器交流
*@author lichaung
*@date  2015/8/29
*
*/

/**
 * 利用单例模式得到一个唯一的沟通中心
 *
 */

public class Communication {
	
	private static Socket socket = null;
	private static BufferedReader localReader = null;
	private static BufferedReader remoteReader = null;
	private static PrintWriter remoteWriter = null;
	private static Communication communication = null;
	private String strTalk = "";
	
	private Communication(){};
	
	private static void connect() {
		try {
			socket = new Socket(Configure.getServerAddress(),Configure.getServerPort());
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		
		try {
			remoteReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		
		try {
			remoteWriter = new PrintWriter(socket.getOutputStream(),true);
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		
	}
	
	public static void init() {		
		localReader = new BufferedReader(new InputStreamReader(System.in));		
	}
	
	public static Communication getInstance () {
		if(communication == null) {
			communication = new Communication();
		}
		init();
		return communication;
	}
	
	/**输出字符串到本地终端
	 * @return String
	*/
	public void printLocal(String s) {
		System.out.println(s);
	}
	
	/**输出字符串到远程终端
	 * @return String
	 */
	public void printRemote(String s) {
		if(socket == null) {
			connect();
		}
		remoteWriter.println(s);
	}
	
	/**从本地终端读取后返回字符串
	*@return String
	*/
	public String readLocal() {
		try {
			strTalk = localReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strTalk;
	}
	
	/**从远程终端读取输入
	 * @return String
	*/
	public String readRemote() {
		if(socket == null) {
			connect();
		}
		try {
			if((strTalk = remoteReader.readLine())!=null) {
            	printLocal(strTalk);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strTalk;
	}
	
	public void downLoadFile(String path, String file) {
		if(socket == null) {
			connect();
		}
		new DownLoad(path, file, socket).start();
		try {
			socket.shutdownInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
		close();
	}
	
	public void upLoadFile(String filePath) {
		if(socket == null) {
			connect();
		}
		new UpLoad(filePath, socket).start();
		try {
			socket.shutdownInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
		close();
	}
	
	/**关闭流,清扫现场
	 */
	private static void close() {
		try {
			if(socket != null)
				socket.close();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		
		try {
			if(localReader != null)
				localReader.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		try {
			if(remoteReader != null)
				remoteReader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(remoteWriter != null)
			remoteWriter.close();

	}

}
