package thread;

import java.util.Set;

import javax.swing.JPanel;

import stream.ServerClientConnectionStream;
import tools.JlableTools;
import tools.UserInformation;
/*
 * 刷新好友列表思路：当有用户登录的时候，服务器会将所有在线用户的信息义字符串的形式，发送给所有在线用户
 * 那么，客户端只需要读取该条信息，并对信息进行解析即可
 * 思路：
 * 		1、创建一个集合变量名叫userSet，类型为Set型 用来储存服务器发过来的字符串
 * 		2、读取到信息之后，判定该条信息的起始符号是否为“%USER_LIST%”，是则将内容存储到userSet中
 * 		3、当接收到"%END_LIST%"语句的时候，则服务器发送的好友列表信息结束，结束之后则解析userSet集合中的所有内容，即
 * 			遍历userSet，每次遍历都将用户信息存到userInfo中，用userInfo的setFriendInfo()方法进行赋值
 * 		4、得到了所有在线用户的ID、昵称、头像编号、个性签名之后，动态生成Jlable，添加到好友列表的Jpanel上
*/
public class FlushOnlineUserlistThread implements Runnable
{
	private JPanel friend;
	private JPanel message;
	private boolean flag = true;
	private ServerClientConnectionStream userCS;
	private UserInformation userInfoTemp;
	private int i = 0;
	private Set<String> userSet;
	private String ID;
	
	private UserInformation userInfo;
	
	public FlushOnlineUserlistThread(JPanel friend,JPanel message, ServerClientConnectionStream userCS,
			Set<String> userSet, String ID, UserInformation userInfo) 
	{
		super();
		this.ID = ID;
		this.friend = friend;
		this.message = message;
		this.userSet = userSet;
		this.userCS = userCS;
		this.userInfo = userInfo;
		userInfoTemp = new UserInformation();
	}
	@Override
	public void run() 
	{
		
		while(true)
		{
			String message = userCS.read();
			if (message.indexOf("%USER_LIST%") == 0 && message != null)
			{
				userSet.add(message);
			}
			
			else if (message.indexOf("%END_LIST%") == 0 && message != null)
			{
				flushGUI();
			}
			else if (message.indexOf("%FROM%") == 0 && message != null) 
			{
				System.out.println(message);
			}
		}
		
	}
	private void flushGUI() 
	{
		friend.removeAll();
		for (String string : userSet)
		{
			userInfoTemp.setFriendInfo(string);
			if (ID.equals(userInfoTemp.getAccount()))
				continue;
			else
			{
				JlableTools addUser = new JlableTools();
				addUser.addJlable(userInfoTemp.getAccount(), userInfoTemp.getName(),
						userInfoTemp.getPort(), friend, userCS,userInfo);
			}
		}
		userSet.clear();
	}
	public void stopMe()
	{
		this.flag = false;
	}
}
