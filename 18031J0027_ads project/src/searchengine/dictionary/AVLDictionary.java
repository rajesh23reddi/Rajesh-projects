package searchengine.dictionary;

public class AVLDictionary implements DictionaryInterface 
{

	private AVLNode root;
	private int size,count;
	private String Key[];
	
	public AVLDictionary()
	{
		root = null;
		size = 0;
		count = 0;
	}
	
	@Override
	public String[] getKeys() 
	{
		Key = new String[size];
		count = 0;
		Key = getKeys(root);
		return Key;
	}
	
	public String[] getKeys(AVLNode node) 
	{
		if(node == null)
			return null;
		
		getKeys(node.left);
		Key[count] = node.key;
		count++;
		getKeys(node.right);
		return Key;
		
	}

	@Override
	public Object getValue(String key) 
	{
		return getValue(root,key);
	}
	
	public Object getValue(AVLNode node,String key) 
	{
		if(node == null)
			return null;
		else if(key.compareTo(node.key) == 0)
			return node.value;
		else if(key.compareTo(node.key) < 0)
			return getValue(node.left,key);
		else
			return getValue(node.right,key);
	}

	@Override
	public void insert(String key, Object value) 
	{
		root = insert(root,key,value);
	}
	
	public AVLNode insert(AVLNode node,String key,Object value)
	{
		if(node == null)
		{
			node = new AVLNode(key,value);
			size++;
		}
		else if(key.compareTo(node.key) < 0)
		{
			node.left = insert(node.left,key,value);
			if(node.right == null)
				node.height = bFactor(node.left) + 1;
			if(node.height == 2)
			{
				if(key.compareTo(node.left.key) < 0)
					node = rotateLL(node);
				else
					node = rotateLR(node);
			}
		}
		else if(key.compareTo(node.key) > 0)
		{
			node.right = insert(node.right,key,value);
			if(node.left == null)
				node.height = bFactor(node.right) + 1;
			if(node.height == 2)
			{
				if(key.compareTo(node.right.key) > 0)
					node = rotateRR(node);
				else
					node = rotateRL(node);
			}
		}
		else
			node.value = value;
		return node;
	}
	
	public int bFactor(AVLNode node)
	{
		if(node == null)
			return -1;
		else
			return node.height;
	}
	
	public AVLNode rotateLL(AVLNode node)
	{
		AVLNode temp = node.left;
		node.left = temp.right;
		temp.right = node;
		node.height = Math.max(bFactor(node.left), bFactor(node.right)) + 1;
		temp.height = Math.max(bFactor(temp.left), node.height) + 1;
		return temp;
	}
	
	public AVLNode rotateRR(AVLNode node)
	{
		AVLNode temp = node.right;
		node.right = temp.left;
		temp.left = node;
		node.height = Math.max(bFactor(node.left), bFactor(node.right)) + 1;
		temp.height = Math.max(bFactor(temp.right), node.height) + 1;
		return temp;
	}
	
	public AVLNode rotateRL(AVLNode node)
	{
		node.right = rotateLL(node.right);
		return rotateRR(node);
	}
	
	public AVLNode rotateLR(AVLNode node)
	{
		node.left = rotateRR(node.left);
		return rotateLL(node);
	}

	
	@Override
	public void remove(String key)
	{
		root = remove_data(root,key);
	}
	
	
	public AVLNode remove_data(AVLNode node,String key) 
	{
		AVLNode temp1;
		if( node == null )
		{
			System.out.println("not found");
			return node;
		}
        if( key.compareTo( (String) node.key ) < 0 )
        	node.left = remove_data(node.left,key);
        else if( key.compareTo( (String) node.key ) > 0 )
        	node.right = remove_data(node.right,key);
        else if( node.left != null && node.right != null ) // Two children
        {
        	temp1 = min( node.right );
        	node.key=temp1.key;
        	node.right = remove_data(node.right,key);
        	if( bFactor( node.left ) - bFactor( node.right ) == 2 )
        	{
        		if( key.compareTo( node.left.key ) < 0 )
        		{
        			node = rotateLL( node );
        		}
        		else if( key.compareTo( node.right.key ) > 0)
        		{
        			node = rotateRR( node );
        		}
		    }
       }
       else
       {
    	   node = ( node.left != null ) ? node.left : node.right;
    	   size--;
       }
       return node;
	}
	
	public static AVLNode min(AVLNode node) 
	{
		if(node==null)
			return null;
		if(node.left == null)
			return node;
		else
			return min(node.left);
	}

}

class AVLNode
{
	String key;
	Object value;
	AVLNode left;
	AVLNode right;
	int height;
	AVLNode(String key,Object value)
	{
		this.key = key;
		this.value = value;
		left = null;
		right = null;
		height=0;
	}
}
