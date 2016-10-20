//package hxy;
//
//public class MainActivity {
//
//	static int i=1;
//	static int j=1;
//	static int sum=0;
//	public static void main(String[] args) {
//		
////		Demo thread1 = new Demo();
////		thread1.start();
////		for(int i =0;i<20;i++){
////			System.out.println(Thread.currentThread().getName()+i);
////		}
////		Demo1 threadTwo = new Demo1();
////		Thread thread2 = new Thread(threadTwo);
////		thread2.start();
////		for(int i =0;i<20;i++){
////			System.out.println(Thread.currentThread().getName()+i);
////		}
////		
//		//XMLReader xr = new XMLReader();
//		//System.out.println("Start GUI");
//		//new MainForm();
////		Thread progress = new Thread(new Runnable() {
////			@Override
////			public void run() {
////				// TODO Auto-generated method stub
////				System.out.println("i="+i+";j="+j);
////				System.out.print("i+j=");
////				try {
////					Thread.sleep(100);	
////				} catch (InterruptedException e) {
////					e.printStackTrace();
////				}
////				System.out.println(i+j);				
////			}
////		});
////		Thread progress1 = new Thread(new Runnable() {
////			@Override
////			public void run() {
////				// TODO Auto-generated method stub
//////				try {
//////					Thread.sleep(1000);
//////				} catch (InterruptedException e) {
//////					e.printStackTrace();
//////				}
////				i+=10;
////				j+=10;				
////			}
////		});
////		progress.start();
////		progress1.start();
//	}
//}
//
//class Demo1 implements Runnable{
//
//	@Override
//	public void run() {
//		for(int i = 0;i<20;i++){
//			System.out.println(Thread.currentThread().getName()+i);
//		}
//	}
//	
//}
//
//class Demo  extends Thread{
//	@Override
//	public void run() {
//		for(int i =0;i<20;i++){
//			System.out.println(Thread.currentThread().getName()+i);
//		}
//	}
//}
