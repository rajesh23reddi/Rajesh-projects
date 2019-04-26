
package searchengine.dictionary;

/* 
 * PQueueADT.java
 * 
 * ADT for Priority Queue implemented with Minimum Priority or Maximum Priority
 */
 
public interface PQueueADT  {
 
	/*
	 * Inserts the element in the priority Queue
	 */
	public void enqueue(Object value,int ch);
 
	/*
	 * Deletes the Priority(first element) element from the Queue.
	 */
	public Object dequeue(int ch);
 
	/*
	 * Returns the number of elements of the Queue
	 */
	public int size();
 
	/*
	 * Returns true if Queue is empty and false otherwise. 
	 */
	public boolean is_empty();
 
	/*
	 * Returns the Priority(first element) element of the Queue 
	 */
	public Object front();
 
}