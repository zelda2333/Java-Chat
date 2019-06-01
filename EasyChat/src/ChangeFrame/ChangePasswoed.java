package ChangeFrame;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import stream.ServerClientConnectionStream;
import tools.ClientOperationXML;

public class ChangePasswoed extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7524090155493907796L;
	private String ID;
	private ClientOperationXML clientOperationXML = new ClientOperationXML();
	private ServerClientConnectionStream userCS;
	private String oldpassword;
	public ChangePasswoed(String ID, ServerClientConnectionStream userCS, String oldpassword)
	{
		super();
		this.ID = ID;
		this.userCS = userCS;
		this.oldpassword = oldpassword;
		createFrame(ID);
	}
	public ChangePasswoed(String ID)
	{
		super();
		createFrame(ID);
	}
	private ImageIcon Background;
	private JPanel imagePanel;
	
	private void createFrame(String ID)
	{
		setSize(400,300);
		
		Background = new ImageIcon("pictures/UpPictures/19.jpg");// 背景图片  
		Background.setImage(Background.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT));
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
		
		JLabel questionjl = new JLabel("密保问题：");
		questionjl.setBounds(40, 20, 100, 30);
		questionjl.setFont(new Font("黑体", 1, 16));
		imagePanel.add(questionjl);
		
		String qString = clientOperationXML.ReadNode(ID, "question");

		JLabel question = new JLabel(qString);
		question.setBounds(40, 50, 320, 30);
		question.setFont(new Font("黑体", 1, 20));
		imagePanel.add(question);
		
		JTextField answer = new JTextField();
		answer.setBounds(40, 90, 320, 30);
		answer.setFont(new Font("黑体", 1, 15));
		imagePanel.add(answer);
		

		JLabel oldquestion = new JLabel("原密码：");
		oldquestion.setBounds(40, 130, 100, 30);
		oldquestion.setFont(new Font("黑体", 1, 16));
		imagePanel.add(oldquestion);
		JTextField txtoldpassword = new JTextField();
		txtoldpassword.setBounds(40, 170, 320, 30);
		txtoldpassword.setFont(new Font("黑体", 1, 15));
		imagePanel.add(txtoldpassword);
		
		
		JButton Submission = new JButton("提  交");
		Submission.setBounds(70,220,100,30);
		imagePanel.add(Submission);
		JButton cancel = new JButton("取  消");
		cancel.setBounds(230,220,100,30);
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
				String Sanswer = answer.getText();
				String Soldpass = txtoldpassword.getText();
				if (oldpassword.equals(Soldpass)&&
						clientOperationXML.ReadNode(ID, "answer").equals(Sanswer))
				{
					dispose();
					new createChangeFrame(ID,userCS);
				}
				
			}
		});
		
		
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ChangePasswoed("473034128");
	}
	class createChangeFrame extends JFrame
	{
		
		private String ID;
		private ClientOperationXML clientOperationXML = new ClientOperationXML();
		private ServerClientConnectionStream userCS;
		private ImageIcon Background;
		private JPanel imagePanel;
		private JLabel passwordJl;
		private JLabel surepasswordJl;
		private JPasswordField password;
		private JPasswordField surepassword;

		public createChangeFrame(String ID, ServerClientConnectionStream userCS)
		{
			
			this.ID = ID;
			this.userCS = userCS;
			createFrame(ID,userCS);
		}

		private void createFrame(String ID, ServerClientConnectionStream userCS) 
		{
			setSize(400,250);
			
			Background = new ImageIcon("pictures/UpPictures/19.jpg");// 背景图片  
			Background.setImage(Background.getImage().getScaledInstance(400, 250, Image.SCALE_DEFAULT));
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
			
			password = new JPasswordField(20);
			surepassword = new JPasswordField(20);
			
			passwordJl = new JLabel("新 密 码 ：");
			passwordJl.setBounds(40, 40, 230, 30);
			passwordJl.setFont(new Font("黑体", 1, 15));
			imagePanel.add(passwordJl);
			password.setBounds(110, 40, 230, 30);
			password.setFont(new Font("黑体", 1, 15));
			imagePanel.add(password);
			
			surepasswordJl = new JLabel("确认密码：");
			surepasswordJl.setBounds(40, 100, 200, 30);
			surepasswordJl.setFont(new Font("黑体", 1, 15));
			imagePanel.add(surepasswordJl);
			surepassword.setBounds(110, 100, 230, 30);
			surepassword.setFont(new Font("黑体", 1, 15));
			imagePanel.add(surepassword);
			
			JButton Submission = new JButton("提  交");
			Submission.setBounds(70,180,100,30);
			imagePanel.add(Submission);
			JButton cancel = new JButton("取  消");
			cancel.setBounds(230,180,100,30);
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
					if (surepassword.getText().equals(password.getText()))
					{
						String message;
						message = password.getText();
						clientOperationXML.ChangeXmlDocument(ID, "password", message);
						userCS.send("%Change_password%:" + message + ":" + ID);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "您两次输入的密码不一致，请重新输入！");
					}
				}
			});
			
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
		}
		
	}
}
