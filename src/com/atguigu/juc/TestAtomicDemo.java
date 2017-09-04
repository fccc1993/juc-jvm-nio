package com.atguigu.juc;

import java.util.concurrent.atomic.AtomicInteger;

/** 
 * 原子性
 * java.util.concurrent.atomic提供原子变量
 * 1.volatile 保证内存可见性
 * 2.CAS(compare-and-swap) 算法保证数据原子性
 * 	CAS 算法是硬件对于并发操作共享数据的支持
 * @author xfc
 *
 */
public class TestAtomicDemo {
	public static void main(String[] args) {
		AtomicDemo ad =new AtomicDemo();
		for(int i=0;i<10;i++){
			new Thread(ad).start();
		}
	}
}

class AtomicDemo implements Runnable{
	//private volatile int num = 0;
	private AtomicInteger num = new AtomicInteger();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+":"+getNum());
	}

	public int getNum() {
		//return num++;
		return num.getAndIncrement();
	}

	public void setNum(AtomicInteger num) {
		this.num = num;
	} 
	
}