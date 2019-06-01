package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import stream.ClientToClientConnectionStream;
import thread.ChatWithFriendThread;
import tools.ClientOperationXML;
import tools.UserInformation;

public class ChatRoomClientFrame extends JFrame implements ActionListener
{


	/**
	 * @author Albert Niu in Main Studio
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private ClientOperationXML xml;
	//该线程用于监听其他客户端发来的消息
	private Thread thread;
	private UserInformation userInfo;
	private int port;
	private String name;
	public ChatRoomClientFrame(String ID,int port,UserInformation userInfo,String name)
	{
		super();
		this.name = name;
		this.ID = ID;
		this.port = port;
		this.userInfo = userInfo;
		xml = new ClientOperationXML();
		createFrame();
	}
	
	private JPanel imagePanel;  
	private ImageIcon HeadBackground;
	

	private JTextArea receive = new JTextArea();
	private JTextArea send = new JTextArea();
	private JButton biaoqing = new JButton();
	private JButton jietu = new JButton();
	private JButton yuyin = new JButton();
	private JButton zhendong = new JButton();
	private JButton wenjian = new JButton();
	private JButton jilu = new JButton();
	private JButton jbtnClose = new JButton("关闭");
	private JButton jbtnSend = new JButton("发送");
	private JScrollPane RecJs;
	private JScrollPane SendJs;
	private void createFrame()
	{
		setSize(600,600);
		setResizable(false);
		
		//上部分背景
		HeadBackground = new ImageIcon("pictures/UpPictures/19.jpg");// 背景图片  
		HeadBackground.setImage(HeadBackground.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT));
		JLabel headLable = new JLabel(HeadBackground);// 把背景图片显示在一个标签里面  
		// 把标签的大小位置设置为图片刚好填充整个面板  
		headLable.setBounds(0, 0, HeadBackground.getIconWidth(),  
				  HeadBackground.getIconHeight());  
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
		imagePanel = (JPanel) this.getContentPane();  
		imagePanel.setOpaque(false);  
		// 内容窗格默认的布局管理器为BorderLayout  
		imagePanel.setLayout(new FlowLayout(0,0,0));  
		this.getLayeredPane().setLayout(null);  
		// 把背景图片添加到分层窗格的最底层作为背景  
		this.getLayeredPane().add(headLable, new Integer(Integer.MIN_VALUE)); 
	
		ImageIcon head = new ImageIcon("pictures/Head/"+xml.ReadNode(ID, "portraitNum") +".jpg");
		head.setImage(head.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
		
		imagePanel.setLayout(null);
		
		JButton jbtnHead = new JButton();
		jbtnHead.setBounds(10, 10, 80, 80);
		jbtnHead.setIcon(head);
		imagePanel.add(jbtnHead);
		
		//昵称
		JLabel name = new JLabel(xml.ReadNode(ID, "name") + "("+ID+")");
		name.setForeground(new Color(0, 0, 0));
		name.setFont(new Font("华文楷体", 1, 20));
		name.setBounds(100,10,400,30);
		imagePanel.add(name);
		JLabel autograph = new JLabel(xml.ReadNode(ID, "autograph"));
		autograph.setForeground(new Color(0, 0, 0));
		autograph.setFont(new Font("华文楷体", 1, 13));
		autograph.setBounds(100,10,480,100);
		imagePanel.add(autograph);
		
		
		//表情
		
		ImageIcon b = new ImageIcon("Pictures/system/表情.jpg");
		b.setImage(b.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		biaoqing.setIcon(b);
		biaoqing.setBounds(22,365,30,30);
		imagePanel.add(biaoqing);
		//截图
		
		ImageIcon j = new ImageIcon("Pictures/system/截图.jpg");
		j.setImage(j.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		jietu.setIcon(j);
		jietu.setBounds(65,365,30,30);
		imagePanel.add(jietu);
		//语音
		
		ImageIcon y = new ImageIcon("Pictures/system/语音.jpg");
		y.setImage(y.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		yuyin.setIcon(y);
		yuyin.setBounds(107,365,30,30);
		imagePanel.add(yuyin);
		//震动
		
		ImageIcon z = new ImageIcon("Pictures/system/震动.jpg");
		z.setImage(z.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		zhendong.setIcon(z);
		zhendong.setBounds(147,365,30,30);
		imagePanel.add(zhendong);
		//文件
		
		ImageIcon w = new ImageIcon("Pictures/system/文件.jpg");
		w.setImage(w.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		wenjian.setIcon(w);
		wenjian.setBounds(187,365,30,30);
		imagePanel.add(wenjian);
		//聊天记录
		ImageIcon l = new ImageIcon("Pictures/system/聊天.jpg");
		l.setImage(l.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		jilu.setIcon(l);
		jilu.setBounds(543,365,30,30);
		imagePanel.add(jilu);
		
		receive.setLineWrap(true);
		receive.setEditable(false);
		receive.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		
		send.setBackground(Color.white);
		receive.setLineWrap(true);
		send.setWrapStyleWord(true);
		send.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		
		RecJs = new JScrollPane(receive);
		RecJs.setBounds(20, 110, 560, 250);
		SendJs = new JScrollPane(send);
		SendJs.setBounds(20, 400, 560, 120);
		imagePanel.add(RecJs);
		imagePanel.add(SendJs);
		
		jbtnClose.setBounds(410,530,80,30);
		this.add(jbtnClose);
		jbtnClose.setActionCommand("close");
		jbtnClose.addActionListener(this);
		
		jbtnSend.setBounds(500,530,80,30);
		this.add(jbtnSend);
		jbtnSend.setActionCommand("send");
		jbtnSend.addActionListener(this);
		
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				sendStream.send("I_HAVE_EXIT_THE_WINDOW");
				recSocket.close();
				sendSocket.close();
                chatWithFriendThread.StopMe();
                System.out.println("窗口已经关闭！");
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("send"))
		{
			sendStream.send("%MESSAGE%:" + send.getText());
			receive.append(name + "    ");
			Date nowTime = new Date();
			SimpleDateFormat matter = new SimpleDateFormat(  
	                "现在时间:yyyy年MM月dd日 HH时mm分ss秒"); 
			
			receive.append(matter.format(nowTime) + "\n");
			receive.append(send.getText() + "\n");
		}
		else if (e.getActionCommand().equals("close"))
		{
			dispose();
		}
	}
	private DatagramSocket sendSocket;
	private ClientToClientConnectionStream sendStream;
	private DatagramSocket recSocket;
	private ClientToClientConnectionStream recStream;
	private ChatWithFriendThread chatWithFriendThread;
	private void InitData()
	{
		try 
		{
			//初始化发送部分的socket
			sendSocket = new DatagramSocket();
			//socket-自己的IP地址-自己的端口号
			sendStream = 
					new ClientToClientConnectionStream(sendSocket,
							InetAddress.getByName(userInfo.getIP()), userInfo.getPort());
			
			//开启接收线程，初始化接收部分的socket
			//需要监听的端口号
			recSocket = new DatagramSocket(port);
			recStream = new ClientToClientConnectionStream(recSocket);
			chatWithFriendThread = 
					new ChatWithFriendThread(receive, recStream, userInfo.getName(), sendSocket, recSocket);
			thread = new Thread(chatWithFriendThread);
			thread.start();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public void ShoeMe()
	{
		setVisible(true);
		InitData();
	}

}
