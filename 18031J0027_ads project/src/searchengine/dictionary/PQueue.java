package searchengine.dictionary;

public class PQueue implements PQueueADT
{
	Linked_List lst=new Linked_List();
	
	@Override
	public Object dequeue(int ch){return lst.remove(ch);}

	@Override
	public void enqueue(Object value,int ch){lst.insert(value,ch);}
	
	@Override
	public Object front()
	{
		if(is_empty())return null;
		return lst.get_first();
	}
	
	@Override
	public boolean is_empty(){return lst.isEmpty();}
	
	@Override
	public int size(){return lst.get_size();}
	
	public void display(){lst.Display();}
	
}