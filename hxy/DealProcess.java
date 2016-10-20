package hxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Timer;

public class DealProcess implements Runnable{

	private Queue<ProcessH> readyQueue;
	private Queue<ProcessH> waitQueue;
	private Queue<ProcessH> finishQueue = new LinkedList<ProcessH>();
	private int clockTime;
	private int cpuTime;
	private JTable[] tables;
	private ProcessH swap;
	private JLabel label;
	
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public JTable[] getTables() {
		return tables;
	}
	public void setTables(JTable[] tables) { /////processTable,varTable,waitQueueTable,readyQueueTable,finishProcessTable,recorderTable
		this.tables = tables;
	}
	public void setReadyQueue(Queue<ProcessH> readyQueue){
		this.readyQueue = readyQueue;
	}
	public void setWaitQueue(Queue<ProcessH> waitQueue){
		this.waitQueue = waitQueue;
	}
	public Queue<ProcessH> getReadyQueue(){
		return this.readyQueue;
	}
	public Queue<ProcessH> getWaitQueue(){
		return this.waitQueue;
	}
	public int getClockTime() {
		return clockTime;
	}
	public void setClockTime(int clockTime) {
		this.clockTime = clockTime;
	}
	public int getCpuTime() {
		return cpuTime;
	}
	public void setCpuTime(int cpuTime) {
		this.cpuTime = cpuTime;
	}
	public Queue<ProcessH> getFinishQueue() {
		return finishQueue;
	}
	public void setFinishQueue(Queue<ProcessH> finishQueue) {
		this.finishQueue = finishQueue;
	}
	public void initReadyProcess(ProcessH processH){
		/*
		 * 初始化就绪进程，将进程状态改成ready，剩余时间设置成需要时间，开始时间设置为-1,
		 */
		processH.setCondition("ready");
		processH.setLeftTime(processH.getNeedTime());
		processH.setStartTime(-1);
	}

