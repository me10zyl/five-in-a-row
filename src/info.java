import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class Info
{
	public static String[] getPreviousInfo()
	{
		File file = new File("settings.ini");
		FileInputStream is = null;
		String str []= null;
		try
		{
			if (file.exists())
			{
				Properties pro = new Properties();
				is = new FileInputStream(file);
				pro.load(is);
				str = new String[2];
				str[0] = pro.getProperty("path","");
				str[1] = pro.getProperty("score",Encypt.encypt("0"));
				str[1] = Encypt.encypt(str[1]);
			}
		} catch (IOException e)
		{
			return null;
		} finally
		{
			try
			{
				if (is != null)
					is.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				throw new RuntimeException("程序异常终止（FileInputStream关闭异常）！");// 主要是让程序停止
			}
		}
		return str;
	}

	public static void setPreviousInfo(String root,String score)
	{
		File file = new File("settings.ini");
		FileOutputStream os = null;
		try
		{
			Properties pro = new Properties();
			os = new FileOutputStream(file);
			pro.setProperty("path", root);
			pro.setProperty("score", Encypt.encypt(score));
			pro.store(os, "Settings and Options");
		} catch (IOException e)
		{
			return;
		} finally
		{
			try
			{
				if (os != null)
					os.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				throw new RuntimeException("程序异常终止（FileOutputStream关闭异常）！");
			}
		}
		return;
	}
}