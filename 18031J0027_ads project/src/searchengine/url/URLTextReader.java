package searchengine.url;

import java.io.*;
import java.net.*;

public class URLTextReader extends Reader 
{

	public URLTextReader (URL u) 
	{
		try 
		{
			System.out.println("in URLTextReader");
			URLConnection c = u.openConnection();
			String t = c.getContentType();
			if (t == null) return;

			InputStream in = c.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			if (!t.startsWith("text/html"))  
			{
				reader.close();
				System.out.println("Type is not text");
				reader = null;
			}
		} 
		catch (IOException e) 
		{

			reader = null;
		}
	}

	public int read (char[] cbuf, int off, int len) throws IOException 
	{
		if (reader != null)
			return reader.read(cbuf, off, len);
		else
			throw new IOException();
	}

	public String readLine () throws IOException 
	{
		if (reader != null)
			return reader.readLine();
		else
			return null;
	}

	public void close () throws IOException 
	{
		reader.close();
	}

	private BufferedReader reader;
}
