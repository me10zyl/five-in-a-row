import java.awt.Button;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame implements ActionListener
{
	static Client client;
	static Server sever;
	static String web;
	static String newVersion;
	static int score = 0;
	static final double version = 1.2;
	static Button chatBut;
	static TextField chatTF;
	static Label infoLB;
	static String[] chenghao =
	{ "幼儿园", "学前班", "小学生", "初中生", " 高中生", "大学生", "玉皇大帝" };
	static int otherScore = 0;
	public static final int SERVER_TURN = 1;
	public static final int CLIENT_TURN = 2;
	static int myturn = SERVER_TURN;
	static int ready = 0;
	static int otherReady = 0;
	static boolean isConnected = false;
	static MainPanel mainPanel;
	static TextField ipTF;
	Label ipLB;
	static JLabel readyLB;
	Button connectBut, createBut;
	static TextArea chatTA;
	static JLabel touxLB, touxReadyLB, otherTouxLB, otherTouxReadyLB;
	static Label scoreLB, chenghaoLB, otherScoreLB, otherChenghaoLB;

	public static void main(String[] args)
	{
		new Main();
	}

	// @Override
	// public void paint(Graphics g)
	// {
	// super.paint(g);
	// if (MainPanel.state == MainPanel.DEAFAULT&&ready==1&&otherReady==1)
	// {
	// if(MainPanel.turn==myturn)
	// {
	// g.setColor(Color.magenta);
	// g.draw3DRect(0, 0, 152, 221, false);
	// g.draw3DRect(0, 0, 151, 220, false);
	// g.draw3DRect(0, 0, 150, 219, false);
	// }else
	// {
	// g.setColor(Color.YELLOW);
	// g.draw3DRect(0, 0, 152, 221, false);
	// g.draw3DRect(0, 0, 151, 220, false);
	// g.draw3DRect(0, 0, 150, 219, false);
	// }
	// }
	// }
	public Main()
	{
		String[] pathNscore;
		pathNscore = Info.getPreviousInfo();
		if (pathNscore != null)
			score = Integer.parseInt(pathNscore[1]);
		chatTA = new TextArea();
		chatTA.setEditable(false);
		chatTA.setBackground(Color.WHITE);
		chatBut = new Button("发送");
		infoLB = new Label("@version " + version + " @author ZyL");
		infoLB.setFont(new Font("黑体", 0, 11));
		chatTF = new TextField();
		mainPanel = new MainPanel();
		if (pathNscore != null)
			ipTF = new TextField(pathNscore[0]);
		else
			ipTF = new TextField();
		ipLB = new Label("ip地址:");
		scoreLB = new Label("分数:" + score);
		scoreLB.setFont(new Font("黑体", 0, 11));
		otherScoreLB = new Label("分数:" + score);
		otherScoreLB.setFont(new Font("黑体", 0, 11));
		otherScoreLB.setVisible(false);
		chenghaoLB = new Label();
		chenghaoLB.setText(chenghaoStr(score, chenghaoLB));
		otherChenghaoLB = new Label(chenghao[6]);
		otherChenghaoLB.setVisible(false);
		touxLB = new JLabel(new ImageIcon(this.getClass().getResource("images/11.gif")));
		otherTouxLB = new JLabel();
		touxReadyLB = new JLabel();
		otherTouxReadyLB = new JLabel();
		readyLB = new JLabel(new ImageIcon(this.getClass().getResource("images/开始.png")));
		connectBut = new Button("连接");
		createBut = new Button("新建");
		client = new Client();
		sever = new Server();
		this.setLayout(null);
		this.add(mainPanel);
		this.add(ipTF);
		this.add(ipLB);
		this.add(touxLB);
		this.add(otherTouxReadyLB);
		this.add(touxReadyLB);
		this.add(connectBut);
		this.add(chenghaoLB);
		this.add(otherChenghaoLB);
		this.add(createBut);
		this.add(chatTF);
		this.add(chatBut);
		this.add(infoLB);
		this.add(scoreLB);
		this.add(otherScoreLB);
		this.add(chatTA);
		this.add(readyLB);
		this.add(otherTouxLB);
		this.setLayout();
		this.setBounds(400, 300, 927, 630);
		this.setVisible(true);
		this.addListeners();
		this.setTitle("五子棋");
		this.setResizable(false);
		this.setIconImage(new ImageIcon(this.getClass().getResource("images/棋盘.png")).getImage());
		if (DetectIsUpdate.isUpdate())
		{
			int op = JOptionPane.showConfirmDialog(null, "已检测到有更新的版本，是否下载更新?\r\n" + "最新版本:" + newVersion + "\r\n你的版本:" + version, "更新", JOptionPane.YES_NO_OPTION);
			if (op == JOptionPane.YES_OPTION)
				try
				{
					URI uri = new URI(web+"五子棋.exe");
					Desktop.getDesktop().browse(uri);
				} catch (IOException e)
				{
					e.printStackTrace();
				} catch (URISyntaxException e)
				{
					e.printStackTrace();
				}

		}
	}

	public static String chenghaoStr(int score, Label label)
	{
		String s = null;
		if (score >= 9999)
		{
			s = chenghao[6];
			label.setForeground(Color.ORANGE);
		} else if (score >= 8888)
		{
			s = chenghao[5];
			label.setForeground(Color.PINK);
		} else if (score >= 6000)
		{
			s = chenghao[4];
			label.setForeground(Color.GREEN);
		} else if (score >= 4500)
		{
			s = chenghao[3];
			label.setForeground(Color.CYAN);
		} else if (score >= 2500)
		{
			s = chenghao[2];
			label.setForeground(Color.BLUE);
		} else if (score >= 1000)
		{
			s = chenghao[1];
			label.setForeground(Color.RED);
		} else if (score >= 0)
		{
			s = chenghao[0];
			label.setForeground(Color.BLACK);
		}
		return s;
	}

	private void addListeners()
	{
		connectBut.addActionListener(this);
		createBut.addActionListener(this);
		chatBut.addActionListener(this);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
//				System.out.println(score);
				Info.setPreviousInfo(ipTF.getText(), score + "");
				System.exit(0);
			}
		});
		readyLB.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				if (ready == 0)
					readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始亮.png")).getImage()));
			}

			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				if (ready == 0)
					readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始.png")).getImage()));
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				// TODO Auto-generated method stub
				if (isConnected)
				{
					if (ready == 0)
					{
						readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始灰.png")).getImage()));
						ready = 1;
						touxReadyLB.setIcon(new ImageIcon(this.getClass().getResource("images/准备.png")));
						MainPanel.state = MainPanel.DEAFAULT;
						MainPanel.clearBoard();
					}
					if (Main.myturn == Main.SERVER_TURN)
					{
						Main.sever.sendMessage("ready=" + ready);
					} else if (Main.myturn == Main.CLIENT_TURN)
					{
						Main.client.sendMessage("ready=" + ready);
					}
				} else
				{
					JOptionPane.showMessageDialog(null, "尚未连接任何玩家");
				}
			}
		});
		ipTF.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if (!isConnected)
					{
						new Thread(client).start();
						myturn = CLIENT_TURN;
					} else
						JOptionPane.showMessageDialog(null, "已连接,请勿重连");
				}
			}
		});
		chatTF.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if (isConnected)
					{
						if (Main.myturn == Main.SERVER_TURN)
						{
							Main.sever.sendMessage("chat=" + chatTF.getText());
						} else if (Main.myturn == Main.CLIENT_TURN)
						{
							Main.client.sendMessage("chat=" + chatTF.getText());
						}
						chatTA.append("我:\r\n" + chatTF.getText() + "\r\n");
						chatTF.setText("");
					} else
						JOptionPane.showMessageDialog(null, "无任何玩家连接");
				}
			}
		});
	}

	private void setLayout()
	{
		mainPanel.setBounds(MainPanel.originX, MainPanel.originY, 551, 550);
		chatTA.setBounds(MainPanel.originX + 551, MainPanel.originY + 20, 100 + 35 + 50, 550 - 20);
		ipLB.setBounds(MainPanel.originX + 551, MainPanel.originY, 50, 20);
		ipTF.setBounds(MainPanel.originX + 551 + 50, MainPanel.originY, 100, 20);
		connectBut.setBounds(MainPanel.originX + 551 + 50 + 100, MainPanel.originY, 35, 20);
		createBut.setBounds(MainPanel.originX + 551 + 50 + 100 + 35, MainPanel.originY + 20, 35, 20);
		readyLB.setBounds(550 / 2 + 100, 551, 80, 42);
		touxLB.setBounds(0, 0, 150, 197);
		touxReadyLB.setBounds(150 - 95, 197, 95, 50);
		otherTouxLB.setBounds(0, 197 + 50, 150, 197);
		otherTouxReadyLB.setBounds(150 - 95, 197 * 2 + 50, 95, 50);
		scoreLB.setBounds(0, 197, 150 - 95, 25);
		chenghaoLB.setBounds(0, 197 + 25, 150 - 95, 25);
		otherScoreLB.setBounds(0, 197 * 2 + 50, 150 - 95, 25);
		otherChenghaoLB.setBounds(0, 197 * 2 + 50 + 25, 150 - 95, 25);
		chatTF.setBounds(MainPanel.originX + 551, MainPanel.originY + 20 + 550 - 20, 150, 20);
		chatBut.setBounds(MainPanel.originX + 551 + 150, MainPanel.originY + 20 + 550 - 20, 35, 20);
		infoLB.setBounds(MainPanel.originX + 551, MainPanel.originY + 20 + 550, 150, 20);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == connectBut)
		{
			if (!isConnected)
			{
				new Thread(client).start();
				myturn = CLIENT_TURN;
			} else
				JOptionPane.showMessageDialog(null, "已连接,请勿重连");
		} else if (e.getSource() == createBut)
		{
			if (!isConnected)
			{
				try
				{
					myturn = SERVER_TURN;
					chatTA.append("本机ip:" + InetAddress.getLocalHost().getHostAddress() + "\r\n" + "等待用户加入...\r\n");
					new Thread(sever).start();
				} catch (UnknownHostException e1)
				{
					JOptionPane.showMessageDialog(null, "未知主机异常!");
					e1.printStackTrace();
				}
			} else
				JOptionPane.showMessageDialog(null, "已连接,请勿新建");
		} else if (e.getSource() == chatBut)
		{
			if (isConnected)
			{
				if (Main.myturn == Main.SERVER_TURN)
				{
					Main.sever.sendMessage("chat=" + chatTF.getText());
				} else if (Main.myturn == Main.CLIENT_TURN)
				{
					Main.client.sendMessage("chat=" + chatTF.getText());
				}
				chatTA.append("我:\r\n" + chatTF.getText() + "\r\n");
				chatTF.setText("");
			} else
				JOptionPane.showMessageDialog(null, "无任何玩家连接");
		}
	}
}

