/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender on 12-10-2009
 */ 

package searchengine.indexer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import searchengine.dictionary.AVLDictionary;
import searchengine.dictionary.BSTDictionary;
import searchengine.dictionary.DictionaryInterface;
import searchengine.dictionary.HashDictionary;
import searchengine.dictionary.ListDictionary;
import searchengine.dictionary.MyHashDictionary;
import searchengine.dictionary.ObjectIterator;
import searchengine.element.PageWord;


/**
 * Web-indexing objects.  This class implements the Indexer interface
 * using a list-based index structure.

A Hash Map based implementation of Indexing 

 */
public class Indexer implements IndexerInterface
{
	/** The constructor for ListWebIndex.
	 */

	// Index Structure 
	DictionaryInterface index;

	// This is for calculating the term frequency
	HashMap<?,?> wordFrequency;

	public Indexer(String mode)
	{
		// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections 
		// list - Dictionary Structure based on Linked List 
		// myhash - Dictionary Structure based on a Hashtable implemented by the students
		// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
		// avl - Dictionary Structure based on AVL Tree implemented by the students

		if (mode.equals("hash")) 
			index = new HashDictionary();
		else if(mode.equals("list"))
			index = new ListDictionary();
		else if(mode.equals("myhash"))
			index = new MyHashDictionary();
		else if(mode.equals("bst"))
			index = new BSTDictionary();
		else if(mode.equals("avl"))
			index = new AVLDictionary();
	}

	/** Add the given web page to the index.
	 *
	 * @param url The web page to add to the index
	 * @param keywords The keywords that are in the web page
	 * @param links The hyperlinks that are in the web page
	 */
	public void addPage(URL url, ObjectIterator<String> keywords)	
	{
	    String str;
	    boolean res=false;
	    int temp=0;
	    String[] dat=new String[keywords.size()];
	    int[] freq=new int[keywords.size()];
	    for(int i=0;i<freq.length;i++)
	    	freq[i]=0;
		while(keywords.hasNext())
	    {
			res=false;
			str=(String)keywords.next();
			if(str.contains(".htm")|| str.contains(".html")|| str.contains(".net") || str.contains("@")){}
			else
			{
				for(int i=0;i<temp;i++)
					if(dat[i].equals(str))
					{
						res=true;
						freq[i]++;
						break;
					}
				if(res==false)
				{
					dat[temp]=str;
					temp++;
				}
			}
	    }
		for(int i=0;i<temp;i++)
		{
			str=url.toString()+"-"+dat[i]+"-"+freq[i];
			index.insert(str,dat[i]);
		}
	}

	/** Produce a printable representation of the index.
	 *
	 * @return a String representation of the index structure
	 */
	public String toString()
	{
		String key[]=index.getKeys();
		String str=null;
		for(int i=0;i<key.length;i++)
			str+=key[i]+"\t"+index.getValue(key[i]);
		return str;
	}

	/** Retrieve all of the web pages that contain the given keyword.
	 *
	 * @param keyword The keyword to search on
	 * @return An iterator of the web pages that match.
	 */
	public ObjectIterator<String> retrievePages(PageWord keyword)
	{
		String[] keys=index.getKeys();
		String str;
		Vector<String> vec=new Vector<String>();
		ObjectIterator<String> dat=new ObjectIterator<String>(vec); 
		str=(String)keyword.toString();
		for(int i=0;i<keys.length;i++)
		if(index.getValue(keys[i]).equals(str))
		{
			vec.add((String)keys[i]);
		}
		return dat;
	}

	/** Retrieve all of the web pages that contain any of the given keywords.
	 *	
	 * @param keywords The keywords to search on
	 * @return An iterator of the web pages that match.
	 * 
	 * Calculating the Intersection of the pages here itself
	 **/
	
	public ObjectIterator<String> retrievePages(ObjectIterator<String> keywords)
	{
		String[] keys=index.getKeys();
		String str;
		Vector<String> vec=new Vector<String>();
		ObjectIterator<String> dat=new ObjectIterator<String>(vec);
		while(keywords.hasNext())
		{
			str=(String)keywords.next();
			for(int i=0;i<keys.length;i++)
			{
				if(index.getValue(keys[i]).equals(str))
				{
					vec.add((String)keys[i]);
				}
			}
		}
		return dat;
	}

	/** Save the index to a file.
	 *
	 * @param stream The stream to write the index
	 */
	public void save(FileOutputStream stream) throws IOException
	{
		PrintWriter pr=new PrintWriter(stream);
		String key[]=index.getKeys();
		for(int i=0;i<key.length;i++)
		{
			String str=" ";
			str+=key[i]+"\t"+index.getValue(key[i]);
			pr.write(str);
			pr.println();
		}
		pr.close();
	}

	/** Restore the index from a file.
	 *
	 * @param stream The stream to read the index
	 */
	public void restore(FileInputStream stream) throws IOException
	{
		Scanner sc=new Scanner(stream);
		while(sc.hasNextLine())
		{
			try{
			StringTokenizer st=new StringTokenizer(sc.nextLine());
			index.insert(st.nextToken("\t"),st.nextToken());
			}
			catch(Exception e){}
		}
	}

	/* Remove Page method not implemented right now
	 * @see searchengine.indexer#removePage(java.net.URL)
	 */
	public void removePage(URL url) {}
};
