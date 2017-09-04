package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumerForLock {
	public static void main(String[] args) {
		Clerk2 clerk = new Clerk2();
		
		Productor2 pro =new Productor2(clerk);
		Consumer2 con = new Consumer2(clerk);
		
		new Thread(pro,"生产者A").start();
		new Thread(con,"消费者B").start();
		
		new Thread(pro,"生产者C").start();
		new Thread(con,"消费者D").start();
		
	}
}

class Clerk2{
	private int product = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void get(){
		lock.lock();
		
		try{
			//避免虚假唤醒问题,wait() 必须在循环中使用
			while(product>=4){
				System.out.println("产品已满");
				
				try {
					condition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " : " + ++product);
			condition.signalAll();
			
		}finally{
			lock.unlock();
		}
		
		
	}
	
	public void sale(){
		lock.lock();
		
		try{
			while(product<=0){
				System.out.println("缺货");
				try {
					condition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " : " + --product);
			condition.signalAll();;
			
		}finally{
			lock.unlock();
		}
		
		
	}
}

class Productor2 implements Runnable{
	private Clerk2 clerk;
	
	public Productor2(Clerk2 clerk){
		this.clerk = clerk;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2000; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clerk.get();
		}
	}
	
}
class Consumer2 implements Runnable{
	private Clerk2 clerk;
	
	public Consumer2(Clerk2 clerk){
		this.clerk = clerk;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2000; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clerk.sale();
		}
	}
	
}