	public void showReadyQueueTable(){
		
	}
	public void showProcess(){
		int i =0;
		for(;i<waitQueue.size();i++){
			tables[0].setValueAt(waitQueue.peek().getProcessName(), i, 0);
			tables[0].setValueAt(waitQueue.peek().getArriveTime(), i, 1);
			tables[0].setValueAt(waitQueue.peek().getNeedTime(), i, 2);
			tables[0].setValueAt(waitQueue.peek().getCondition(), i, 3);
			tables[0].setValueAt(waitQueue.peek().getLeftTime(), i, 4);
			waitQueue.add(waitQueue.poll());
		}
		for(;i-waitQueue.size()<readyQueue.size();i++){
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 0);
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 1);
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 2);
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 3);
			tables[0].setValueAt(readyQueue.peek().getProcessName(), i, 4);
			readyQueue.add(readyQueue.poll());
		}
		for(;i-waitQueue.size()-readyQueue.size()<finishQueue.size();i++){
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 0);
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 1);
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 2);
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 3);
			tables[0].setValueAt(finishQueue.peek().getProcessName(), i, 4);
			finishQueue.add(finishQueue.poll());
		}
		
	}
	public void showWaitQueueTable(){
		for(int i = 0;i<waitQueue.size();i++){
			tables[0].setValueAt(waitQueue.peek().getProcessName(), i, 0);
			tables[0].setValueAt(waitQueue.peek().getArriveTime(), i, 1);
			tables[0].setValueAt(waitQueue.peek().getNeedTime(), i, 2);
			tables[0].setValueAt(waitQueue.peek().getCondition(), i, 3);
			tables[0].setValueAt(waitQueue.peek().getLeftTime(), i, 4);
			waitQueue.add(waitQueue.poll());
		}
	}
	public void showReadyQueue(){
		System.out.println("===================================\nProName startTime condition leftTime");
		int size = readyQueue.size();
		ProcessH temp ;
		for(int i = 0;i<size;i++){
			temp = readyQueue.poll();
			System.out.println("  "+temp.getProcessName()+"  "+temp.getStartTime()+"  "+temp.getCondition()+"   "+temp.getLeftTime());
			readyQueue.add(temp);
		}
	}
	public void showWaitQueue(){
		System.out.println("===================================\nProName  arriveTime needTime condition");
		int size = waitQueue.size();
		ProcessH temp ;
		for(int i = 0;i<size;i++){
			temp = waitQueue.poll();
			System.out.println(temp.getProcessName()+"    "+temp.getArriveTime()+"          "+temp.getNeedTime()+"       "+temp.getCondition());
			waitQueue.add(temp);
		}
	}
	
	public void showData(){
		System.out.println("CPU begin to process processes!!!\n===================================\nClockTime:"+this.clockTime);
		//showVar();
		showWaitQueue();
		showReadyQueue();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		Timer timer = new Timer(1000, new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText(clockTime+"");
				//clockTime++;
				System.out.println("\n==============ClockTime:"+(clockTime)+"==============");
				{
					/*
					 * 扫描一次进程睡眠1秒，交给处理进程，每次都扫描等待队列看是否有进程需要进入就绪队列，
					 * do循环是为了可能同时有多个进程进入就绪队列（此处假设等待队列的到达时间是按先后顺序的）
					 */
					for(int i = 0;i<waitQueue.size();i++){
						if(waitQueue.peek().getArriveTime()==clockTime){
							initReadyProcess(waitQueue.peek());
							System.out.println("Process "+waitQueue.peek().getProcessName()+" turn to readyQueue from waitQueue!!!");
							readyQueue.add(waitQueue.poll());
						}else{
							waitQueue.add(waitQueue.poll());
						}
					}		
	
					if(swap!=null){
						readyQueue.add(swap);
						System.out.println("未完成进程"+swap.getProcessName()+"重新加入到就绪队列！！！");
						swap =null;
						System.out.println("readyQueue:"+readyQueue.size());
					}
				}
				/*
				 * 处理就绪队列
				 * 
				 */
				{
					ProcessH tempPro = readyQueue.peek();
					if(tempPro!=null){
						//判断就绪队列的队头的进程是否已经被执行了，若没有则将当前的时间设置为startTime
						if(tempPro.getStartTime()==-1){
							//设置进程的开始时间
							tempPro.setStartTime(clockTime);
							tempPro.setLeftTime(tempPro.getNeedTime());
						}
						//else
						{
							//判断进程的需要时间是否超过时间片的时间
							if(tempPro.getNeedTime()<=cpuTime){
								//判断进程是否执行到了最后一个时间单位（公有变量的处理）
								if(tempPro.getLeftTime()==1){
									if(tempPro.doOperation()){
										//readyQueue.poll();
										finishQueue.add(readyQueue.poll());
										System.out.println(tempPro.getProcessName()+" has finsished!!!  让出CPU");
									}else{
										//将为完成的进程放到swap中间变量中，等待下一次扫描重新加入就绪队列
										swap = readyQueue.poll();
										//readyQueue.add();
										System.out.println(tempPro.getProcessName()+"进程调用的变量"+tempPro.getVar().getVarName()+"处于只读状态！！！");
									}
								}else{
									tempPro.setLeftTime(tempPro.getLeftTime()-1);
									System.out.println(tempPro.getProcessName()+" is doing!!!");
								}
							}else{
								//当需求时间大于时间片时，判断是否为第一个时间片
								if(tempPro.getLeftTime()==1){
									if(tempPro.doOperation()){
										//readyQueue.poll();
										finishQueue.add(readyQueue.poll());
										System.out.println(tempPro.getProcessName()+" has finsished!!!  让出CPU");
									}else{
										swap = readyQueue.poll();
										//readyQueue.add(readyQueue.poll());
										System.out.println(tempPro.getProcessName()+"进程调用的变量"+tempPro.getVar().getVarName()+"处于只读状态！！！");
									}							
								}else{
									if((tempPro.getNeedTime()-tempPro.getLeftTime()+1)%cpuTime==0&&tempPro.getNeedTime()!=tempPro.getLeftTime()){
										tempPro.setLeftTime(tempPro.getLeftTime()-1);
										System.out.println(tempPro.getProcessName()+" is doing!!!");
										swap = readyQueue.poll();
										//readyQueue.add(readyQueue.poll());
										System.out.println("时间片到，"+tempPro.getProcessName()+"让出cpu！！！");
									}else{
										tempPro.setLeftTime(tempPro.getLeftTime()-1);
										System.out.println(tempPro.getProcessName()+" is doing!!!");
									}
								}						
							}
							
						}
					}else{
						System.out.println("CPU is unuse!!!");
					}
	
				}
	
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					}
				System.out.println("========================================");
				showWaitQueueTable();
				clockTime++;
				if(readyQueue.isEmpty()&&waitQueue.isEmpty()){
					Thread.currentThread().stop();
				}
			}
		});
		timer.start();
		/*
		 * 执行循环体的内容直到内存中的等待队列和就绪队列都为空，结束循环体
		 * 时钟时间每次自增一个，如此可以及时的处理等待队列进入就绪队列
		 */
		//ProcessH swap = null;
