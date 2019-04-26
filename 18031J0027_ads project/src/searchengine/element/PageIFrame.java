package searchengine.element;

import java.net.MalformedURLException;
import java.net.URL;

public class PageIFrame implements PageElementInterface
{
	
   public PageIFrame(String s) throws MalformedURLException
   {
	   @SuppressWarnings("unused")
	   URL pif = new URL(s);
   }
   public PageIFrame()
   {
	   
   }
   public String toString()
   {
	return null;
	   
   }
}
