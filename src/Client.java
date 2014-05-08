import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Client extends Player implements Runnable
{

	@Override
	public void run()
	{
		try
		{
			ip = Main.ipTF.getText();
			if(ip.equals(""))
			{
				JOptionPane.showMessageDialog(null,"����дip��ַ");
				return;
			}
			s = new Socket(ip, 10005);
			Main.chatTA.append("�����ӵ�"+ip+"...\r\n");
			os = new PrintWriter(s.getOutputStream(),true);
			Main.isConnected=true;
			Main.otherTouxLB.setIcon(new ImageIcon(this.getClass().getResource("images/02.gif")));
			sendMessage("score="+Main.score);
			InputStream is = s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str;
			while ((str = br.readLine()) != "over")
			{
				deal(str);
			}
			os.println("over");
		}catch(ConnectException e)
		{
			JOptionPane.showMessageDialog(null, "��������δ����");
			e.printStackTrace();
		}
		catch (UnknownHostException e)
		{
			JOptionPane.showMessageDialog(null, "�޷����ӵ�������");
			e.printStackTrace();
		}catch (SocketException e) {
			JOptionPane.showMessageDialog(null, "�������ѶϿ�");
			
			Main.chatTA.append("��"+ip+"�Ͽ�����...\r\n");
			Main.isConnected=false;
			Main.ready=0;
			Main.readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/��ʼ.png")).getImage()));
			Main.touxReadyLB.setIcon(null);
			Main.otherTouxReadyLB.setIcon(null);
			Main.otherTouxLB.setIcon(null);
			Main.otherScoreLB.setVisible(false);
			Main.otherChenghaoLB.setVisible(false);
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
				if (s != null)
					s.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
