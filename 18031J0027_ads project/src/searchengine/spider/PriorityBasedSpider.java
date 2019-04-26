/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Created by Mahender on 12-10-2009
 */
package searchengine.spider;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

import searchengine.dictionary.ObjectIterator;
import searchengine.element.PageElementInterface;
import searchengine.element.PageHref;
import searchengine.element.PageWord;
import searchengine.indexer.Indexer;
import searchengine.parser.PageLexer;
import searchengine.url.URLTextReader;

/** Web-crawling objects.  Instances of this class will crawl a given
 *  web site in Priority-first order.
 */
public class PriorityBasedSpider implements SpiderInterface 
{

	/** Create a new web spider.
	@param u The URL of the web site to crawl.
	@param i The initial web index object to extend.
	 */
	public String[] arr=null;
	private Indexer i = null;
	private URL u; 
	int j=0;
	Vector<String> temp=new Vector<String>();
	ObjectIterator<String> data=new ObjectIterator<String>(temp);
	Priority pq=new Priority();
	Scanner sc=new Scanner(System.in);
	Stack<String> stk=new Stack<String>();
	int level;
	String str;

	public PriorityBasedSpider (URL u, Indexer i) 
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
		get_data(u);
		i.addPage(u,data);
		try 
		{
			PageLexer<PageElementInterface> elts = new PageLexer<PageElementInterface>(in, u);
			while (elts.hasNext()) 
			{
				PageElementInterface elt = (PageElementInterface)elts.next();
				if (elt instanceof PageHref)
				{
					if(elt.toString().equals(u.toString()))
							return;
					int count=0,s=0;
					String d=elt.toString();
					System.out.println("d is "+d);
					arr[j]=d;                      // storing every link in to an array
					j++;
					if(d.contains("..")){}
					else
					{
						if( d.endsWith(".html")|| d.endsWith(".htm")|| d.endsWith(".net"))
						{
							while(s!=d.length())
							{
								if(d.charAt(s)=='/')
									count++;
								s++;
							}
							if(!pq.present(d))
								pq.insert(elt.toString(),count-2);
						}
					}
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/** Crawl the web, up to a certain number of web pages.
	@param limit The maximum number of pages to crawl.
	 */
	public Indexer crawl (int limit) 
	{
		try
		{
		System.out.println(level+1+"  "+u.toString());
		//arr[level]=u.toString();
		level++;
		crawl_all(limit);
		while(level!=limit)
		{
			try 
			{
				if(stk.size()==0)
				{
					str=(String)pq.remove();
					stk.push(str);
				}
				else
				{
					str=(String)pq.get_first();
					while(stk.contains(str))
					{
						str=(String)pq.remove();
					}
					stk.push(str);
				}
				arr[level]=u.toString();
				System.out.println(level+1+"  "+str);
				level++;
				if(str.endsWith(".html")||str.endsWith(".htm")||str.endsWith(".net"))
				{
					if(str.contains("..")){}
					else
					{
						u= new URL(str);
						crawl_all(limit);
					}
				}
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
		}
		}catch(Exception e){}
		return i;
	}
	public String[] arrays()
	{
		return arr;
	}
	/** Crawl the web, up to the default number of web pages.
	 */
	public Indexer  crawl() {
		// This redirection may effect performance, but its OK !!
		System.out.println("Crawling: "+u.toString());
		arr=new String[crawlLimitDefault];
		return  crawl(crawlLimitDefault);
	}

	/** The maximum number of pages to crawl. */
	public int crawlLimitDefault = 10;

}

class Priority 
{
	class Node
	{
		Node Next;
		Node Previous;
		int index;
		int depth;
		Object Data;
		
		Node(){Next=Previous=null;}
		
	}
	Node front;
	Node rear;
	int count;
	
	Priority(){rear=front=null;count=0;}
	
	public void insert(Object o,int d)
	{
		Node temp=new Node();
		if(rear==null)
		{
			front=temp;
			temp.index=1;
		}
		else
		{
			rear.Previous=temp;
			temp.index=rear.index+1;
		}
		temp.depth=d;
		temp.Data=o;
		temp.Next=rear;
		rear=temp;
		count++;
		insert_arrange();
		remove_arrange();
	}
	
	public Object remove()
	{
		if(isEmpty()) return null;
		Object data=front.Data;
		front.Data=rear.Data;
		front.depth=rear.depth;
		rear=rear.Next;
		remove_arrange();
		count--;
		if(rear!=null)	
			rear.Previous=null;
		else{}
		insert_arrange();
		return data;
	}
	
	public boolean isEmpty(){return rear==null;}
	
	public void remove_arrange()
	{
		Object str;
		int t;
		int ind;
		Node temp=front;
		Node temp2=front;
		while(temp.index!=count)
		{
			int dep=temp.depth;
			ind=temp.index;
			while(true)
			{
				if(temp==null)
					return;
				else if(temp.index==2*ind)
					break;
				else
					temp=temp.Previous;
			}
			if(temp==null)
				return;
			if(temp.Next==null){}
			else
			{
				int d=temp.depth;
				if(d > temp.Next.depth)
					temp=temp.Next;
			}
			if(dep > temp.depth)
			{
				str=temp2.Data;
				t=temp2.depth;
				temp2.Data=temp.Data;
				temp2.depth=temp.depth;
				temp.Data=str;
				temp.depth=t;
				temp2=temp;
			}
			else
				break;
			
		}
	}
	
	public void insert_arrange()
	{
		Object str;
		int t;
		Node temp=rear;
		Node temp2=rear;
		int ind;
		while(temp.index!=1)
		{
			int dep=temp.depth;
			ind=temp.index;
			while(true)
			{
				if(temp==null)
					return;
				else if(temp.index==ind/2)
					break;
				else
					temp=temp.Next;
			}
			if(temp==null)
				return;
			if(dep < temp.depth)
				{
					str=temp2.Data;
					t=temp2.depth;
					temp2.Data=temp.Data;
					temp2.depth=temp.depth;
					temp.Data=str;
					temp.depth=t;
					temp2=temp;
				}
				else
					break;
			
		}
	}
	
	public Object get_first()
	{
		return front.Data;
	}
	
	public void Display()
	{
		Node temp=front;
		for(int i=0;i<count;i++)
		{
			System.out.println(temp.Data+"   "+temp.depth);
			temp=temp.Previous;
		}
	}
	
	public int get_size(){ return count;}
	
	public boolean present(String str)
	{
		Node temp=front;
		for(int i=0;i<count;i++)
		{
			if(str.compareTo((String)temp.Data)==0)
				return true;
			temp=temp.Previous;
		}
		return false;
	}
	
}


