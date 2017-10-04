package BackEnd;

import java.util.concurrent.LinkedBlockingQueue;
/**
 * This is a basic thread pool that executes all of the tasks the server creates
 * @author Tevin Schmidt
 * @author James Bews
 * @since March 31,2017
 *
 */
public class ThreadPool {
	
	/**
	 * The array of worker threads
	 */
	private Worker [] threads;
	
	/**
	 * The queue that holds all the tasks waiting to be executed
	 */
	private LinkedBlockingQueue<Task> queue;
	
	/**
	 * Constructs a ThreadPool with the desired limit of threads
	 * @param nThreads is the desired limit of threads
	 */
	public ThreadPool(int nThreads){
		queue = new LinkedBlockingQueue<Task>();
		threads = new Worker[nThreads];
		
		for(int i = 0; i < nThreads; i++){
			threads[i] = new Worker();
			threads[i].start();
		}
	}
	
	/**
	 * Adds a task to the Queue
	 * @param task is the task being added to the queue
	 */
	public void execute(Task task){
		synchronized (queue){
			queue.add(task);
			queue.notify();
		}
	}
	
	/**
	 * This class acts as the worker in the ThreadPool, it waits to for there
	 * to be a task in the queue, then when it gets a task it runs it. For the
	 * purpose of the flight management system it waits for clients to connect 
	 * then executes the task.
	 * 
	 * @author Tevin Schmidt
	 * @author James Bews
	 * @since March 31, 2017
	 *
	 */
	private class Worker extends Thread {
		public void run(){
			Task toPerform;
			
			while(true){
				synchronized (queue) {
					while(queue.isEmpty()){
						try{
							queue.wait();
						}
						catch(InterruptedException e){
							System.err.println("An error occurred while queue is waiting.");
							System.err.println(e.getMessage());
						}
						
					}
					toPerform = queue.poll();
				}
				
				try{
					toPerform.run();
				}
				catch(RuntimeException e){
					System.err.println("Thread pool is interrupted due to an issue: " + e.getMessage());
					System.err.println(e.getStackTrace());
				}
			}
			
		}
	}
}
