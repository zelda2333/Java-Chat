package tools;

public class UserInformation {
	
	private String name;
    private String account;
    private String password;
    private String IP;
    private int port;
    private int recentPort;
    private String recenIP;
    private String userPortraitNum;
    private String question;
    private String answer;
    private String autograph;

    public UserInformation()
    {
        super();
    }
    public UserInformation(String ID) {
		super();
		this.account = ID;
	}
    //客户端专用：区别。。。服务器能获取密码，客户端不能获取密码
    public UserInformation(String name, String account, String password, String IP, int port, String userPortraitNum)
    {
        super();
        this.name = name;
        this.account = account;
        this.password = password;
        this.IP = IP;
        this.port = port;
        this.userPortraitNum = userPortraitNum;
    }
    //服务器专用
    public UserInformation(String name, String account, String IP, int port, String userPortraitNum, String recentIP,
            int recentPort)
    {
        super();
        this.name = name;
        this.account = account;
        this.IP = IP;
        this.port = port;
        this.recenIP = recentIP;
        this.recentPort = recentPort;
        this.userPortraitNum = userPortraitNum;
       
    }

    public int getRecentPort()
    {
        return recentPort;
    }

    public void setRecentPort(int recentPort)
    {
        this.recentPort = recentPort;
    }

    public String getRecenIP()
    {
        return recenIP;
    }

    public void setRecenIP(String recenIP)
    {
        this.recenIP = recenIP;
    }

    public UserInformation(String name, int port)
    {
        super();
        this.name = name;
        this.port = port;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String acocount)
    {
        this.account = acocount;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getIP()
    {
        return IP;
    }

    public void setIP(String iP)
    {
        IP = iP;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getUserPortraitNum()
    {
        return userPortraitNum;
    }

    public void setUserPortraitNum(String userPortraitNum)
    {
        this.userPortraitNum = userPortraitNum;
    }
    public void setUserQuestion(String question)
    {
    		this.question = question;
    }
    public String getUserQuestion()
    {
    		return question;
    }
    public void setUserAutograph(String autograph)
    {
    		this.autograph = autograph;
    }
    public String getUserAautograph()
    {
    		return autograph;
    }
    public void setUserAnswer(String answer)
    {
    		this.answer = answer;
    }
    public String getUserAnswer()
    {
    		return answer;
    }
    public UserInformation setInfo(String message)
    {
        // 标志 IP 端口号 姓名 账号 密码 头像
        this.IP = message.split(":")[1];
        this.port = Integer.parseInt(message.split(":")[2]);
        this.name = message.split(":")[3];
        this.account = message.split(":")[4];
        this.password = message.split(":")[5];
        this.userPortraitNum = message.split(":")[6];
        this.autograph = message.split(":")[7];
        return this;
    }
    
    public UserInformation setAllInfo(String message)
    {
        // 标志 IP 端口号 姓名 账号 密码 头像
        this.IP = message.split(":")[1];
        this.port = Integer.parseInt(message.split(":")[2]);
        this.name = message.split(":")[3];
        this.account = message.split(":")[4];
        this.password = message.split(":")[5];
        this.userPortraitNum = message.split(":")[6];
        this.question = message.split(":")[7];
        this.answer = message.split(":")[8];
        this.autograph = message.split(":")[9];
        return this;
    }
    public UserInformation setFriendInfo(String message)
    {
        // 标志 IP 端口号 姓名 账号 密码 头像
        this.account = message.split(":")[1];
        this.name = message.split(":")[2];
        this.userPortraitNum = message.split(":")[3];
        this.autograph = message.split(":")[4];
        this.port = Integer.parseInt(message.split(":")[5]);
        
        return this;
    }
}
