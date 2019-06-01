package client;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindFriendsFrame extends JFrame
{

	/**
	 * @author Albert Niu in Main Studio
	 */
	private static final long serialVersionUID = 1L;

	public FindFriendsFrame()
	{
		super();
		createFrame();
	}
	
	JPanel background;
	JTextField accountField;
	
	private ImageIcon Background;
	private JPanel imagePanel;
	
	@SuppressWarnings("deprecation")
	private void createFrame()
	{
		setSize(365,180);
		
		Background = new ImageIcon("pictures/UpPictures/Pink_2.jpg");// 背景图片  
		Background.setImage(Background.getImage().getScaledInstance(365, 180, Image.SCALE_DEFAULT));
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

		background = (JPanel) this.getContentPane();  
		background.setOpaque(false);   
		background.setLayout(new FlowLayout(0,0,0));  
		this.getLayeredPane().setLayout(null); 
		background.setLayout(null);
		
		JLabel jLabel = new JLabel("请输入你要搜索的账号或昵称：");
		jLabel.setFont(new Font("华文行楷", 1, 14));
		jLabel.setBounds(55, 40, 300, 20);
		background.add(jLabel);
		
		accountField = new JTextField(15);
		accountField.setBounds(50, 85, 200, 35);
		accountField.setFont(new Font("黑体", 1, 15));
		background.add(accountField);
		
		JButton startFind = new JButton("搜 索");
		startFind.setBounds(280, 85, 50, 35);
		background.add(startFind);
		startFind.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "该功能暂未开通！");
				dispose();
			}
		});
		
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
