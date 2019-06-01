package thread;

import java.awt.geom.FlatteningPathIterator;
import java.net.DatagramSocket;
import java.security.interfaces.RSAKey;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;
import stream.ClientToClientConnectionStream;

public class ChatWithFriendThread implements Runnable
{
	private ClientToClientConnectionStream userDataCS;
	private JTextArea receive;
	private String name;

	private boolean flag = true;
	private DatagramSocket sendSocket;
	private DatagramSocket recSocket;
	public ChatWithFriendThread(JTextArea receive,ClientToClientConnectionStream userDataCS, 
			String name,DatagramSocket sendSocket,DatagramSocket recSocket) 
	{
		super();
		this.sendSocket = sendSocket;
		this.recSocket = recSocket;
		this.name = name;
		this.receive = receive;
		this.userDataCS = userDataCS;
	}
	
	@Override
	public void run() 
	{
		while (flag)
		{
			String message = userDataCS.read();
			if (message.indexOf("%MESSAGE%") == 0)
			{
				message = message.split(":")[1];
				receive.append(name + "    ");
				Date nowTime = new Date();
				SimpleDateFormat matter = new SimpleDateFormat(  
		                "现在时间:yyyy年MM月dd日 HH时mm分ss秒"); 
				
				receive.append(matter.format(nowTime) + "\n");
				receive.append(message);
			}
			else if ("I_HAVE_EXIT_THE_WINDOW".equals(message))
			{
				StopMe();
				recSocket.close();
				sendSocket.close();
			}
		}
	}
	public void StopMe()
	{
		flag = false;
	}
	
}
