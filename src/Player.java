import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;

abstract class Player
{

	protected PrintWriter os = null;
	protected String ip = null;
	Socket s = null;
	MainPanel mainPanel = null;

	public void sendMessage(String s)
	{
		if(os!=null)
		os.println(s);
	}

	protected void deal(String s)
	{
		if (s.contains("turn"))
		{
			String a[] = s.split("=");
			MainPanel.turn = Integer.parseInt(a[1]);
		}else if(s.contains("buff"))
		{
			String a[] = s.split("=");
			MainPanel.buff[Integer.parseInt(a[1])][Integer.parseInt(a[2])]=Integer.parseInt(a[3]);
			new Thread(new Thread(new PlayWav("sounds/luoz.wav"))).start();
			for (int i = 0; i < 15; i++)
				for (int j = 0; j < 15; j++)
					MainPanel.tempBuf[i][j]=0;
			MainPanel.tempBuf[Integer.parseInt(a[1])][Integer.parseInt(a[2])]=3;
			Main.mainPanel.repaint();
		}else if(s.equals("lose"))
		{
			MainPanel.state=MainPanel.LOSE;
			new Thread(new Thread(new PlayWav("sounds/lose.wav"))).start();
			Main.ready=0;
			Main.otherReady=0;
			Main.touxReadyLB.setIcon(null);
			Main.otherTouxReadyLB.setIcon(null);
			Main.readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始.png")).getImage()));
		}else if (s.contains("ready"))
		{
			String a[] = s.split("=");
			Main.otherReady = Integer.parseInt(a[1]);
			Main.otherTouxReadyLB.setIcon(new ImageIcon(this.getClass().getResource("images/准备.png")));
		}else if(s.contains("score"))
		{
			String a[] = s.split("=");
			Main.otherScore = Integer.parseInt(a[1]);
			Main.otherScoreLB.setText("分数:"+Main.otherScore);
			Main.otherChenghaoLB.setText(Main.chenghaoStr(Main.otherScore, Main.otherChenghaoLB));
			Main.otherChenghaoLB.setVisible(true);
			Main.otherScoreLB.setVisible(true);
		}else if(s.contains("chat"))
		{
			int index = s.indexOf("=");
			String str = s.substring(index+1);
			Main.chatTA.append(ip+":\r\n"+str+"\r\n");
			new Thread(new Thread(new PlayWav("sounds/chat.wav"))).start();
		}
	}

}