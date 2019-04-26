/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender K on 12-10-2009
 */ 


package searchengine.search;


import java.util.*;
import java.io.*;

import searchengine.dictionary.ObjectIterator;
import searchengine.element.PageWord;
import searchengine.indexer.Indexer;


/**
 * The user interface for the index structure.
 *
 * This class provides a main program that allows users to search a web
 * site for keywords.  It essentially uses the index structure generated
 * by WebIndex or ListWebIndex, depending on parameters, to do this.
 *
 * To run this, type the following:
 *
 *    % java SearchDriver indexfile list|custom keyword1 [keyword2] [keyword3] ...
 *
 * where indexfile is a file containing a saved index and list or custom indicates index structure.
 *
 */
public class SearchDriver
{
	ObjectIterator<String> i;
	StringTokenizer  stk;
	int limit=20;
	Object[] res_arr;
	
	public Vector<String> display_links()
	{
		Vector<String> sdata=new Vector<String>();
		Vector<String> res=new Vector<String>();
		String str;
		double[] rank;
		int s;
		if(i!=null)
		{
			while(i.hasNext())
			{
				sdata.add((String)i.next());
			}
			Object temp=new Object();
			Object[] sdat=sdata.toArray();
			rank=new double[sdat.length];
			for(int l=0;l<sdat.length;l++)
			{
				stk=new StringTokenizer((String)sdat[l]);
				str=stk.nextToken("-");
				int depth=0;s=0;
				while(s!=str.length())
				{
					if(str.charAt(s)=='/')
						depth++;
					s++;
				}
				depth-=2;
				stk.nextToken("-");
				rank[l]=Integer.parseInt(stk.nextToken());
				if(depth!=0 & rank[l]!=0)
				{
					double d=(double)1/depth;
					rank[l]*=(d*100.00);
				}
				else
					rank[l]*=100;
			}
			for(int l=0;l<sdat.length-1;l++)
			{
				int itr=l;
				for(s=l+1;s<sdat.length;s++)
				{
					if(rank[itr] < rank[s]){itr=s;}
				}
				if(l!=itr)
				{
					double t;
					temp=sdat[l];t=rank[l];
					sdat[l]=sdat[itr];rank[l]=rank[itr];
					sdat[itr]=temp;rank[itr]=t;
				}
			}
			for(int l=0;l<limit;l++)
			try
			{
				stk=new StringTokenizer((String)sdat[l]);
				str=stk.nextToken("-");
				res.add(str);
				//System.out.println(l+1+"\t"+str);
			}
			catch(Exception e){}
			//System.out.println("\nSearch complete.");
			//System.out.println("---------------\n");
		}
		else
		{
			//System.out.println("Search complete.  0  hits found.");
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public String Search(String str1,String str2,String str3,String str4)
	{
		Vector<String> v=new Vector<String>();
		int count=0;
		if(str2==null)
			count=1;
		else
			count=2;
		String[] str=new String[2];
		str[0]=str1;
		str[1]=str2;
		Indexer w = null;
			
			// Take care to use the right usage of the Index structure
			// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections 
			// list - Dictionary Structure based on Linked List 
			// myhash - Dictionary Structure based on a Hashtable implemented by the students
			// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
			// avl - Dictionary Structure based on AVL Tree implemented by the students
		    w = new Indexer(str4);
			
			try
			{
			    FileInputStream indexSource=new FileInputStream("foo.save");
			    w.restore(indexSource);
			}
			catch(IOException e)
			{
			    System.out.println(e.toString());
			}
			
			for(int i=0;i<count;i++)
			    v.addElement(str[i]);
			res_arr=new Object[v.size()];
			for(int i=0;i<v.size();i++)
				res_arr[i]=new Vector<String>();
			for(int k=0;k<v.size();k++)
			{
				PageWord dat=new PageWord((String) v.elementAt(k));
				i= w.retrievePages(dat);
				res_arr[k]=display_links();
			}
			Set d=new Set();
			d.vec=(Vector<String>)res_arr[0];
			if(str3==null)
			{
				String s="";
				for(int i=0;i<d.vec.size();i++)
				{	s+=d.iterator(i)+"\n";
					System.out.println(s);}
				return s;
			}
			else if(str3.equals("AND"))
			{
				d.vec=d.union((Vector<String>)res_arr[1]);
				String s="";
				for(int i=0;i<d.vec.size();i++)
				{	s+=d.iterator(i)+"\n";
					System.out.println(s);}
				return s;
			}
			else if(str3.equals("OR"))
			{
				d.vec=d.intersection((Vector<String>)res_arr[1]);
				String s="";
				for(int i=0;i<d.vec.size();i++)
				{	s+=d.iterator(i)+"\n";
					System.out.println(s);}
				return s;
			}
			else
			{
				d.vec=d.difference((Vector<String>)res_arr[1]);
				String s="";
				for(int i=0;i<d.vec.size();i++)
				{	s+=d.iterator(i)+"\n";
					System.out.println(s);}
				return s;
			}
	}
	
	/*@SuppressWarnings("unchecked")
	public static void main(String [] args)
    {
        Vector<String> v=new Vector<String>();
        SearchDriver sd=new SearchDriver();
	
	if(args.length<3)
	    System.out.println("Usage: java SearchDriver indexfile list|hash keyword1 [keyword2] [keyword3] [...]");
	else
	    {
		Indexer w = null;
		
		// Take care to use the right usage of the Index structure
		// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections 
		// list - Dictionary Structure based on Linked List 
		// myhash - Dictionary Structure based on a Hashtable implemented by the students
		// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
		// avl - Dictionary Structure based on AVL Tree implemented by the students
		if(args[1].equalsIgnoreCase("list") || args[1].equals("hash") || args[1].equals("myhash") || args[1].equals("bst") 
				|| args[1].equals("avl")){
		    w = new Indexer(args[1]);
		}
		else
		{
			System.out.println("Invalid Indexer mode \n");
		}
		
		try{
		    FileInputStream indexSource=new FileInputStream(args[0]);
		    w.restore(indexSource);
		}
		catch(IOException e){
		    System.out.println(e.toString());
		}
		
		for(int i=2;i<args.length;i++)
		    v.addElement(args[i]);
		sd.res_arr=new Object[v.size()];
		for(int i=0;i<v.size();i++)
			sd.res_arr[i]=new Vector<String>();
		for(int k=0;k<v.size();k++)
		{
			PageWord dat=new PageWord((String) v.elementAt(k));
			sd.i= w.retrievePages(dat);
			System.out.println("Links which contain:  "+dat+" "+"\n");
			sd.res_arr[k]=sd.display_links();
			Vector<String> vect=(Vector<String>)sd.res_arr[k];
			for(int i=0;i<vect.size();i++)
			{
				System.out.println(vect.elementAt(i));
			}
		}
    }
   }*/
};


