package stream;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ClientToClientConnectionStream 
{
	 /**
     * @author CSJ
     */
    private DatagramPacket userReceivePacket;
    private DatagramSocket userDataSocket;
    private byte buffer[];
    private InetAddress hostAddress;
    private int port;

    public ClientToClientConnectionStream(DatagramSocket userDataSocket)
    {
        super();
        //UDP服务，提供UDP断点 
        this.userDataSocket = userDataSocket;
     	//定义数据包，用来存储接收到的数据
        buffer = new byte[1024];
        userReceivePacket = new DatagramPacket(buffer, buffer.length);
    }

    public ClientToClientConnectionStream(DatagramSocket userDataSocket, InetAddress hostAddress, int port)
    {
        super();
        this.hostAddress = hostAddress;
        this.port = port;
        this.userDataSocket = userDataSocket;
        buffer = new byte[1024];
        userReceivePacket = new DatagramPacket(buffer, buffer.length);
    }
    //通过UDP通讯将消息发送出去
    public void send(String message)
    {
        try
        {
            userDataSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, hostAddress, port));
        } catch (IOException e)
        {
            System.out.println("UDP端口被占用！");
            e.printStackTrace();
        }
    }
    //通过UDP通讯接收消息
    public String read()
    {
        try
        {
            if (!userDataSocket.isClosed())
            {
                userDataSocket.receive(userReceivePacket);
                String string = new String(userReceivePacket.getData(), 0, userReceivePacket.getLength(), "utf-8");
                return string + "\n";
            }
        } catch (UnsupportedEncodingException e)
        {
            System.out.println("不支持的编码类型！");
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println(userDataSocket == null);
            e.printStackTrace();
        }
        return null;
    }

    public void close()
    {
        if (userDataSocket != null)
        {
            userDataSocket.close();
        }
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getHostAddress()
    {
        return hostAddress.getHostAddress();
    }

    public void setHostAddress(InetAddress hostAddress)
    {
        this.hostAddress = hostAddress;
    }

    public int getPort()
    {
        return port;
    }

    public DatagramPacket getUserReceivePacket()
    {
        return userReceivePacket;
    }

    public InetAddress getInetAddress()
    {
        return userReceivePacket.getAddress();
    }

    public DatagramSocket getDatagramSocket()
    {
        return userDataSocket;
    }
}
