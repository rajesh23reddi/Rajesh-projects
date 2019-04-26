package searchengine.search;

import java.util.Vector;

public class Set implements  SetADT<String>
{

	public Vector<String> vec=new Vector<String>();
	Vector<String> temp;
	
	@Override
	public void add(String element){vec.add(element);}

	@Override
	public boolean contains(String target) 
	{
		if(vec.contains(target)) 
			return true;
		return false;
	}

	@Override
	public Vector<String> difference(Vector<String> set) 
	{
		temp=new Vector<String>();
		for(int i=0;i<vec.size();i++)
		{
			if(set.contains(vec.elementAt(i))){}
			else
				temp.add(vec.elementAt(i));
		}
		return temp;
	}

	@Override
	public boolean equals(Vector<String> set) 
	{
		if(vec.size()==set.size())
		{
			for(int i=0;i<vec.size();i++)
				if(set.contains(vec.elementAt(i))){}
				else
					return false;
			return true;
		}
		else
			return false;
	}

	@Override
	public Vector<String> intersection(Vector<String> set) 
	{
		temp=new Vector<String>();
		for(int i=0;i<vec.size();i++)
			if(set.contains(vec.elementAt(i)))
				temp.add(vec.elementAt(i));
		return temp;
	}

	@Override
	public boolean isEmpty() 
	{
		if(vec.size()==0)
			return true;
		else
			return false;
	}

	@Override
	public String iterator(int ind) 
	{
		return vec.elementAt(ind);
	}

	@Override
	public String remove(String element) 
	{
		vec.removeElement(element);
		return element;
	}

	@Override
	public int size() {return vec.size();}

	@Override
	public Vector<String> union(Vector<String> set) 
	{
		temp=new Vector<String>();
		for(int i=0;i<vec.size();i++)
			temp.add(vec.elementAt(i));
		for(int i=0;i<set.size();i++)
			temp.add(set.elementAt(i));
		return temp;
	}
}
