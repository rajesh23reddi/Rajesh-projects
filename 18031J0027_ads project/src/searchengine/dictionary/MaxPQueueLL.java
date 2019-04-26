package searchengine.dictionary;

import java.util.Scanner;

public class MaxPQueueLL 
{
	public static void main(String[] args) 
	{
		int ch=0;
		String data;
		Scanner sc=new Scanner(System.in);
		PQueue max_pq=new PQueue();
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
					max_pq.enqueue(data,2);
					break;
				case 2:
					data=(String)max_pq.dequeue(2);
					if(data==null) System.out.println("\nThe Queue is empty");
					else System.out.println("The removed element is:  "+data);
					break;
				case 3:
					max_pq.display();
					break;
				case 4:
					System.out.println("\nFirst Element is:  "+max_pq.front());
					break;
				case 5:
					System.out.println("\nNo of elements:  "+max_pq.size());
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