class MainPanel extends JPanel implements MouseListener, MouseMotionListener
{
	public static final int originX = 150;
	public static final int originY = 0;
	public static final int WIN = 1;
	public static final int DEAFAULT = 0;
	public static final int LOSE = 2;
	static int state = DEAFAULT;
	static int[][] tempBuf;
	int x, y;
	static int[][] buff;
	int[][] locX;
	int[][] locY;
	public static int turn;

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/棋盘.jpg")), 0, 0, this);
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
			{
				// g.drawString(locX[i][j]+","+locY[i][j],locX[i][j],locY[i][j]);
				if (buff[i][j] == 1)
				{
					g.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/黑棋.png")), locX[i][j] - 38 / 2 + 3, locY[i][j] - 38 / 2 + 3, this);
					if (tempBuf[i][j] == 3)
						g.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/下子.png")), locX[i][j] - 38 / 2 + 3, locY[i][j] - 38 / 2 + 3, this);
				} else if (buff[i][j] == 2)
				{
					g.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/白棋.png")), locX[i][j] - 38 / 2 + 3, locY[i][j] - 38 / 2 + 3, this);
					if (tempBuf[i][j] == 3)
						g.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/下子.png")), locX[i][j] - 38 / 2 + 3, locY[i][j] - 38 / 2 + 3, this);
				} else if (buff[i][j] == 3)
					g.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/选定.png")), locX[i][j] - 38 / 2 + 3, locY[i][j] - 38 / 2 + 3, this);
			}
		if (state == WIN)
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/you win.png")), 550 / 2 - 343 / 2, 551 / 2 - 128 / 2, this);
		} else if (state == LOSE)
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/you lose.png")), 550 / 2 - 343 / 2, 551 / 2 - 128 / 2, this);
		}

	}

	public MainPanel()
	{
		init();
	}

	private void init()
	{
		tempBuf = new int[15][15];
		buff = new int[15][15];
		locX = new int[15][15];
		locY = new int[15][15];
		turn = 1;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
			{
				locX[i][j] = (/* originX + */9) + 38 * j;// 纵坐标是行
				locY[i][j] = (/* originY + */9) + 38 * i;// 横坐标是列

			}

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// System.out.println((e.getX()) + "," + (e.getY()));
		int x = e.getX();
		int y = e.getY();
		int minX = 35;
		int minY = 35;
		int tempI = -1;
		int tempJ = -1;
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
			{
				if (Math.abs(x - locX[i][j]) <= 38 / 2 && Math.abs(y - locY[i][j]) <= 38 / 2)
				{
					// System.out.println(i + "," + j);
					// System.out.println((Math.abs(x - locX[i][j])) + "," +
					// (Math.abs(y - locY[i][j])));
					if (Math.abs(x - locX[i][j]) < minX && Math.abs(y - locY[i][j]) < minY)
					{
						minX = Math.abs(x - locX[i][j]);
						minY = Math.abs(y - locY[i][j]);
						tempI = i;
						tempJ = j;
					}
				}
			}
		if (turn == Main.myturn && Main.ready == 1 && Main.otherReady == 1)
		{
			if (tempI != -1 && tempJ != -1)
				if (buff[tempI][tempJ] == 3)
				{
					new Thread(new PlayWav("sounds/luoz.wav")).start();
					buff[tempI][tempJ] = turn;
					for (int i = 0; i < 15; i++)
						for (int j = 0; j < 15; j++)
						{
							tempBuf[i][j] = 0;
						}
					tempBuf[tempI][tempJ] = 3;
				} else
					return;
			// System.out.println(tempI+","+tempJ+":"+locX[tempI][tempJ]+","+locY[tempI][tempJ]);
			isWinOrLose();
			turn = 3 - turn;
			if (Main.myturn == Main.SERVER_TURN)
			{
				Main.sever.sendMessage("turn=" + turn);
				Main.sever.sendMessage("buff=" + tempI + "=" + tempJ + "=" + Main.SERVER_TURN);
			} else if (Main.myturn == Main.CLIENT_TURN)
			{
				Main.client.sendMessage("turn=" + turn);
				Main.client.sendMessage("buff=" + tempI + "=" + tempJ + "=" + Main.CLIENT_TURN);
			}
		}
		repaint();
	}

	public void isWinOrLose()
	{
		for (int i = 0; i < 15; i++)
		{
			for (int j = 0; j < 15; j++)
				if (buff[i][j] == turn)
				{
					int count = 1;
					int tempJ = j;
					int tempI = i;
					while (tempJ + 1 < 15 && buff[i][tempJ + 1] == turn)
					{
						count++;
						tempJ++;
//						System.out.println(count);
						if (count == 5)
						{
							state = WIN;
							Main.ready = 0;
							Main.otherReady = 0;
							Main.touxReadyLB.setIcon(null);
							Main.otherTouxReadyLB.setIcon(null);
							new Thread(new PlayWav("sounds/win.wav")).start();
							Main.readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始.png")).getImage()));
							int addScore = (int) (Math.random() * 500);
							Main.score += addScore;
							Main.chatTA.append("分数 +" + addScore + "\r\n");
							Main.scoreLB.setText("分数:" + Main.score);
							Main.chenghaoLB.setText(Main.chenghaoStr(Main.score, Main.chenghaoLB));
							if (Main.myturn == Main.SERVER_TURN)
							{
								Main.sever.sendMessage("lose");
								Main.sever.sendMessage("score=" + Main.score);
							} else if (Main.myturn == Main.CLIENT_TURN)
							{
								Main.client.sendMessage("lose");
								Main.client.sendMessage("score=" + Main.score);
							}
							repaint();
							// clearBoard();
							return;
						}
					}
					count = 1;
					while (tempI + 1 < 15 && buff[tempI + 1][j] == turn)
					{
						count++;
						tempI++;
						if (count == 5)
						{
							state = WIN;
							Main.ready = 0;
							Main.otherReady = 0;
							Main.touxReadyLB.setIcon(null);
							Main.otherTouxReadyLB.setIcon(null);
							new Thread(new PlayWav("sounds/win.wav")).start();
							Main.readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始.png")).getImage()));
							int addScore = (int) (Math.random() * 500);
							Main.score += addScore;
							Main.chatTA.append("分数 +" + addScore + "\r\n");
							Main.scoreLB.setText("分数:" + Main.score);
							Main.chenghaoLB.setText(Main.chenghaoStr(Main.score, Main.chenghaoLB));
							if (Main.myturn == Main.SERVER_TURN)
							{
								Main.sever.sendMessage("lose");
								Main.sever.sendMessage("score=" + Main.score);
							} else if (Main.myturn == Main.CLIENT_TURN)
							{
								Main.client.sendMessage("lose");
								Main.client.sendMessage("score=" + Main.score);
							}
							repaint();
							// clearBoard();
							return;
						}
					}
					count = 1;
					tempI = i;
					tempJ = j;
					while (tempI + 1 < 15 && tempJ + 1 < 15 && buff[tempI + 1][tempJ + 1] == turn)
					{
						count++;
						tempI++;
						tempJ++;
						if (count == 5)
						{
							state = WIN;
							Main.ready = 0;
							Main.otherReady = 0;
							Main.touxReadyLB.setIcon(null);
							Main.otherTouxReadyLB.setIcon(null);
							new Thread(new PlayWav("sounds/win.wav")).start();
							Main.readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始.png")).getImage()));
							int addScore = (int) (Math.random() * 500);
							Main.score += addScore;
							Main.chatTA.append("分数 +" + addScore + "\r\n");
							Main.scoreLB.setText("分数:" + Main.score);
							Main.chenghaoLB.setText(Main.chenghaoStr(Main.score, Main.chenghaoLB));
							if (Main.myturn == Main.SERVER_TURN)
							{
								Main.sever.sendMessage("lose");
								Main.sever.sendMessage("score=" + Main.score);
							} else if (Main.myturn == Main.CLIENT_TURN)
							{
								Main.client.sendMessage("lose");
								Main.client.sendMessage("score=" + Main.score);
							}
							repaint();
							// clearBoard();
							return;
						}
					}
					count = 1;
					tempI = i;
					tempJ = j;
					while (tempI - 1 >= 0 && tempJ + 1 < 15 && buff[tempI - 1][tempJ + 1] == turn)
					{
						count++;
						tempI--;
						tempJ++;
						if (count == 5)
						{
							state = WIN;
							Main.ready = 0;
							Main.otherReady = 0;
							Main.touxReadyLB.setIcon(null);
							Main.otherTouxReadyLB.setIcon(null);
							new Thread(new PlayWav("sounds/win.wav")).start();
							Main.readyLB.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("images/开始.png")).getImage()));
							int addScore = (int) (Math.random() * 500);
							Main.score += addScore;
							Main.chatTA.append("分数 +" + addScore + "\r\n");
							Main.scoreLB.setText("分数:" + Main.score);
							Main.chenghaoLB.setText(Main.chenghaoStr(Main.score, Main.chenghaoLB));
							if (Main.myturn == Main.SERVER_TURN)
							{
								Main.sever.sendMessage("lose");
								Main.sever.sendMessage("score=" + Main.score);
							} else if (Main.myturn == Main.CLIENT_TURN)
							{
								Main.client.sendMessage("lose");
								Main.client.sendMessage("score=" + Main.score);
							}
							repaint();
							// clearBoard();
							return;
						}
					}
				}
		}
	}

	public static void clearBoard()
	{
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				buff[i][j] = 0;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new
		// ImageIcon(this.getClass().getResource("images/光标.png")).getImage(),
		// new Point(10, 10), "光标"));
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				if (buff[i][j] == 3)
					buff[i][j] = 0;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{

		int x = e.getX();
		int y = e.getY();
		int minX = 35;
		int minY = 35;
		int tempI = -1;
		int tempJ = -1;
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
			{
				if (Math.abs(x - locX[i][j]) <= 38 / 2 && Math.abs(y - locY[i][j]) <= 38 / 2)
				{
					// System.out.println(i + "," + j);
					// System.out.println((Math.abs(x - locX[i][j])) + "," +
					// (Math.abs(y - locY[i][j])));
					if (Math.abs(x - locX[i][j]) < minX && Math.abs(y - locY[i][j]) < minY)
					{
						minX = Math.abs(x - locX[i][j]);
						minY = Math.abs(y - locY[i][j]);
						tempI = i;
						tempJ = j;
					}
				}
			}
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
			{
				if (buff[i][j] == 3)
				{
					buff[i][j] = 0;
				}
			}
		// System.out.println("turn:"+turn);
		// System.out.println("Myturn:"+Main.myturn);
		if (turn == Main.myturn && Main.ready == 1 && Main.otherReady == 1)
		{
			if (tempI != -1 && tempJ != -1)
				if (buff[tempI][tempJ] == 0)
				{
					buff[tempI][tempJ] = 3;
					tempBuf[tempI][tempJ] = 3;
				}
			// System.out.println(tempI+","+tempJ+":"+locX[tempI][tempJ]+","+locY[tempI][tempJ]);
		}
		repaint();
	}

}
