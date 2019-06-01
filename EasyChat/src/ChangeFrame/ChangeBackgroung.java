package ChangeFrame;

import java.awt.FlowLayout;
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
import javax.swing.JPanel;

import client.FriendsListFrame;
import client.SetUpFrame;
import stream.ServerClientConnectionStream;
import tools.UserInformation;

public class ChangeBackgroung extends JFrame
{
	/**
	 * 
	 */
	private ImageIcon UpBackground;
	private ImageIcon DownBackground;
	private String UpNum;
	private String DownNum;
	private JPanel imagePanel;
	private JLabel UpLable;
	private JLabel DownLable;
	private JComboBox<String> UpBackgroundpictures;
	private JComboBox<String> DownBackgroundpictures;
	private static final long serialVersionUID = 5476999078143538241L;
	
	private ServerClientConnectionStream userCS;
	private UserInformation userInfo;
	private FriendsListFrame fatherFrame;
	private SetUpFrame setUpFrame;
	public ChangeBackgroung(SetUpFrame setUpFrame, FriendsListFrame parentFrm,
			ServerClientConnectionStream userCS, UserInformation userInfo)
	{
		super();
		this.setUpFrame = setUpFrame;
		this.userCS = userCS;
		this.userInfo = userInfo;
		this.fatherFrame = parentFrm;
		createFrame();
	}
	private void createFrame()
	{
		setSize(360,800);
		
		String[] Up = {"01","02","03","04","05","06","07","08","09","10",
				 "11","12","13","14","15","16","17","18","19","20", "21"};
		String[] Down = {"01","02","03","04","05","06","07","08","09","10",
				 "11","12","13","14","15","16","17","18","19","20", "21"};

		UpBackgroundpictures = new JComboBox<String>(Up);
		DownBackgroundpictures = new JComboBox<String>(Down);
		UpLable = new JLabel(UpBackground);
		DownLable = new JLabel(DownBackground);
		
		UpBackground = new ImageIcon("pictures/UpPictures/"+"08"+".jpg");
		UpBackground.setImage(UpBackground.getImage().getScaledInstance(360, 150, Image.SCALE_DEFAULT));
		UpLable.setIcon(UpBackground);
		UpLable.setBounds(0, 0, UpBackground.getIconWidth(),  
				UpBackground.getIconHeight()); 	
		DownBackground = new ImageIcon("pictures/DownPictures/"+"08"+".jpg");
		DownBackground.setImage(DownBackground.getImage().getScaledInstance(360, 650, Image.SCALE_DEFAULT));
		DownLable.setIcon(DownBackground);
		DownLable.setBounds(0, 150, DownBackground.getIconWidth(),  
				DownBackground.getIconHeight()); 
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
		imagePanel = (JPanel) this.getContentPane();  
		imagePanel.setOpaque(false);  
		// 内容窗格默认的布局管理器为BorderLayout  
		imagePanel.setLayout(new FlowLayout(0,0,0));  
		this.getLayeredPane().setLayout(null); 
		// 把背景图片添加到分层窗格的最底层作为背景  
		this.getLayeredPane().add(UpLable, new Integer(Integer.MIN_VALUE)); 
		this.getLayeredPane().add(DownLable, new Integer(Integer.MIN_VALUE));
		
		imagePanel.setLayout(null);
		UpBackgroundpictures.setBounds(130, 20, 100, 30);
		DownBackgroundpictures.setBounds(130, 200, 100, 30);
		imagePanel.add(UpBackgroundpictures);
		imagePanel.add(DownBackgroundpictures);
		
		UpBackgroundpictures.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent event) 
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					UpNum = (String) UpBackgroundpictures.getSelectedItem();
					UpSetBack(UpNum);
				}
			}
		});
		DownBackgroundpictures.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent event) 
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					DownNum = (String) DownBackgroundpictures.getSelectedItem();
					DownSetBack(DownNum);
				}
			}
		});
		
		JButton Submission = new JButton("提  交");
		Submission.setBounds(50,700,100,30);
		imagePanel.add(Submission);
		JButton cancel = new JButton("取  消");
		cancel.setBounds(210,700,100,30);
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
				setUpFrame.dispose();
				fatherFrame.dispose();
				FriendsListFrame frame = new FriendsListFrame(userCS, userInfo,UpNum, DownNum);
				frame.showMe();
				dispose();
			}
		});
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private void UpSetBack(String text)
	{
		UpBackground = new ImageIcon("pictures/UpPictures/"+text+".jpg");
		UpBackground.setImage(UpBackground.getImage().getScaledInstance(360, 150, Image.SCALE_DEFAULT));
		UpLable.setIcon(UpBackground);
		UpLable.setBounds(0, 0, UpBackground.getIconWidth(),  
				UpBackground.getIconHeight()); 		
	}
	private void DownSetBack(String text)
	{
		DownBackground = new ImageIcon("pictures/DownPictures/"+text+".jpg");
		DownBackground.setImage(DownBackground.getImage().getScaledInstance(360, 650, Image.SCALE_DEFAULT));
		DownLable.setIcon(DownBackground);
		DownLable.setBounds(0, 150, DownBackground.getIconWidth(),  
				DownBackground.getIconHeight()); 
	}
}
