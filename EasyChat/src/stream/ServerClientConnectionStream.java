package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientConnectionStream {
	
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter printWriter;
	
	public ServerClientConnectionStream(Socket socket)
	{
		super();
		this.socket = socket;
		getStream();
	}
	public Socket getSocket()
	{
		return this.socket;
	}
	private void getStream()
	{
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			printWriter = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		if (socket != null)
		{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void send(String message)
	{
		printWriter.println(message);
		printWriter.flush();
	}
	
	public String read()
	{
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
