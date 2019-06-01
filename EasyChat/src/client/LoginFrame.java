package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import stream.ServerClientConnectionStream;
import tools.ClientOperationXML;
import tools.UserInformation;


public class LoginFrame extends JFrame implements ActionListener
{    
	/**
	 * @author Albert Niu in Main Studio
	 * 		2018-5-25  20：50
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton login;
	private JTextField accountField; 		 	// 账号
    private JPasswordField passwordField;     	// 密码
    private JCheckBox remPassword; //记住密码
    private JCheckBox autoLogin; //自动登录
    private JLabel register; //注册账号
    private JLabel findPassword; //找回密码
    
    private String ID;
    private ImageIcon portraItImage;
    private JLabel portraItImageLable = new JLabel(portraItImage);
    
    private String IP;
    private Socket socket;
    private DatagramSocket datagramSocket;
    private UserInformation userInfo;
    private int userPort;//用户端口号
    private String userName; //用户名
    private String message;//存放从服务器接收到的信息
    private ServerClientConnectionStream userCS;
    private String temp;//头像
    
    private ClientOperationXML clientOperationXML = new ClientOperationXML();
    public static void main(String[] args) {
		new LoginFrame();
	}
    public LoginFrame(Socket socket)
    {
		super();
		this.socket = socket;
		createFrame();
	}
	public LoginFrame()
	{
		super();
		initData();
		createFrame();
	}
    private void initData()
    {
        final int SERVER_PORT = 9090;
        userInfo = new UserInformation();
        try
        {
        		IP = "127.0.0.1";
        		datagramSocket = new DatagramSocket();
        		userPort = datagramSocket.getLocalPort();
        		userInfo.setIP(IP);
        		userInfo.setPort(userPort);
        		datagramSocket.close();
            socket = new Socket(IP, SERVER_PORT);
            userCS = new ServerClientConnectionStream(socket);
            System.out.println("服务器已连接...");
        } catch (UnknownHostException e)
        {
            System.out.println("找不到 host 的任何 IP 地址!");
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println("创建套接字时发生 I/O 错误!");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    class RecThread extends Thread implements Runnable
    {
    		
		@Override
		public void run() 
		{
			System.out.println("等待服务器响应...");
			boolean flag = true;
			while (flag)
			{
				
	            if((message = userCS.read()) != null)
	            {
	        			if ("successfully".equals(message))
					{
						flag = false;
						System.out.println(ID + "登录成功！");
						userInfo.setPassword(passwordField.getText());
						userInfo.setAccount(ID);//账号
	                    userInfo.setName(userName);//昵称
	                    userInfo.setPort(userPort);//端口号
	                    userInfo.setUserPortraitNum(temp);//头像
	                    dispose();
	                    LoginLoading loading = new LoginLoading(temp);
	                    Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								loading.dispose();
								new FriendsListFrame(userCS, userInfo, "01", "01").showMe();
								stop();
							}
						}, 1500);
					}
					else if ("password_error".equals(message))
					{
						JOptionPane.showMessageDialog(null, "密码错误！");
						flag = false;
					}
					else if("remote_login".equals(message))
					{
						JOptionPane.showMessageDialog(null, "该账号已在其他地方登录！");
						flag = false;
					}
	            }
				
			}
				
		}
    	
    }
    
	private void createFrame() 
	{
		setTitle("EasyChat V1.0版");
		setBounds(0,0,410,350);
		setResizable(false);
		
        ImageIcon imageIcon = new ImageIcon("pictures/system/Logo.png");
		//设置图片在窗体中显示的大小
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(410, 150, Image.SCALE_DEFAULT));
		JLabel jLabel = new JLabel(imageIcon);
		JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		imagePanel.add(jLabel);
        
		portraItImage = new ImageIcon("pictures/Head/" + "Programmer" + ".jpg");
		portraItImage.setImage(portraItImage.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
		portraItImageLable.setIcon(portraItImage);
		
        //最底层
        JPanel mainLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        
        //账号
        accountField = new JTextField(15);
        //密码
        passwordField = new JPasswordField(15);
       
        //存放：账号、注册账号
        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        //存放：密码、找回密码
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
        //存放：账号、注册账号、密码、找回密码
        JPanel accPassHintPanel = new JPanel(new GridLayout(3, 15, 0, 0));
        
        
        //注册账号
        register = new JLabel("注册账号");
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        register.setForeground(Color.BLUE);
        register.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new RegisteredAccount(userCS,userInfo);
			}
		});
        
        //找回密码
        findPassword = new JLabel("找回密码");
        findPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        findPassword.setForeground(Color.BLUE);
        findPassword.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new RetrievePassword(userCS);
			}
		});
        
        //选择
        remPassword = new JCheckBox("记住密码");
        autoLogin = new JCheckBox("自动登录");
        
        
        //账号 & 注册账号 模块
        accountPanel.add(accountField);
        accountPanel.add(register);
        //密码 & 找回密码 模块
        passwordPanel.add(passwordField);
        passwordPanel.add(findPassword);
        //将两个输入框和注册账号以及找回密码整合
        accPassHintPanel.add(accountPanel);
        accPassHintPanel.add(passwordPanel);
        
        
        //存放：头像、账号、密码、自动登录、记住密码、找回密码、注册账号
        JPanel dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        //存放：记住密码、自动登录
        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 1));
        //存放：登录按钮
        JPanel jbtnPanel = new JPanel();
        
        //登录按钮
        login = new JButton("登  录");
        login.setBackground(new Color(30, 144, 255));
        login.setPreferredSize(new Dimension(170, 30));
        jbtnPanel.setLayout(new FlowLayout());
        jbtnPanel.add(login);
        login.setActionCommand("Login");
        login.addActionListener(this);
        
        accountField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
                try {
                	ID = e.getDocument().getText(0,e.getDocument().getLength());
                    setflag(ID);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				
                try {
                	ID = e.getDocument().getText(0,e.getDocument().getLength());
                    setflag(ID);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				 try {
					 ID = e.getDocument().getText(0,e.getDocument().getLength());
	                    setflag(ID);
	                } catch (BadLocationException ex) {
	                    ex.printStackTrace();
	                }
			}
			private void setflag(String ID)
			{
				temp = clientOperationXML.ReadNode(ID, "portraitNum");
				if (temp == null)
		        {
		        		temp = "Programmer";
		        }
		        else 
		        {
					portraItImage = new ImageIcon("pictures/Head/" + temp + ".jpg");
					portraItImage.setImage(portraItImage.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
					portraItImageLable.setIcon(portraItImage);
		        }
		        
				
			}
            
		});
        
        
        //头像
        dataPanel.add(portraItImageLable);
        //整合：账号、密码、自动登录、记住密码、找回密码、注册账号
        dataPanel.add(accPassHintPanel);
        
        //记住密码 & 自动登录 模块
        checkPanel.add(remPassword);
        checkPanel.add(autoLogin);
        //整合:记住密码、自动登录
        accPassHintPanel.add(checkPanel);
        
        
        //整合所有成为最终模块
        mainLayout.add(imagePanel);
        mainLayout.add(dataPanel);
        mainLayout.add(jbtnPanel);
        
        //将最终模块添加到窗体上
        add(mainLayout);
        
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("Login"))
		{
			if (clientOperationXML.ReadNode(ID, "ID") != null)
			{
				userName = clientOperationXML.ReadNode(ID, "name");
				userInfo.setUserPortraitNum(clientOperationXML.ReadNode(ID, "portraitNum"));
				userInfo.setUserAutograph(clientOperationXML.ReadNode(ID, "autograph"));
				System.out.println("已开始向服务器发送登录数据...");
				try {
					 // 标志 IP 端口号 姓名 账号 密码
					userCS.send("%LOGIN%:" + IP + ":" + userPort + ":" + userName + ":" 
							+ ID + ":" + String.valueOf(passwordField.getPassword()).trim() 
							+ ":" + userInfo.getUserPortraitNum() + ":" + userInfo.getUserAautograph());
					
				} catch (Exception e2) {
					
				}
	            new RecThread().start();;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "账户不存在！");
			}
		}
	}
} 
