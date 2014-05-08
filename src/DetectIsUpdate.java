import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class DetectIsUpdate
{
	static BufferedReader br = null;
	public static boolean isUpdate()
	{
		Pattern p = Pattern.compile("@version.*\\d.\\d.*@address.*Îå×ÓÆå.exe");
		URL url = null;
		try
		{
			url = new URL("http://hi.baidu.com/fuudnztaiiknuvf/item/cc03971ae37f55f8746a8453");
		} catch (MalformedURLException e)
		{
			return false;
		}
		try
		{
			URLConnection conn = url.openConnection();
			 br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String str = null;
			String buf = null;
			while ((str = br.readLine()) != null)
			{
				Matcher m = p.matcher(str);
				while (m.find())
					buf=m.group();
			}
			String version  = null;
			String web =null;
			version=buf.substring(14, 17);
			web=buf.substring(buf.indexOf("href=\"")+6, buf.indexOf("\"",buf.indexOf("href=\"")+7));
			Main.web = web;
			Main.newVersion = version;
			if(version!=null)
				if( (Main.version+"").equals(version))
					return false;
				else
					return true;
		} catch (IOException e)
		{
			return false;
		}finally{
				try
				{
					if(br!=null)
					br.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return false;
	}
}
