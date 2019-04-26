/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender on 12-10-2009
 */
package searchengine.spider;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

import searchengine.dictionary.ObjectIterator;
import searchengine.element.PageElementInterface;
import searchengine.element.PageHref;
import searchengine.element.PageWord;
import searchengine.indexer.Indexer;
import searchengine.parser.PageLexer;
import searchengine.url.URLTextReader;

/** Web-crawling objects.  Instances of this class will crawl a given
 *  web site in breadth-first order.
 */
public class BreadthFirstSpider implements SpiderInterface 
{

	/** Create a new web spider.
	@param u The URL of the web site to crawl.
	@param i The initial web index object to extend.
	 */
	public String[] arr=null;
	private Indexer i = null;
	private URL u;
	Vector<String> temp;
	Vector<String> t=new Vector<String>();
	ObjectIterator<String> data;
	LLQueue q=new LLQueue();
	int level;

	public BreadthFirstSpider (URL u, Indexer i) 
	{
		this.u = u;
		this.i = i;
		level=0;
	}
	
	public ObjectIterator<String> get_data(URL ur) throws IOException
	{
		temp=new Vector<String>();
		data=new ObjectIterator<String>(temp);
		URLTextReader in = new URLTextReader(ur);
		PageLexer<PageElementInterface> elts = new PageLexer<PageElementInterface>(in, ur);
		int count = 0;
		while (elts.hasNext()) 
		{
			count++;
			PageElementInterface elt = (PageElementInterface)elts.next();
			if (elt instanceof PageWord)
			{
				temp.add(elt.toString());
			}
		}
		return data;
	}
	
	public void crawl_all(int limit) throws IOException
	{
		URLTextReader in = new URLTextReader(u);
		if(temp.size()==0) 
		{
			get_data(u);
			i.addPage(u,data);
		}
		try 
		{
			PageLexer<PageElementInterface> elts = new PageLexer<PageElementInterface>(in, u);
			while (elts.hasNext()) 
			{
				PageElementInterface elt = (PageElementInterface)elts.next();
				if (elt instanceof PageHref)
				{
					if(t.contains(elt.toString())){}
					else
					{
						if(elt.toString().contains("..")){}
						else if(elt.toString().endsWith(".html")|| elt.toString().endsWith(".htm") 
								|| elt.toString().endsWith(".net"))
						{
							t.add(elt.toString());
							System.out.println(level+1+"  "+elt);
							arr[level]=elt.toString();
							get_data(new URL(elt.toString()));
							i.addPage(new URL(elt.toString()),data);
							q.enqueue(elt.toString());
							level++;
						}
					}
				}
				if(level==limit)
					return;
			}
		} 
		catch (Exception e) 
		{}
	}

	/** Crawl the web, up to a certain number of web pages.
	@param limit The maximum number of pages to crawl.
	 */
	public Indexer crawl (int limit) 
	{
		try {
		get_data(u);
		i.addPage(u,data);
		crawl_all(limit);
		while(level!=limit)
		{
				while(true)
				{
					if(q.first().toString().endsWith(".html")|| q.first().toString().endsWith(".htm") 
						|| q.first().toString().endsWith(".net"))
					{u= new URL(q.dequeue()); break;}
					else q.dequeue();
				}
					crawl_all(limit);
		}
		} catch (Exception e1) {}
		return i;
	}
	public String[] arrays()
	{
		return arr;
	}
	/** Crawl the web, up to the default number of web pages.
	 */
	public Indexer  crawl() 
	{
		// This redirection may effect performance, but its OK !!
		System.out.println("Crawling: "+u.toString());
		arr=new String[crawlLimitDefault];
		return  crawl(crawlLimitDefault);
	}

	/** The maximum number of pages to crawl. */
	public int crawlLimitDefault = 10;

}

class LLQueue
{

    private String data;
    private LLQueue next;
    private LLQueue start;
    private int count;    
    LLQueue(){start=null;count=-1;}

    public void enqueue(String element)
    {
    	System.out.println(element);
        LLQueue temp=new LLQueue();
        temp.data=element;
        temp.next=start;
        start=temp;
        count++;
    }

    public String dequeue()
    {
    	String ob;
        if(isEmpty())
            return null;
        else
        {
            LLQueue temp=start;
            for(int i=0;i<count-1;i++)
                temp=temp.next;
            if(count==0)
            {
                ob=temp.data;
                start=null;
            }
            else
            {
                ob=temp.next.data;
                temp.next=null;
            }
            count--;
            return ob;
        }
    }

    public String first()
    {
        if(isEmpty())
            return null;
        else
        {
        	try{
            LLQueue temp=start;
            for(int i=0;i<count-1;i++)
                temp=temp.next;
            return temp.next.data;
        	}catch(Exception e){return null;}
        }
    }

    public boolean isEmpty()
    {
        if(count==-1 && start==null)
            return true;
        else
            return false;
    }

    public int size()
    {
        return count+1;
    }

    public void display()
    {
        for(int i=count;i>=0;i--)
        {
            LLQueue temp=start;
            for(int j=0;j<i;j++)
                temp=temp.next;
            System.out.println(temp.data);
        }
    }
    
    public boolean present(String str)
    {
    	for(int i=count;i>=0;i--)
        {
            LLQueue temp=start;
            for(int j=0;j<i;j++)
                temp=temp.next;
            if(str.equals((String)temp.data))
            	return true;
        }
    	return false;
    }
    
}

