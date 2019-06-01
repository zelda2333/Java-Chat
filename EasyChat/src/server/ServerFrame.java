package server;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import stream.ServerClientConnectionStream;
import tools.ServerOperationXML;
import tools.UserInformation;

public class ServerFrame extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ServerClientConnectionStream userCS;
	
	private Set<UserInformation> userSet;
	private Map<String, ServerClientConnectionStream> userMap;
	private static final int PORT = 9090;
	private ServerSocket serverSocket;
	
	private JTextArea recMsg = new JTextArea();
	private JTextArea state = new JTextArea();
	private JPanel onlineUserlist;
	
	private JButton start = new JButton("启动服务器");
	
	private JPanel backPanel;
	
	public ServerFrame()
	{
		super();
		userMap = new HashMap<String, ServerClientConnectionStream>();
		userSet = new HashSet<UserInformation>();
		
		createFrame();
	}
	public static void main(String[] args) 
	{
		new ServerFrame();
	}
	
	//创建窗体
	private void createFrame() 
	{
		setSize(500,650);
		backPanel = new JPanel();
		backPanel.setLayout(null);
		
		JLabel jLabel = new JLabel("服务器");
		jLabel.setBounds(220, 15, 50, 40);
		jLabel.setFont(new Font("黑体", 1, 14));
		backPanel.add(jLabel);
		
		state.setEditable(false);
		recMsg.setEditable(false);
		
		onlineUserlist = new JPanel(new GridLayout(50, 1, 0 , 0));
		onlineUserlist.setBackground(Color.white);
		
		JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JScrollPane userList = new JScrollPane(onlineUserlist);
		JScrollPane funState = new JScrollPane(state);
		JScrollPane msgBox = new JScrollPane(recMsg);
		
		jTabbedPane.setBounds(20, 50, 460, 500);
		jTabbedPane.add("运行状态",funState);
		jTabbedPane.add("用户列表",userList);
		jTabbedPane.add("消息列表",msgBox);
		backPanel.add(jTabbedPane);
		
		start.setBounds(170, 570, 150, 30);
		backPanel.add(start);
		start.setActionCommand("start");
		start.addActionListener(this);
		
		recMsg.setFont(new Font("黑体", 1, 14));
		state.setFont(new Font("黑体", 1, 14));
		
		
		add(backPanel);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				int t = JOptionPane.showConfirmDialog(null, "确认要退出服务器"
						+ "吗？", "确认退出", JOptionPane.OK_CANCEL_OPTION);
				if (t == JOptionPane.OK_OPTION)
				{
					System.exit(0);
				}
				else
					return;
			}
		});
	}
	//监听按钮动作
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getActionCommand().equals("start"))
		{
			System.out.println("服务器已启动，等待客户端连接...");
			state.append("服务器已启动，等待客户端连接...\r\n");
			try 
			{
				serverSocket = new ServerSocket(PORT);
				new Thread(new Runnable() 
				{
					@Override
					public void run() 
					{
						StartServer();
					}
				}).start();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			start.setEnabled(false);
		}
		
	}
	private void StartServer()
	{
		while (true) 
		{
			try 
			{
				Socket socket = serverSocket.accept();
				userCS = new ServerClientConnectionStream(socket);
				new ServerThread(userCS,userSet, userMap,recMsg,onlineUserlist,state).start();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	class ClientThread
	{
		private ServerClientConnectionStream userCS;
		public ClientThread(ServerClientConnectionStream userCS) 
		{
			super();
			this.userCS = userCS;
		}
	}
	
}
