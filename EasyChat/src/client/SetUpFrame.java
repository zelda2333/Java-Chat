package client;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ChangeFrame.*;
import stream.ServerClientConnectionStream;
import tools.UserInformation;

public class SetUpFrame extends JFrame
{
	/**
	 * @author Albert Niu in Main Studio
	 */
	private static final long serialVersionUID = 8263064292199574884L;
	private FriendsListFrame parentFrm;
	private ServerClientConnectionStream userCS;
	private UserInformation userInfo;
	public SetUpFrame(FriendsListFrame parentFrm, ServerClientConnectionStream userCS, UserInformation userInfo)
	{  
        this.parentFrm = parentFrm;
        this.userCS = userCS;
        this.userInfo = userInfo;
        createFrame();
    }
	
	private ImageIcon Background;
	private JPanel imagePanel;

	private void createFrame()
	{
		setSize(180,290);
		setResizable(false);
		
		Background = new ImageIcon("pictures/UpPictures/19.jpg");// 背景图片  
		Background.setImage(Background.getImage().getScaledInstance(180, 280, Image.SCALE_DEFAULT));
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
		//修改密码
		JButton changePassWord = new JButton("修改密码");
		changePassWord.setBounds(30,20,120,30);
		changePassWord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] string = {"old_password",userInfo.getAccount()};
				for (int i = 0; i < 2; i++)
				{
					userCS.send(string[i]);
				}
				
				String oldPassword = userInfo.getPassword();
				if (oldPassword != null)
					new ChangePasswoed(userInfo.getAccount(),userCS,oldPassword);
			
			} 
		});
		imagePanel.add(changePassWord);
		//修改背景图片
		JButton changeBackground = new JButton("修改背景图片");
		changeBackground.setBounds(30,60,120,30);
		changeBackground.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ChangeBackgroung(SetUpFrame.this,parentFrm,userCS,userInfo);
			}
		});
		imagePanel.add(changeBackground);
		//修改签名
		JButton changeAutograph = new JButton("修改签名");
		changeAutograph.setBounds(30,100,120,30);
		changeAutograph.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ChangeAutograph();
			}
		});
		imagePanel.add(changeAutograph);
		
		//修改头像
		JButton changeHead = new JButton("修改头像");
		changeHead.setBounds(30,140,120,30);
		changeHead.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ChangeHead();
			}
		});
		imagePanel.add(changeHead);
		//修改昵称
		JButton changeName= new JButton("修改昵称");
		changeName.setBounds(30,180,120,30);
		changeName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ChangeName();
			}
		});
		imagePanel.add(changeName);
		
		//退出
		JButton out = new JButton("退出登录");
		out.setBounds(30,220,120,30);
		out.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (userCS != null)
				{
					dispose();
					parentFrm.dispose();
					 // 标志 IP 端口号 姓名 账号 密码
					userCS.send("%EXIT%:" + userInfo.getIP() + ":" + userInfo.getPort() + ":" 
					 + userInfo.getName() + ":" + userInfo.getAccount() + ":" + userInfo.getPassword() 
							+ ":" + userInfo.getUserPortraitNum() + ":" + userInfo.getUserAautograph());
					new LoginFrame(userCS.getSocket());
				}
				else
				{
					System.out.println("Error!");
				}
			}
		});
		imagePanel.add(out);

		setLocationRelativeTo(null);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
