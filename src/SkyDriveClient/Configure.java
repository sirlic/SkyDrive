package SkyDriveClient;

/**
*实现配置功能
*@author lichaung
*@date  2015/8/29
*
*/

import java.io.File;

public class Configure {
	
	private static String serverAddress = "127.0.0.1";
	private static int serverPort = 10000;
	private static String storePath = "";
	private static String localFilePath = "";
	private static String remoteFilePath = "";
	
	public static String getServerAddress () {
		return serverAddress;
	}
	
	public static int getServerPort() {
		return serverPort;
	}
	
	public static String getStorePath() {
		return storePath;
	}
	
	public static String getlocalFilePath() {
		return localFilePath;
	}
	
	public static String getRemoteFilePath() {
		return remoteFilePath;
	}
	
	public static boolean setServerAddress(String sa) {
		if(serverAddress.matches(
				"([0-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}")) {
			serverAddress = sa;
			return true;
		}
		return false;
	}
	
	public static boolean setServerPort(int sp) {
		if(0 < sp && sp < 65535){
			serverPort = sp;
			return true;
		}
		return false;
	}
	
	public static boolean setStorePath(String sp) {
		File path = new File(sp);
		if(path.isDirectory()) {
			storePath = sp;
			return true;
		}
		return false;
	}
	
	public static boolean setLocalFilePath(String lfp) {
		File file = new File(lfp);
		if(file.exists()) {
			localFilePath = lfp;
			return true;
		}
		return false;
	}
	
	public static boolean setRemoteFilePath(String rfp) {
		Communication con = Communication.getInstance();
		remoteFilePath = rfp;
		con.printRemote("文件路径");
        if(con.readRemote().matches("好的")) {
            con.printRemote(remoteFilePath);
		}
		
		return true;
	}
}
