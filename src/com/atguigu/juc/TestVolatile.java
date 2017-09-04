package com.atguigu.juc;

/**
 * 内存可见性  volatile/synchronized
 * @author xfc
 *
 */
public class TestVolatile {
	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();
		new Thread(td).start();
		
		while(true){
//			synchronized (td) {
//				if(td.isFlag()){
//					System.out.println("----------");
//					break ;
//				}
//			}
			if(td.isFlag()){
				System.out.println("----------");
				break ;
			}
		}
	}
}

class ThreadDemo implements Runnable{
	private volatile boolean flag = false;
	//private boolean flag = false;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		flag = true;
		System.out.println("flag="+isFlag());
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}