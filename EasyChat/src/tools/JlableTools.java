package tools;

import java.awt.Color;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ChatRoomClientFrame;
import stream.ServerClientConnectionStream;
public class JlableTools
{
	private ServerOperationXML xml = new ServerOperationXML();
	public void addMessage()
	{
		
	}
	public void addJlable(String ID,String name, int port, JPanel UserList,
			ServerClientConnectionStream userCS,UserInformation userInfo) 
	{
		ImageIcon icon = new ImageIcon("pictures/Head/"+ xml.ReadNode(ID, "portraitNum") +".jpg");
		icon.setImage(icon.getImage().getScaledInstance(
				50,50, Image.SCALE_DEFAULT));
		JLabel jLabels = new JLabel(name + "（"+ID+"）",icon,JLabel.LEFT);

		jLabels.setBorder(BorderFactory.createEtchedBorder());
		jLabels.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				JLabel jLabel = (JLabel)arg0.getSource();
				jLabel.setBackground(Color.white);
				jLabel.setBorder(BorderFactory.createEtchedBorder());
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				JLabel jLabel = (JLabel)arg0.getSource();
				jLabel.setBackground(Color.red);
				jLabel.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2)
				{
					new ChatRoomClientFrame(ID,port,userInfo,name).ShoeMe();
				}
			}
		});
		UserList.add(jLabels);
		
	}
	
}
