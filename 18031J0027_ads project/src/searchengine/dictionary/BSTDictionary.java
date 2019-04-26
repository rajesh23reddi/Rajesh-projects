package searchengine.dictionary;

public class BSTDictionary implements DictionaryInterface 
{
	private BSTNode root;
	private int size,count;
	private String Key[];
	
	public BSTDictionary(){root = null;size = 0;}
	
	@Override
	public String[] getKeys(){Key = new String[size];count=0;Key = get_keys(root);return Key;}
	
	public String[] get_keys(BSTNode node) 
	{
		if(node == null)
			return null;	
		get_keys(node.left);
		Key[count] = node.key;
		count++;
		get_keys(node.right);
		return Key;	
	}

	@Override
	public Object getValue(String key) {return get_value(root,key);}
	
	public Object get_value(BSTNode node,String key) 
	{
		if(node == null)
			return null;
		else if(key.compareToIgnoreCase(node.key) == 0)
			return node.value;
		else if(key.compareToIgnoreCase(node.key) > 0)
			return get_value(node.right,key);
		else
			return get_value(node.left,key);
	}

	@Override
	public void insert(String key, Object value) {root = insert_data(root,key,value);}
	
	public BSTNode insert_data(BSTNode node,String key,Object value) 
	{
		if(node == null)
		{
			node = new BSTNode(key,value);
			size++;
		}
		else if(key.compareToIgnoreCase(node.key) > 0)
			node.right = insert_data(node.right,key,value);
		else if(key.compareToIgnoreCase(node.key) < 0)
			node.left = insert_data(node.left,key,value);
		else
			node.value = value;
		return node;
	}

	@Override
	public void remove(String key) {root = remove(root,key);}
	
	public BSTNode remove(BSTNode node,String key) 
	{
		if(node == null){}
		else if(key.compareToIgnoreCase(node.key) > 0)
			node.right = remove(node.right,key);
		else if(key.compareToIgnoreCase(node.key) < 0)
			node.left = remove(node.left,key);
		else
		{
			if(node.right == null)
				node = node.left;
			else
			{
				BSTNode successor = min(node.right);
				node.key = successor.key;
				node.value = successor.value;
				node.right = remove(node.right,successor.key);
				return node;
			}
			size--;
		}
		return node;
	}
	
	public static BSTNode min(BSTNode node) 
	{
		if(node.left == null)
			return node;
		else
			return min(node.left);
	}

}

class BSTNode
{
	String key;
	Object value;
	BSTNode left;
	BSTNode right;
	BSTNode(String key,Object value)
	{
		this.key = key;
		this.value = value;
		left = null;
		right = null;
	}
}
