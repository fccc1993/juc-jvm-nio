package com.atguigu.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author xfc
 *
 */
public class TestLock {
	public static void main(String[] args) {
		
		Ticket ticket = new Ticket();
		new Thread(ticket,"1号窗口").start();
		new Thread(ticket,"2号窗口").start();
		new Thread(ticket,"3号窗口").start();
	}
}

class Ticket implements Runnable{
	private int ticket = 100;
	private Lock lock = new ReentrantLock();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			lock.lock();
			try{
				if(ticket>0){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"窗口售票,余票为"+ --ticket);
				}
			}finally{
				lock.unlock();
			}
		}
	}
	
}