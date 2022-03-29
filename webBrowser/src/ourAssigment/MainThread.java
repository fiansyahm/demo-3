package ourAssigment;

import java.io.IOException;

public class MainThread {
	public static void main(String[] args) throws IOException {
		MyThread myThread1=new MyThread("Thread 1",10);
		MyThread myThread2=new MyThread("Thread 2",20);
		
		System.out.println("Strating Thread 1...");
		myThread1.setNama("Not Thread-1");
		myThread1.start();
		System.out.println("Strating Thread 2...");
		myThread2.start();
		
//		Thread.yield();
	
		try {
//			Thread.sleep(5000);
			myThread1.join();
			myThread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		01.28
		
		System.out.println("Result from "+myThread1.getNama()+": "+myThread1.getResult());
		System.out.println("Result from "+myThread2.getNama()+": "+myThread2.getResult());
	}
}
