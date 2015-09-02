package SkyDriveClient;


/**
*实现初始化功能，读取默认的配置
*@author lichaung
*@date  2015/8/29
*
*/

public class SkyDriveClient {
	
    private static final String[] options = {"  0    列出选项","  1    设置网盘服务器地址",
                "  2    设置网盘服务器端口","  3    文件上传","  4    文件下载","  5    退出"};
    private static final String[] fileOpts = {"0 返回上层","1 设置文件路径","2 设置存储路径","3 开始传输"};
    private static final String[] tip1 = {"请选择命令 (0-5):"};
    private static final String[] tip2 = {"请选择命令 (0-3):"};
	private static  Communication con = Communication.getInstance();
	private static String command = "";
	
	public static void main(String[] args) {
		showOpts(options);
		while(true) {
			showOpts(tip1);
			command = con.readLocal();
			if(command.matches("5")) {
				con.printLocal("程序已退出");
				break;
			}
			switch (command) {
				case "0":
					showOpts(options);
					break;
				case "1":
					con.printLocal("请输入网盘服务器地址: ");
					command = con.readLocal();
					if(!Configure.setServerAddress(command)) {
						con.printLocal("无效的IP地址");
					}
					break;
				case "2":
					con.printLocal("请输入网盘服务器端口号(0-65545):");
					command = con.readLocal();
					if(!Configure.setServerPort(Integer.parseInt(command))) {
						con.printLocal("无效的端口号");
					}
					break;
				case "3":
					showOpts(fileOpts);
					fileHandle(true);
					break;
				case "4":
					showOpts(fileOpts);
					fileHandle(false);
					break;
				case "5":
					
					break;
	
				default:
					break;
				}
		}
	}
	
	private static void showOpts(String[] opts) {
		for(int i = 0; i < opts.length; i++) {
			con.printLocal(opts[i]);
		}
	}
	
	public static void fileHandle(boolean flag) {
		while(true) {
            showOpts(tip2);
			command = con.readLocal();
			if(command.matches("0")) {
				showOpts(options);
				break;
			}
			switch (command) {
				case "0":
					break;
				case "1":
					if(flag == true){
						con.printLocal("请输入上传文件路径:");
						command = con.readLocal();
						if(!Configure.setLocalFilePath(command)) {
							con.printLocal("文件路径不存在");
						}
					}else {
						con.printLocal("请输入网盘文件路径:");
						command = con.readLocal();
						if(!Configure.setRemoteFilePath(command)) {
							con.printLocal("网盘文件路径不存在");
						}
					}
					break;
				case "2":
					if(flag == true){
						con.printLocal("请输入网盘文件路径:");
						command = con.readLocal();
						if(!Configure.setRemoteFilePath(command)) {
							con.printLocal("网盘文件路径不存在");
						}
					}else {
						con.printLocal("请输入存储文件路径:");
						command = con.readLocal();
						if(!Configure.setLocalFilePath(command)) {
							con.printLocal("文件路径不存在");
						}
					}
					break;
				case "3":
					if(flag == true) {
						con.upLoadFile(Configure.getlocalFilePath());
					}else {
						con.downLoadFile(Configure.getStorePath(), Configure.getRemoteFilePath());
					}
					break;
				default:
					break;
			}
		}
		
	}

}

