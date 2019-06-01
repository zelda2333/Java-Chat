package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LoginLoading extends JFrame
{
	private static final long serialVersionUID = -3614725970956842677L;
	/**
	 * @author Albert Niu in Main Studio
	 * 		2018-5-25  22：25
	 */
	private String ID;
	public LoginLoading(String ID)
	{
		super();
		this.ID = ID;
		createFrame(ID);
	}
	private JButton cancel;
	private ImageIcon portraItImage;
	private JPanel jbtnPanel;
	private PrintWriter printWriter;
	
	private void createFrame(String path) 
	{
		setBounds(0,0,410,350);
		setResizable(false);
        ImageIcon imageIcon = new ImageIcon("pictures/system/Logo.png");
		//设置图片在窗体中显示的大小
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(410, 150, Image.SCALE_DEFAULT));
		JLabel jLabel = new JLabel(imageIcon);
		JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		imagePanel.add(jLabel);
		
		//判定头像
        portraItImage = new ImageIcon("pictures/Head/" + path + ".jpg");
        portraItImage.setImage(portraItImage.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        //加载头像
        JLabel portraItImageLable = new JLabel(portraItImage);
        JPanel portraItPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        portraItPanel.add(portraItImageLable);
        //取消按钮
        jbtnPanel = new JPanel();
        jbtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        cancel = new JButton("取  消");
        cancel.setBackground(new Color(30, 144, 255));
        cancel.setPreferredSize(new Dimension(130, 30));
        jbtnPanel.add(cancel);
        cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendExitMsg();
	        		dispose();
				new LoginFrame();
			}
			
			private void sendExitMsg() {
				String msg = "exit@#@#全部@#@#null@#@#" + ID;
		        System.out.println("退出:" + msg);
		        printWriter.println(msg);
		        printWriter.flush();
			}
		});

        
        JPanel jPanel = new JPanel(new GridLayout(3, 15, 0, 0));
        jPanel.add(portraItPanel);
        jPanel.add(jbtnPanel);
        
        JPanel mainLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        mainLayout.add(imagePanel);
        mainLayout.add(jPanel);
        
        this.add(mainLayout);
        
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
}
