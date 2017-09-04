package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写程序,开启3个线程,线程ID分别为A.B.C,每个线程将自己ID打印10遍,要求输出结果必须按顺序显示
 * 如 ABCABCABC......依次显示
 * @author xfc
 *
 */
public class TestABCAlternate {
	public static void main(String[] args) {
		AlternateDemo ad = new AlternateDemo();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 1; i <= 20; i++) {
					ad.loopA(i);
				}
			}
		},"A").start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 1; i <= 20; i++) {
					ad.loopB(i);
				}
			}
		},"B").start();


		new Thread(new Runnable() {
	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 1; i <= 20; i++) {
					ad.loopC(i);
					System.out.println("--------------------");
				}
			}
		},"C").start();

	}
}

class AlternateDemo{
	private int number = 1;
	
	private Lock lock = new ReentrantLock();
	Condition condition1 = lock.newCondition();
	Condition condition2 = lock.newCondition();
	Condition condition3 = lock.newCondition();
	
	public void loopA(int totalLoop){
		lock.lock();
		
		try{
			if(number != 1){
				condition1.await();
				
			}
			
			for (int i = 1; i <= 2; i++) {
				System.out.println(Thread.currentThread().getName()+ "  "+ i + "  "+ totalLoop);
			}
			
			number = 2;
			condition2.signal();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		
	}
	
	public void loopB(int totalLoop){
		lock.lock();
		
		try{
			if(number != 2){
				condition2.await();
			}
			
			for (int i = 1; i <= 4; i++) {
				System.out.println(Thread.currentThread().getName()+ "  "+ i + "  "+ totalLoop);
			}
			
			number = 3;
			condition3.signal();;
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void loopC(int totalLoop){
		lock.lock();
		
		try{
			if(number != 3){
				condition3.await();
			}
			
			for (int i = 1; i <= 6; i++) {
				System.out.println(Thread.currentThread().getName()+ "  "+ i + "  "+ totalLoop);
			}
			
			number = 1;
			condition1.signal();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		
	}
	
	
}