//		for(;!readyQueue.isEmpty()||!waitQueue.isEmpty();clockTime++){
//			/*
//			 * 扫描等待队列，看是否有进程需要进入就绪队列
//			 * 取出等待队头的进程，判断是否需要进去就绪队列
//			 */
//			System.out.println("\n==============ClockTime:"+(clockTime)+"==============");
//			{
//				/*
//				 * 扫描一次进程睡眠1秒，交给处理进程，每次都扫描等待队列看是否有进程需要进入就绪队列，
//				 * do循环是为了可能同时有多个进程进入就绪队列（此处假设等待队列的到达时间是按先后顺序的）
//				 */
//				for(int i = 0;i<waitQueue.size();i++){
//					if(waitQueue.peek().getArriveTime()==clockTime){
//						initReadyProcess(waitQueue.peek());
//						System.out.println("Process "+waitQueue.peek().getProcessName()+" turn to readyQueue from waitQueue!!!");
//						readyQueue.add(waitQueue.poll());
//					}else{
//						waitQueue.add(waitQueue.poll());
//					}
//				}
//				
//
//				if(swap!=null){
//					readyQueue.add(swap);
//					System.out.println("未完成进程"+swap.getProcessName()+"重新加入到就绪队列！！！");
//					swap =null;
//					System.out.println("readyQueue:"+readyQueue.size());
//				}
//			}
//			/*
//			 * 处理就绪队列
//			 * 
//			 */
//			{
//				ProcessH tempPro = readyQueue.peek();
//				if(tempPro!=null){
//					//判断就绪队列的队头的进程是否已经被执行了，若没有则将当前的时间设置为startTime
//					if(tempPro.getStartTime()==-1){
//						//设置进程的开始时间
//						tempPro.setStartTime(clockTime);
//						tempPro.setLeftTime(tempPro.getNeedTime());
//					}
//					//else
//					{
//						//判断进程的需要时间是否超过时间片的时间
//						if(tempPro.getNeedTime()<=cpuTime){
//							//判断进程是否执行到了最后一个时间单位（公有变量的处理）
//							if(tempPro.getLeftTime()==1){
//								if(tempPro.doOperation()){
//									//readyQueue.poll();
//									finishQueue.add(readyQueue.poll());
//									System.out.println(tempPro.getProcessName()+" has finsished!!!  让出CPU");
//								}else{
//									//将为完成的进程放到swap中间变量中，等待下一次扫描重新加入就绪队列
//									swap = readyQueue.poll();
//									//readyQueue.add();
//									System.out.println(tempPro.getProcessName()+"进程调用的变量"+tempPro.getVar().getVarName()+"处于只读状态！！！");
//								}
//							}else{
//								tempPro.setLeftTime(tempPro.getLeftTime()-1);
//								System.out.println(tempPro.getProcessName()+" is doing!!!");
//							}
//						}else{
//							//当需求时间大于时间片时，判断是否为第一个时间片
//							if(tempPro.getLeftTime()==1){
//								if(tempPro.doOperation()){
//									//readyQueue.poll();
//									finishQueue.add(readyQueue.poll());
//									System.out.println(tempPro.getProcessName()+" has finsished!!!  让出CPU");
//								}else{
//									swap = readyQueue.poll();
//									//readyQueue.add(readyQueue.poll());
//									System.out.println(tempPro.getProcessName()+"进程调用的变量"+tempPro.getVar().getVarName()+"处于只读状态！！！");
//								}							
//							}else{
//								if((tempPro.getNeedTime()-tempPro.getLeftTime()+1)%cpuTime==0&&tempPro.getNeedTime()!=tempPro.getLeftTime()){
//									tempPro.setLeftTime(tempPro.getLeftTime()-1);
//									System.out.println(tempPro.getProcessName()+" is doing!!!");
//									swap = readyQueue.poll();
//									//readyQueue.add(readyQueue.poll());
//									System.out.println("时间片到，"+tempPro.getProcessName()+"让出cpu！！！");
//								}else{
//									tempPro.setLeftTime(tempPro.getLeftTime()-1);
//									System.out.println(tempPro.getProcessName()+" is doing!!!");
//								}
//							}						
//						}
//						
//					}
//				}else{
//					System.out.println("CPU is unuse!!!");
//				}
//
//			}
//
//			try {
//				Thread.currentThread().sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				}
//			System.out.println("========================================");
		}
//		showData();
//		Iterator<ProcessH> iterator = finishQueue.iterator();
//		System.out.println("\n\n===========Process finish list==============");
//		while(iterator.hasNext()){
//			System.out.println(iterator.next().getProcessName());
//		}
//	}
	
}



//public void showVar(){
//	System.out.println("===================================\nvarName value condition");
//	for(int i = 0;i<varList.size();i++){
//		System.out.println("     "+varList.get(i).getVarName()+"   "+varList.get(i).getVarValue()+"    "+varList.get(i).getFlag());
//	}
//}
/*
 * 初始扫描等待队列代码			
 */
//			do{
//				ProcessH tempPro = waitQueue.peek();
//				if(tempPro!=null){
//					//判断就绪队列的队头的到达时间是否和当前时钟时间一样，是的话就将该进程从等待队列添加进就绪队列
//					if(tempPro.getArriveTime()==clockTime){
//						readyQueue.add(waitQueue.poll());
//						initReadyProcess(tempPro);
//					System.out.println("Process "+tempPro.getProcessName()+" turn to readyQueue from waitQueue!!!");
//						//showData();
//					}else{
//						//System.out.println("There is no process turn to readyQueue from waitQueue!!!");
//						break;
//					}
//				}else{
//					System.out.println("waitQueue is empty!!!");
//					break;
//				}			
//			}while(true);	