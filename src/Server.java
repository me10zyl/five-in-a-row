import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Server extends Player implements Runnable
{
	ServerSocket ss = null;
	@Override
	public void run()
	{
		try
		{
			ss = new ServerSocket(10005);
			s = ss.accept();
			ip = s.getInetAddress().getHostAddress();
			Main.otherTouxLB.setIcon(new ImageIcon(this.getClass().getResource("images/02.gif")));
			Main.chatTA.append("玩家" +ip + "已连接...\r\n");
			Main.isConnected = true;
			InputStream in = s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String str;
			os = new PrintWriter(s.getOutputStream(),true);
			sendMessage("score="+Main.score);
			while ((str = br.readLine()) != "over")
			{
				deal(str);
			}
			os.println("over");
		}catch (BindException e)
		{
			JOptionPane.showMessageDialog(null, "已开启服务器!请重新开启");
			e.printStackTrace();
		}
		catch (UnknownHostException e1)
		{
			JOptionPane.showMessageDialog(null, "未知主机异常!");
			e1.printStackTrace();
		}catch (SocketException e) {
			JOptionPane.showMessageDialog(null, "客户端已断开");
			Main.chatTA.append("与"+ip+"断开连接...\r\n");
			Main.isConnected=false;
			Main.ready=0;
			Main.touxReadyLB.setIcon(null);
			Main.otherTouxReadyLB.setIcon(null);
			Main.otherScoreLB.setVisible(false);
			Main.otherChenghaoLB.setVisible(false);
			Main.otherTouxLB.setIcon(null); 
			Main.readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始.png")).getImage()));
			MainPanel.clearBoard();
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (ss != null)
					ss.close();
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try
			{
				if (s != null)
					s.close();
			}  catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
