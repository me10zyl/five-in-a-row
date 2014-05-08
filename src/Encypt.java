class Encypt
{
	public static String encypt(String s)
	{
		char a[] = null;
		if (s != null)
		{
			 a = s.toCharArray();
			for (int i = 0; i < s.length(); i++)
			{
				if (a[i] != 10)
					a[i] = (char) (a[i] ^ 4);
			}
			return new String(a);
		}
		return s;
	}
}