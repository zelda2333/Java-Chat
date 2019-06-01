package server;
/*
 * 搭建服务器思路：
 * 		1、在开始按钮单击事件触发之后，创建一个线程用来启动一个子线程监听一个客户端。
 * 		2、每次有一个客户端连接，则产生一个子线程用来监听该客户端与服务器搭建的通道
 * 服务器数据处理思路：
 * 		接收到用户信息之后，根据起始符号进行判断消息类型
 */
import java.lang.ProcessHandle.Info;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import client.FriendsListFrame;
import stream.ServerClientConnectionStream;
import tools.JlableTools;
import tools.ServerOperationXML;
import tools.UserInformation;

public class ServerThread extends Thread
{
	private ServerClientConnectionStream userCS;
	private UserInformation userInfo;
	
	 private StringBuffer userList;
	private Map<String, ServerClientConnectionStream> userMap;
	private Set<UserInformation> userSet;
	private ServerOperationXML serverOperationXML;
	
	private JTextArea recMsg;
	private JTextArea state;
	private JPanel onlineUserlist;
	
	public ServerThread(ServerClientConnectionStream userCS,Set<UserInformation> userSet,
			Map<String, ServerClientConnectionStream> userMap,
			JTextArea recMsg, JPanel onlineUserlist,JTextArea state)
	{
		super();
		this.userCS = userCS;
		this.userSet = userSet;
		this.userMap = userMap;
		this.recMsg = recMsg;
		this.state = state;
		this.onlineUserlist = onlineUserlist;
		serverOperationXML = new ServerOperationXML();
		userInfo = new UserInformation();
		userList = new StringBuffer();
	}
	@Override 
	public void run()
	{
		while(true)
		{
			String message = userCS.read();
			//用户登录
			if (message.indexOf("%LOGIN%") == 0 && message != null)
	        {
				userInfo.setInfo(message);
				//当密码正确时
				if (userInfo.getPassword().equals
						(serverOperationXML.ReadNode(userInfo.getAccount(), "password")))
				{
					
					//判定该用户是否已经登录
					if (userMap.containsKey(userInfo.getAccount()))
					{
						userCS.send("remote_login");
					}
					else 
					{
						recMsg.append(userInfo.getName() + "("+ userInfo.getAccount() +")"+ "上线了！\n");
						userSet.add(userInfo);
						userMap.put(userInfo.getAccount(),userCS);
						userCS.send("successfully");
						sendOnlineUserlistToClient();
						
					}
				}
				//当密码错误时
				else if (!userInfo.getPassword().equals
						(serverOperationXML.ReadNode(userInfo.getAccount(), "password")))
					userCS.send("password_error");
				

			} 
			//注册用户
			else if (message.indexOf("%REGISTERED%:") == 0)
			{
				userInfo.setAllInfo(message);
				//将新用户信息写入数据库
				serverOperationXML.AddXmlNode(userInfo.getAccount(), userInfo.getPassword(),
						userInfo.getName(), userInfo.getUserQuestion(), userInfo.getUserAnswer(),
						userInfo.getUserPortraitNum(), userInfo.getUserAautograph());
			}
			//修改密码
			else if (message.indexOf("%Change_password%") == 0)
			{
				userInfo.setPassword(message.split(":")[1]);
				userInfo.setAccount(message.split(":")[2]);
				serverOperationXML.ChangeXmlDocument(userInfo.getAccount(), "password", userInfo.getPassword());
			}
			//用户退出
			else if (message.indexOf("%EXIT%") == 0)
            {
				System.out.println("收到退出请求...");
				userInfo.setInfo(message);
				userMap.remove(userInfo.getAccount());
				userSet.remove(userInfo);
				userList.append("%EXIT%");
				sendOnlineUserlistToClient();
            }
			else if (message.indexOf("%MESSAGE%:") == 0)
			{
				String account = message.split(":")[1];
				for (String temp : userMap.keySet())
				{
					//谁发来的消息-消息内容
					if (temp.equals(account))
						userMap.get(temp).send("%FROM%:" + message.split(":")[3] +":" + message.split(":")[2]);
				}
			}
			
		}
	}
		/*
		 * 该方法的作用：
		 * 1、刷新服务器端的在线用户列表
		 * 2、将该用户的部分信息发送给所有用户
		 * userMap是一个集合，存放的是每个用户的userCS
		 * userSet是一个Userinformation类型的集合,存放的是所有当前在线的好友
		 */
	//此处ID的作用为传递本次接收列表的账号，避免把自己发送出去
	public void sendOnlineUserlistToClient() 
	{
		System.out.println("正在向客户端发送用户列表...");
		onlineUserlist.removeAll();
		//服务器显示界面上添加在线好友
		for (UserInformation userInformation : userSet)
		{
			JlableTools addUser = new JlableTools();
			addUser.addJlable(userInformation.getAccount(), userInformation.getName(),userInformation.getPort(), onlineUserlist,userCS,userInformation);
			//账号-名字-头像-签名-端口号
			String info = userInformation.getAccount()+ ":" + serverOperationXML.ReadNode(userInformation.getAccount(), "name") + ":" 
					+ serverOperationXML.ReadNode(userInformation.getAccount(), "portraitNum") + ":"
					+ serverOperationXML.ReadNode(userInformation.getAccount(), "autograph") + ":"
					+ userInformation.getPort();
			System.out.println(info);
			for (String temp : userMap.keySet())
			{
				userMap.get(temp).send("%USER_LIST%:" + info);
			}
		}
		for (String temp : userMap.keySet())
		{
			userMap.get(temp).send("%END_LIST%");
		}
		if (userList.indexOf("%EXIT%") == -1)
		{
			state.append("已向客户端账号：" + userInfo.getAccount() + "发送了用户列表消息！\n");
		}
		else
		{
			recMsg.append(userInfo.getName() + "("+ userInfo.getAccount() +")"+ "下线了！\n");
		}
		userList.delete(0, userList.length());
	}
}