package searchengine.dictionary;

import java.util.Scanner;

public class MinPQueueLL 
{
	public static void main(String[] args) 
	{
		int ch=0;
		String data;
		Scanner sc=new Scanner(System.in);
		PQueue min_pq=new PQueue();
		while(true)
		{
			System.out.println("\n\n1. Insert Data");
			System.out.println("2. Delete");
			System.out.println("3. Display");
			System.out.println("4. Get the first element");
			System.out.println("5. Get the number of elements");
			System.out.println("6. Exit");
			System.out.print("\nEnter your choice:  ");
			ch=sc.nextInt();
			switch(ch)
			{
				case 1:
					sc.nextLine();
					System.out.print("\nEnter the data:  ");
					data=sc.nextLine();
					min_pq.enqueue(data,1);
					break;
				case 2:
					data=(String)min_pq.dequeue(1);
					if(data==null) System.out.println("\nThe Queue is empty");
					else System.out.println("The removed element is:  "+data);
					break;
				case 3:
					min_pq.display();
					break;
				case 4:
					System.out.println("\nFirst Element is:  "+min_pq.front());
					break;
				case 5:
					System.out.println("\nNo of elements:  "+min_pq.size());
					break;
				case 6:
					System.exit(0);
					break;
				default:
					System.out.println("\n you entered illegal choice");
			}
		}
	}
}
