package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch : 闭锁
 * @author xfc
 *
 */
public class TestCountDownLatch {
	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(5);
		LatchDemo ld = new LatchDemo(latch);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			new Thread(ld).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("耗费时间为:"+(end - start));
	}
}

class LatchDemo implements Runnable{
	private CountDownLatch latch;
	public LatchDemo(CountDownLatch latch){
		this.latch =latch;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (this) {
			try{
				for (int i = 0; i < 10000; i++) {
					if( i % 2 ==0){
						System.out.println(i);
					}
				}
			}finally{
				latch.countDown();
			}
		}
	}
	
}