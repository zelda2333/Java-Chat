package client;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import stream.ServerClientConnectionStream;
import tools.ClientOperationXML;
import tools.UserInformation;

public class RegisteredAccount extends JFrame
{
	/**
	 * 
	 */
	private ServerClientConnectionStream userCS;
	private UserInformation userInfo;
	private static final long serialVersionUID = -6615972193244018061L;
	public RegisteredAccount(ServerClientConnectionStream userCS, UserInformation userInfo)
	{
		super();
		this.userCS = userCS;
		this.userInfo = userInfo;
		createFrame();
	}
	private ImageIcon Background;
	private JPanel imagePanel;
	
	private JTextField name;
	private JPasswordField password;
	private JPasswordField surepassword;
	
	private JComboBox<String> portrait;
	private JTextArea autograph;
	
	private ImageIcon headpicture;
	private JLabel backportraitJl = new JLabel(headpicture);;
	private JLabel portraitJl;
	
	private JLabel accountJl;
	private JLabel passwordJl;
	private JLabel surepasswordJl;
	private JLabel autographJl;
	
	private ClientOperationXML clientOperationXML = new ClientOperationXML();
	private String num;
	@SuppressWarnings("deprecation")
	private void createFrame() 
	{
		setSize(350,730);
		setResizable(false);
		
		Background = new ImageIcon("pictures/UpPictures/19.jpg");// 背景图片  
		Background.setImage(Background.getImage().getScaledInstance(350, 730, Image.SCALE_DEFAULT));
		JLabel headLable = new JLabel(Background);// 把背景图片显示在一个标签里面  
		// 把标签的大小位置设置为图片刚好填充整个面板  
		headLable.setBounds(0, 0, Background.getIconWidth(),  
				Background.getIconHeight());  
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
		imagePanel = (JPanel) this.getContentPane();  
		imagePanel.setOpaque(false);  
		// 内容窗格默认的布局管理器为BorderLayout  
		imagePanel.setLayout(new FlowLayout(0,0,0));  
		this.getLayeredPane().setLayout(null);  
		// 把背景图片添加到分层窗格的最底层作为背景  
		this.getLayeredPane().add(headLable, new Integer(Integer.MIN_VALUE));  
		// 内容窗格默认的布局管理器为BorderLayout  
		imagePanel.setLayout(new FlowLayout(0,0,0));  
		this.getLayeredPane().setLayout(null);
		imagePanel.setLayout(null);
		
		String[] head = {"00","01","02","03","04","05","06","07","08","09","10",
						 "11","12","13","14","15","16","17","18","19","20",
						 "21","22","23","24","25","26","27","28","29","30",
						 "31","32","33","34","35","36","37","38","39","40"};
		
		name = new JTextField(20);
		
		password = new JPasswordField(20);
		surepassword = new JPasswordField(20);
		portrait = new JComboBox<String>(head);
		autograph = new JTextArea();
		
		accountJl = new JLabel("昵      称：");
		accountJl.setFont(new Font("黑体", 1, 15));
		accountJl.setBounds(40, 40, 150, 30);
		imagePanel.add(accountJl);
		name.setBounds(110, 40, 200, 30);
		name.setFont(new Font("黑体", 1, 15));
		imagePanel.add(name);
		
		passwordJl = new JLabel("密      码：");
		passwordJl.setBounds(40, 80, 200, 30);
		passwordJl.setFont(new Font("黑体", 1, 15));
		imagePanel.add(passwordJl);
		password.setBounds(110, 80, 200, 30);
		password.setFont(new Font("黑体", 1, 15));
		imagePanel.add(password);
		
		surepasswordJl = new JLabel("确认密码：");
		surepasswordJl.setBounds(40, 120, 200, 30);
		surepasswordJl.setFont(new Font("黑体", 1, 15));
		imagePanel.add(surepasswordJl);
		surepassword.setBounds(110, 120, 200, 30);
		surepassword.setFont(new Font("黑体", 1, 15));
		imagePanel.add(surepassword);
		
		JLabel questionjl = new JLabel("密保问题：");
		questionjl.setBounds(40, 160, 200, 30);
		questionjl.setFont(new Font("黑体", 1, 15));
		imagePanel.add(questionjl);
		JTextField question = new JTextField();
		question.setBounds(110, 160, 200, 30);
		question.setFont(new Font("黑体", 1, 15));
		imagePanel.add(question);
		
		JLabel answerjl = new JLabel("密保答案：");
		answerjl.setBounds(40, 200, 200, 30);
		answerjl.setFont(new Font("黑体", 1, 15));
		imagePanel.add(answerjl);
		JTextField answer = new JTextField();
		answer.setBounds(110, 200, 200, 30);
		answer.setFont(new Font("黑体", 1, 15));
		imagePanel.add(answer);
		
		autographJl = new JLabel("个性签名：");
		autographJl.setBounds(40, 240, 200, 30);
		autographJl.setFont(new Font("黑体", 1, 15));
		imagePanel.add(autographJl);
		autograph.setBounds(40, 270, 270, 100);
		autograph.setFont(new Font("黑体", 1, 15));
		imagePanel.add(autograph);
		
		portraitJl = new JLabel("头      像：");
		portraitJl.setBounds(40, 380, 200, 30);
		portraitJl.setFont(new Font("黑体", 1, 15));
		imagePanel.add(portraitJl);
		portrait.setBounds(150, 347, 100, 100);
		imagePanel.add(portrait);
		
		portrait.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent event) 
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					num = (String) portrait.getSelectedItem();
					setflag(num);
				}
			}
			
			
		});
		
		imagePanel.add(backportraitJl);
		
		JButton Submission = new JButton("提  交");
		Submission.setBounds(50,650,100,30);
		imagePanel.add(Submission);
		JButton cancel = new JButton("取  消");
		cancel.setBounds(200,650,100,30);
		imagePanel.add(cancel);
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		Submission.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//判定信息是否完全正确
				boolean flag = true;
				
				if (String.valueOf(password.getPassword()).trim().equals
						(String.valueOf(surepassword.getPassword()).trim())) 
				{
					//生成一个9位的账号
					int x = (int)((Math.random()*9+1)*100000000);
					while ((x<400000000)||(x>500000000))
					{
						x = (int)((Math.random()*9+1)*100000000);
					}
					String ID = Integer.toString(x);
					//账号-昵称-密码-密保问题-密保答案-头像-个性签名
					String[] information = {ID , "-", name.getText(), "-", password.getText(), "-", question.getText(),
							 "-",answer.getText(), "-", num, "-", autograph.getText()};
					for (int j = 1; j < information.length; j++)
					{
						if (information[j] == null)
						{
							flag = false;
							JOptionPane.showMessageDialog(null, "您有注册信息未填写，请重新输入！");
							return;
						}
					}

					if (flag)
					{

						JOptionPane.showMessageDialog(null, "您的账号是："+ID+"\r\n请您牢记！");
						//账号-昵称-密码-密保问题-密保答案-头像-个性签名
						clientOperationXML.AddXmlNode(ID, name.getText(), question.getText(), 
								answer.getText(), num, autograph.getText());
						 // 标志 IP 端口号 姓名 账号 密码 头像 密保 答案 签名
						userCS.send("%REGISTERED%:" + userInfo.getIP() + ":" + userInfo.getPort() + ":" + name.getText() + ":" 
								+ ID + ":" + String.valueOf(password.getPassword()).trim() 
								+ ":" + num + ":" + question.getText() + ":" + answer.getText() + ":" 
								+ autograph.getText());
						dispose();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "您两次输入的密码不一致，请重新输入！");
					return;
				}
				
			}
		});
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private void setflag(String text)
	{
		
		headpicture = new ImageIcon("pictures/Head/"+text+".jpg");
		headpicture.setImage(headpicture.getImage().getScaledInstance(190, 190, Image.SCALE_DEFAULT));
		backportraitJl.setIcon(headpicture);
		backportraitJl.setBounds(80, 435, headpicture.getIconWidth(),  
				headpicture.getIconHeight()); 
				
	}
	
}

