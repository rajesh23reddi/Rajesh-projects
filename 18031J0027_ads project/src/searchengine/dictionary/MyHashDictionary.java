package searchengine.dictionary;

public class MyHashDictionary implements DictionaryInterface 
{
	private  LinkedList[] list;
	private int size;
	private int count;
	
	public MyHashDictionary()
	{
		size=10;count=0; 
		list=new LinkedList[size];
		for(int i=0;i<size;i++)
			list[i]=new LinkedList();
	}
	
	@Override
	public String[] getKeys() 
	{
		if(count==0)
		{
			System.out.println("\nthe table is empty");
			return null;
		}
		else
		{
			String[] str=new String[count];
			int k=0;
			for(int i=0;i<size;i++)
			{	
				int s=list[i].getsize();
				for(int j=0;j<s;j++)
				{
					str[k++]=(String)list[i].get_key(j);
				}
			}
			return str;
		}
	}

	@Override
	public Object getValue(String key) 
	{
		if(count==0)
		{
			System.out.println("\nthe table is empty");
			return null;
		}
		int code=gethashcode(key);
		Object obj=list[code].get_data(key);
		return obj;
	}
	
	private void rehash()
	{
		int temp=size;
		Object data=new Object();
		String d_key;
		LinkedList[] temp_list=new LinkedList[size];
		for(int i=0;i<size;i++)
			temp_list[i]=new LinkedList();
		for(int i=0;i<size;i++)
		{
			int s=list[i].getsize();
			for(int j=0;j<s;j++)
			{
				d_key=(String)list[i].get_key(j);
				data=list[i].get_data(d_key);
				temp_list[i].Insert(d_key, data);
			}
		}
		size+=10;
		list=new LinkedList[size];
		for(int i=0;i<size;i++)
			list[i]=new LinkedList();
		count=0;
		for(int i=0;i<temp;i++)
		{
			int s=temp_list[i].getsize();
			for(int j=0;j<s;j++)
			{
				d_key=(String)temp_list[i].get_key(j);
				data=temp_list[i].get_data(d_key);
				insert_data(d_key,data);
			}
		}
	}
	
	private void check_full()
	{
		if(count!=0)
		for(int i=0;i<size;i++)
		{
			if(list[i].getsize()>5) rehash();
		}
	}

	public void insert_data(String key,Object value)
	{
		int code=gethashcode(key);
		if(list[code].present(key))
			list[code].modify(key,value);
		else
		{
			boolean res=list[code].Insert(key,value);
			if(res) count++;
		}
	}
	
	@Override
	public void insert(String key, Object value)
	{
		check_full();
		insert_data(key,value);
	}

	@Override
	public void remove(String key) 
	{
		int code=gethashcode(key);
		boolean res=false;
		if(count==0)
			System.out.println("\nthe table is empty");
		else
		{
			res=list[code].remove(key);
			if(res) count--;
		}
		if(res==false)
			System.out.println("\nthe key is not present in the table");
			
	}
	
	private int gethashcode(String key)
	{
		int code=0;
		for(int i=0;i<key.length();i++)
			code=code*3+(int)(key.charAt(i));
		code=code%size;
		return code;
	}
}

class LinkedList
{
	private String key;
	private Object Data;
	private LinkedList next,start;
	private int count;
	LinkedList(){start=null;count=0;
}
	public boolean Insert(String key,Object data)
	{
		LinkedList temp=new LinkedList();
		temp.key=key;
		temp.Data=data;
		temp.next=start;
		start=temp;
		count++;
		return true;
	}
	public boolean remove(String key)
	{
		LinkedList temp=start;
		for(int i=0;i<count;i++)
		{
			if(key.equals(temp.key) && i==0)
			{
				start=temp.next;
				count--;
				return true;
			}
			else if(key.equals(temp.key))
			{
				temp=start;
				for(int j=0;j<i-1;j++)
					temp=temp.next;
				if(i!=count-1)
					temp.next=temp.next.next;
				else
					temp.next=null;
				count--;
				return true;
			}
			else
				temp=temp.next;
		}
		return false;
	}
	public Object get_key(int pos)
	{
		LinkedList temp=start;
		Object list_key=new Object();
		if(count!=0)
		{
			for(int i=0;i<pos;i++)
				temp=temp.next;
			list_key=temp.key;
			return list_key;
		}
		return null; 
	}
	
	public Object get_data(String Key)
	{
		LinkedList temp=start;
		Object list_data=new Object();
		list_data=null;
		for(int i=0;i<count;i++)
		{
			if(Key.equals(temp.key))
			{list_data=temp.Data;break;}
			else
				temp=temp.next;
		}
		return list_data; 
	}
	public boolean present(String key)
	{
		LinkedList temp=start;
		for(int i=0;i<count;i++)
		{
			if(key.equals(temp.key))
				return true;
			else
				temp=temp.next;
		}
		return false;
	}
	public void modify(String key,Object data)
	{
		LinkedList temp=start;
		for(int i=0;i<count;i++)
		{
			if(key.equals(temp.key))
				temp.Data=data;
			else
				temp=temp.next;
		}
	}
	public int getsize()
	{
		return count;
	}
}

