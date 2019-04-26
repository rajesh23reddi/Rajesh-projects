package searchengine.element;

import java.net.MalformedURLException;
import java.net.URL;

import searchengine.url.URLFixer;

public class PageFrame implements PageElementInterface 
{
  URL pf;
  public PageFrame(String s) throws MalformedURLException
  {
	  pf=new URL(s);
  }
  public PageFrame (URL context, String h) throws MalformedURLException
  {
	   pf = URLFixer.fix(context, h);
  }
  public String toString()
  {
	  return null;
  }
}
