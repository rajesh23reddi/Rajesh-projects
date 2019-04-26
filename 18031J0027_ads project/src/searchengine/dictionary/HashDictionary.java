package searchengine.dictionary;

import java.util.Enumeration;
import java.util.Hashtable;

public class HashDictionary implements DictionaryInterface {

	@SuppressWarnings("unchecked")
	Hashtable hst=new Hashtable();
	
	@SuppressWarnings("unchecked")
	@Override
	public String[] getKeys() 
	{
		if(hst.size()==0)
			return null;
		else
		{
			String[] str=new String[hst.size()];
			Enumeration data=hst.keys();
			int i=0;
			while(data.hasMoreElements())
			{
				str[i++]=(String) data.nextElement();
			}
			return str;
		}
	}

	@Override
	public Object getValue(String key) 
	{
		if(hst.size()==0)
			return null;
		else
			return hst.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(String key, Object value) 
	{
		hst.put(key, value);
	}

	@Override
	public void remove(String key) 
	{
		hst.remove(key);
	}

}
