package com.atguigu.juc;

public class TestProductorAndConsumer {
	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		
		Productor pro =new Productor(clerk);
		Consumer con = new Consumer(clerk);
		
		new Thread(pro,"生产者A").start();
		new Thread(con,"消费者B").start();
		
		new Thread(pro,"生产者C").start();
		new Thread(con,"消费者D").start();
	}
}

class Clerk{
	private int product = 0;
	
	public synchronized void get(){
		//避免虚假唤醒问题,wait() 必须在循环中使用
		while(product>=4){
			System.out.println("产品已满");
			
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " : " + ++product);
		this.notifyAll();
		
	}
	
	public synchronized void sale(){
		while(product<=0){
			System.out.println("缺货");
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " : " + --product);
		this.notifyAll();
		
	}
}

class Productor implements Runnable{
	private Clerk clerk;
	
	public Productor(Clerk clerk){
		this.clerk = clerk;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clerk.get();
		}
	}
	
}
class Consumer implements Runnable{
	private Clerk clerk;
	
	public Consumer(Clerk clerk){
		this.clerk = clerk;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}
	
}