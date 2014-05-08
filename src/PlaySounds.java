import java.applet.Applet;
import java.applet.AudioClip;

//class PlaySounds extends Thread
//{
//
//	private InputStream file;
//
//	public PlaySounds(InputStream wav)
//	{
//		file = wav;
//	}
//
//	public void run()
//	{
//
//		InputStream wavFile = null;
//		wavFile = file;
//		AudioInputStream audioInputStream = null;
//		try
//		{
//			audioInputStream = AudioSystem.getAudioInputStream(wavFile);
//		} catch (Exception e1)
//		{
//			e1.printStackTrace();
//			return;
//		}
//		AudioFormat format = audioInputStream.getFormat();
//		SourceDataLine audioline = null;
//		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//		try
//		{
//			audioline = (SourceDataLine) AudioSystem.getLine(info);
//			audioline.open(format);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			return;
//		}
//
//		audioline.start();
//		int nBytesRead = 0;
//		// ÕâÊÇ»º³å
//		byte[] abData = new byte[512];
//
//		try
//		{
//			while (nBytesRead != -1)
//			{
//				nBytesRead = audioInputStream.read(abData, 0, abData.length);
//				if (nBytesRead >= 0)
//					audioline.write(abData, 0, nBytesRead);
//			}
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//			return;
//		} finally
//		{
//			audioline.drain();
//			audioline.close();
//		}
//
//	}
//
//}
//
class PlayWav extends Applet implements Runnable
{

	AudioClip audio;
	String str = null;
	public PlayWav(String str)
	{
		this.str = str;
	}
	@Override
	public void run()
	{
		try
		{
			audio = Applet.newAudioClip(this.getClass().getResource(str));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		audio.play();
	}

}
