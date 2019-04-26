package searchengine.dictionary;

public class Linked_List 
{
	class Node
	{
		Node Next;
		Node Previous;
		int index;
		Object Data;
		
		Node(){Next=Previous=null;}
		
	}
	Node front;
	Node rear;
	int count;
	
	Linked_List(){rear=front=null;count=0;}
	
	public void insert(Object o,int ch)
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
		temp.Data=o;
		temp.Next=rear;
		rear=temp;
		count++;
		insert_arrange(ch);
	}
	
	public Object remove(int ch)
	{
		if(isEmpty()) return null;
		Object data=front.Data;
		front.Data=rear.Data;
		rear=rear.Next;
		remove_arrange(ch);
		count--;
		if(rear!=null)	
			rear.Previous=null;
		else{}
		return data;
	}
	
	public boolean isEmpty(){return rear==null;}
	
	public void remove_arrange(int ch)
	{
		Object str;
		int ind;
		Node temp=front;
		Node temp2=front;
		while(temp.index!=count)
		{
			String dat=(String)temp.Data;
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
			if(ch==2)
			{
				if(temp.Next==null){}
				else
				{
					String d=(String)temp.Data;
					if(d.compareTo((String)temp.Next.Data)<0)
						temp=temp.Next;
				}
				if(dat.compareTo((String)temp.Data)<0)
				{str=temp2.Data;temp2.Data=temp.Data;temp.Data=str;temp2=temp;}
				else
					break;
			}
			else
			{
				if(temp.Next==null){}
				else
				{
					String d=(String)temp.Data;
					if(d.compareTo((String)temp.Next.Data)>0)
						temp=temp.Next;
				}
				if(dat.compareTo((String)temp.Data)>0)
				{str=temp2.Data;temp2.Data=temp.Data;temp.Data=str;temp2=temp;}
				else
					break;
			}
			
		}
	}
	
	public void insert_arrange(int ch)
	{
		Object str;
		Node temp=rear;
		Node temp2=rear;
		int ind;
		while(temp.index!=1)
		{
			String dat=(String)temp.Data;
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
			if(ch==1)
				if(dat.compareTo((String)temp.Data)<0)
				{str=temp2.Data;temp2.Data=temp.Data;temp.Data=str;temp2=temp;}
				else
					break;
			else
				if(dat.compareTo((String)temp.Data)>0)
				{str=temp2.Data;temp2.Data=temp.Data;temp.Data=str;temp2=temp;}
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
			System.out.println(temp.Data);
			temp=temp.Previous;
		}
	}
	
	public int get_size(){ return count;}
	
}
