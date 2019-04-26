package searchengine.dictionary;

public class ListDictionary implements DictionaryInterface {

	class ListNode
    {
	String key;
	Object value;
	ListNode next;
    }
    private ListNode start;
    private int count;

	public String[] getKeys() 
	{
		if(count==0)
            return null;
        else
        {
            String[] arr=new String[count];
            ListNode temp=start;
            for(int i=0;i<count;i++)
            {
                arr[i]=temp.key;
                temp=temp.next;
            }
            return arr;
        }
	}

	@Override
	public Object getValue(String key) 
	{
		if(count==0)
            return null;
        else
        {
            ListNode temp=start;
            for(int i=0;i<count;i++)
            {
                if(temp.key.equals(key))
                    return temp.value;
                else
                    temp=temp.next;
            }
            return null;
        }
	}

	@Override
	public void insert(String key, Object value) 
	{
		boolean res=false;
        ListNode temp=new ListNode();
        String[] arr=getKeys();
        try
        {
            for(int i=0;;i++)
                if(arr[i].equals(key)) { res=true; break;}
        }catch(Exception e){}
        if(res==false)
        {
            temp.key=key;
            temp.value=value;
            temp.next=null;
            temp.next=start;
            start=temp;
            count++;
        }
        else
        {
            ListNode temp2=start;
            for(int i=0;i<count;i++)
                if(temp2.key.equals(key)){break;}
                else temp2=temp2.next;
            temp2.value=value;
        }

	}

	@Override
	public void remove(String key) 
	{
		int i;
        boolean res=false;
        if(count==0)
            System.out.println(" the list is empty");
        else
        {
            ListNode temp=start;
            for(i=0;i<count;i++)
                if(temp.key.equalsIgnoreCase(key))
                {
                    res=true;
                    break;
                }
                else
                	temp=temp.next;
            if(res)
            {
                temp=start;
                for(int j=0;j<i-1;j++)
                    temp=temp.next;
                if(i==0)
                	start=start.next;
                else
                	temp.next=temp.next.next;
                count--;
            }
            else
                System.out.println("\n the key is not present in the list");
        }

	}

}
