package com.atguigu.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {
	public static void main(String[] args) {
		ReadWriteLockDemo rw = new ReadWriteLockDemo();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				rw.set((int)(Math.random()*101));
			}
		},"write").start();
		
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					rw.get();
				}
			},"read").start();
		}
	}
}
class ReadWriteLockDemo{
	
	private int number = 0;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void get(){
		lock.readLock().lock();
		
		try{
			
			System.out.println(Thread.currentThread().getName() + ":" + number);
		}finally{
			lock.readLock().unlock();
		}
		
	}
	
	public void set(int number){
		lock.writeLock().lock();
		try{
			System.out.println(Thread.currentThread().getName());
			this.number = number;
			
		}finally{
			lock.writeLock().unlock();
		}
	}
	
}