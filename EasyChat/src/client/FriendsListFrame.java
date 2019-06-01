package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessHandle.Info;
import java.net.Socket;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import stream.ServerClientConnectionStream;
import thread.FlushOnlineUserlistThread;
import tools.JlableTools;
import tools.UserInformation;

public class FriendsListFrame extends JFrame
{
	private static final long serialVersionUID = -3556151740887888142L;
	/**
	 * @author Albert Niu in Main Studio
	 * 		2018-5-26  13：50
	 */
	private Socket socket;
	private String ID;
	private UserInformation userInfo;
	private ServerClientConnectionStream userCS;
	private JPanel imagePanel;  
	private ImageIcon HeadBackground;
	private ImageIcon MiddleBackgouund;
	private JPanel messageJp;
	private JPanel friendsJp;
	
	private Set<String> userSet;
	
	private Thread readMessageFromServer;
	private FlushOnlineUserlistThread flushOnlineUserlistThread;
	
	public FriendsListFrame(ServerClientConnectionStream userCS, 
			UserInformation userInfo, String Up, String Down)
	{
		super();
		this.ID = userInfo.getUserPortraitNum();
		this.userInfo = userInfo;
		this.userCS = userCS;
		this.userSet = new HashSet<String>();
		createFrame(Up,Down);
	}
	
	@SuppressWarnings("deprecation")
	public void createFrame(String Up, String Down) 
	{
		//上部分背景
		HeadBackground = new ImageIcon("pictures/UpPictures/" + Up + ".jpg");// 背景图片  
		HeadBackground.setImage(HeadBackground.getImage().getScaledInstance(360, 150, Image.SCALE_DEFAULT));
		JLabel headLable = new JLabel(HeadBackground);// 把背景图片显示在一个标签里面  
		// 把标签的大小位置设置为图片刚好填充整个面板  
		headLable.setBounds(0, 0, HeadBackground.getIconWidth(),  
				  HeadBackground.getIconHeight());  
		// 把背景图片添加到分层窗格的最底层作为背景  
		this.getLayeredPane().add(headLable, new Integer(Integer.MIN_VALUE));  
		
		
		//中部分背景
		MiddleBackgouund = new ImageIcon("pictures/DownPictures/"+ Down +".jpg");// 背景图片  
		MiddleBackgouund.setImage(MiddleBackgouund.getImage().getScaledInstance(360, 650, Image.SCALE_DEFAULT));
		JLabel middleLable = new JLabel(MiddleBackgouund);// 把背景图片显示在一个标签里面  
		// 把标签的大小位置设置为图片刚好填充整个面板  
		middleLable.setBounds(0, 150, MiddleBackgouund.getIconWidth(),  
					MiddleBackgouund.getIconHeight());   
		// 把背景图片添加到分层窗格的最底层作为背景  
		this.getLayeredPane().add(middleLable, new Integer(Integer.MIN_VALUE));
		
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
		imagePanel = (JPanel) this.getContentPane();  
		imagePanel.setOpaque(false);  
		// 内容窗格默认的布局管理器为BorderLayout  
		imagePanel.setLayout(new FlowLayout(0,0,0));  
		this.getLayeredPane().setLayout(null);  
				
		//头像
		ImageIcon head = new ImageIcon("pictures/Head/" + userInfo.getUserPortraitNum() + ".jpg");
		head.setImage(head.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
		JButton jbtnHead = new JButton();
		jbtnHead.setBounds(10, 10, 80, 80);
		imagePanel.setLayout(null);
		jbtnHead.setSize(80,80);
		jbtnHead.setIcon(head);
		imagePanel.add(jbtnHead);
		//昵称
		JLabel name = new JLabel(userInfo.getName() + "（" + userInfo.getAccount() + "）");
		name.setForeground(new Color(0, 0, 0));
		name.setFont(new Font("华文楷体", 1, 15));
		name.setBounds(100,10,260,30);
		imagePanel.add(name);
		//签名
		JLabel autograph = new JLabel(userInfo.getUserAautograph());
		autograph.setForeground(new Color(0, 0, 0));
		autograph.setFont(new Font("华文楷体", 1, 13));
		autograph.setBounds(100,10,200,100);
		imagePanel.add(autograph);

		messageJp = new JPanel(new GridLayout(50, 1, 0 , 0));
		messageJp.setBackground(Color.white);
		friendsJp = new JPanel(new GridLayout(50, 1, 0 , 0));
		friendsJp.setBackground(Color.white);
		
		JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JScrollPane message = new JScrollPane(messageJp);
		JScrollPane friend = new JScrollPane(friendsJp);
		jTabbedPane.add("消息",message);
		jTabbedPane.add("好友",friend);
		jTabbedPane.setBounds(5, 150, 350, 600);
		imagePanel.add(jTabbedPane);
		
		//下部的 设置 && 添加好友
		ImageIcon setup = new ImageIcon("pictures/system/SetUp.png");
		setup.setImage(setup.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
		ImageIcon find = new ImageIcon("pictures/system/Find.png");
		find.setImage(find.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
		JButton jbtnSetUp = new JButton();
		JButton jbtnFind = new JButton();
		jbtnSetUp.setIcon(setup);
		jbtnFind.setIcon(find);
		jbtnSetUp.setBounds(8,743,30,33);
		jbtnFind.setBounds(50,743,30,33);
		
		jbtnSetUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SetUpFrame(FriendsListFrame.this,userCS,userInfo);
			}
		});
		
		jbtnFind.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FindFriendsFrame();
			}
		});
		
		imagePanel.add(jbtnSetUp);
		imagePanel.add(jbtnFind);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				
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
				userCS.send("%EXIT%:" + userInfo.getIP() + ":" + userInfo.getPort() + ":" 
						 + userInfo.getName() + ":" + userInfo.getAccount() + ":" + userInfo.getPassword() 
								+ ":" + userInfo.getUserPortraitNum() + ":" + userInfo.getUserAautograph());
				try {
					userCS.getSocket().close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
		});

		setLocationRelativeTo(null);
		this.setResizable(false); 

	}
	public void showMe() 
	{
		setSize(360,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true); 
		
		flushOnlineUserlistThread = 
				new FlushOnlineUserlistThread(friendsJp, messageJp, userCS,userSet, userInfo.getAccount(),userInfo);
		readMessageFromServer = new Thread(flushOnlineUserlistThread);
		readMessageFromServer.start();
	}
}
