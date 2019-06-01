package ChangeFrame;

import javax.swing.JFrame;

public class ChangeHead extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4021882520382678599L;
	public ChangeHead()
	{
		super();
		createFrame();
	}
	private void createFrame()
	{
		setSize(500,500);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
