package hxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Timer;

public class DProcess implements Runnable{
	private Queue<ProcessH> processQueue;
	private Queue<ProcessH> readyQueue;
	private Queue<ProcessH> waitQueue;
	private Queue<ProcessH> finishQueue;
	private ArrayList<ResourceVar> varList;
	private ProcessH swap;
	private int clockTime;
	private int cpuTime;
	private String content;
	private JTable[] tables;  //processTable,varTable,waitQueueTable,readyQueueTable,finishProcessTable,recorderTable
	private JLabel clockTimeLabel;
	private int doRecord = 0;
	public static int recordCount;
	
	public int getDoRecord() {
		return doRecord;
	}
	public void setDoRecord(int doRecord) {
		this.doRecord = doRecord;
	}
	public Queue<ProcessH> getProcessQueue() {
		return processQueue;
	}
	public void setProcessQueue(Queue<ProcessH> processQueue) {
		this.processQueue = processQueue;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<ResourceVar> getVarList() {
		return varList;
	}
	public void setVarList(ArrayList<ResourceVar> varList) {
		this.varList = varList;
	}
	public JLabel getClockTimeLabel() {
		return clockTimeLabel;
	}
	public void setClockTimeLabel(JLabel clockTimeLabel) {
		this.clockTimeLabel = clockTimeLabel;
	}
	public ProcessH getSwap() {
		return swap;
	}
	public void setSwap(ProcessH swap) {
		this.swap = swap;
	}
	public JTable[] getTables() {
		return tables;
	}
	public void setTables(JTable[] tables) {
		this.tables = tables;
	}
	public int getCpuTime() {
		return cpuTime;
	}
	public void setCpuTime(int cpuTime) {
		this.cpuTime = cpuTime;
	}
	public int getClockTime() {
		return clockTime;
	}
	public void setClockTime(int clockTime) {
		this.clockTime = clockTime;
	}
	public Queue<ProcessH> getFinishQueue() {
		return finishQueue;
	}
	public void setFinishQueue(Queue<ProcessH> finishQueue) {
		this.finishQueue = finishQueue;
	}
	public Queue<ProcessH> getWaitQueue() {
		return waitQueue;
	}
	public void setWaitQueue(Queue<ProcessH> waitQueue) {
		this.waitQueue = waitQueue;
	}
	public Queue<ProcessH> getReadyQueue() {
		return readyQueue;
	}
	public void setReadyQueue(Queue<ProcessH> readyQueue) {
		this.readyQueue = readyQueue;
	}
	public void initReadyProcess(ProcessH processH){
		/*
		 * 初始化就绪进程，将进程状态改成ready，剩余时间设置成需要时间，开始时间设置为-1,
		 */
		processH.setCondition("ready");
		processH.setLeftTime(processH.getNeedTime());
		processH.setStartTime(-1);
	}
	
	public void showRecorder(ProcessH process,String content,boolean result){
		if(!"".equals(content.trim())){
			recordCount++;
			tables[5].setValueAt(clockTime, doRecord, 0);
			tables[5].setValueAt(process==null?"空闲":process.getProcessName(), doRecord, 1);
			tables[5].setValueAt(content, doRecord, 2);
			tables[5].setValueAt(result?"成功":"失败", doRecord++, 3);
		}else{
			System.out.println("本时间点没调度发生！！！");
		}

	}
	public void showW(){
		for(int i = 0;i<processQueue.size();i++){
			System.out.println(processQueue.peek().getProcessName()+"  "+processQueue.peek().getArriveTime());
			processQueue.add(processQueue.poll());
		}
	}
	public void scanWaitQueue(){
		/*
		 * 扫描一次进程睡眠1秒，交给处理进程，每次都扫描进程队列看是否有进程需要进入就绪队列，
		 * 
		 */
		System.out.println("第"+(clockTime+1)+"次扫描进程队列！！！");
		showW();
		content = null;
		int size = processQueue.size();
		for(int i = 0;i<size;i++){
			if(processQueue.peek().getArriveTime()==clockTime){
				initReadyProcess(processQueue.peek());
				if(content == null){
					content = processQueue.peek().getProcessName()+" ";
				}else{
					content += processQueue.peek().getProcessName()+" ";
				}
				System.out.println("Process "+processQueue.peek().getProcessName()+" turn to readyQueue from waitQueue!!!");
				readyQueue.add(processQueue.poll());
			}else{
				System.out.println(processQueue.peek().getProcessName()+"继续返回队列");
				processQueue.add(processQueue.poll());
			}
		}
		if(content!=null){
			content+="就绪  ";
		}
		if(swap!=null){
			swap.setCondition("ready");
			readyQueue.add(swap);
			swap = null;
		}else{			
		}
		if(!readyQueue.isEmpty()){
			readyQueue.peek().setCondition("doing");			
		}else{			
		}
	}
	
	public void awake(){
		if(!waitQueue.isEmpty()){
			readyQueue.add(waitQueue.poll());
		}else{
			System.out.println("等待队列为空！！！");
		}
//		for(int i = 0;i<waitQueue.size();i++){
//			initReadyProcess(waitQueue.peek());
//			readyQueue.add(waitQueue.poll());
//		}
	}
	
	public String doContent(String content ,String con){
		if(content==null){
			return con;
		}else{
			return content+con;
		}
	}
	
	public void doProcess(){
		/*
		 * 就绪队列的进程处理
		 * 
		 */
		System.out.println("第"+(clockTime+1)+"次处理就绪队列！！！");
		ProcessH tempPro = readyQueue.isEmpty()?null:readyQueue.peek();
		if(tempPro!=null){
			//判断就绪队列的队头的进程是否已经被执行了，若没有则将当前的时间设置为startTime
			if(tempPro.getStartTime()==-1){
				//设置进程的开始时间
				/*
				 * 判断是准备执行进程是否为消费者，若是则在进程执行前就需要为进程分配资源，
				 * 若资源数为0则该进程放弃cpu的使用权并将该进程放到等待队列中等待资源释放来唤醒
				 */
				if("-".equals(tempPro.getOperation())){
					for(int i = 0;i<varList.size();i++){
						if(tempPro.getVar().getVarName().equals(varList.get(i).getVarName())){
							if(varList.get(i).getVarValue()<=0){
								if(content == null){
									content = "消费者"+tempPro.getProcessName()+"资源申请失败转入等待";
								}else{
									content += "消费者"+tempPro.getProcessName()+"资源申请失败转入等待";
								}
								showRecorder(tempPro, content, false);
								System.out.println("资源申请失败！！！");
								tempPro.setCondition("wait");
								waitQueue.add(readyQueue.poll());
								return;
							}else{
								varList.get(i).setVarValue(varList.get(i).getVarValue()-1);
								if(content == null){
									content = "消费者"+tempPro.getProcessName()+"资源申请成功";
								}else{
									content += "消费者"+tempPro.getProcessName()+"资源申请成功";
								}
								System.out.println("资源申请成功！！！");
								tempPro.setStartTime(clockTime);
								tempPro.setLeftTime(tempPro.getNeedTime());
							}
						}
					}
				}else{
					System.out.println("生产者！！！");
				}
			}else{
				System.out.println("////////////////////////////");
			}
			{
				//判断进程的需要时间是否超过时间片的时间
//				if(tempPro.getNeedTime()<=cpuTime)
				{
					//判断进程是否执行到了最后一个时间单位（公有变量的处理）
					if(tempPro.getLeftTime()==1){
						if("-".equals(tempPro.getOperation())){
							tempPro.setLeftTime(tempPro.getLeftTime()-1);
							tempPro.setCondition("finished");
							showRecorder(tempPro, doContent(content,"消费者"+tempPro.getProcessName()+"结束"), true);
							finishQueue.add(readyQueue.poll());
							System.out.println(tempPro.getProcessName()+" has finsished!!!  让出CPU");
						}else if(tempPro.doOperation()){
							//readyQueue.poll();
							tempPro.setLeftTime(tempPro.getLeftTime()-1);
							tempPro.setCondition("finished");
							showRecorder(tempPro, doContent(content,"释放资源，生产者结束，唤醒等待"), true);
							finishQueue.add(readyQueue.poll());
							//当生产者生产完成后需要唤醒等待队列看是否有进程处于等待状态
							awake();
							System.out.println(tempPro.getProcessName()+" has finsished!!!  让出CPU");
						}else{
							//将为完成的进程放到swap中间变量中，等待下一次扫描重新加入就绪队列
							swap = readyQueue.poll();
							showRecorder(swap, doContent(content,"数据"), false);
							//swap.setCondition("ready");
							//readyQueue.add();
							System.out.println(tempPro.getProcessName()+"进程调用的变量"+tempPro.getVar().getVarName()+"处于只读状态！！！");
						}						
					}else{
						//判断当前进程的需要时间是否超过时间片的长度
//						if(tempPro.getNeedTime()<=cpuTime){
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							showRecorder(tempPro, doContent(content," "), true);
//							System.out.println(tempPro.getProcessName()+" is doing!!!");
//						}else
						{
							if((tempPro.getNeedTime()-tempPro.getLeftTime()+1)%cpuTime==0&&tempPro.getNeedTime()!=tempPro.getLeftTime()){
								tempPro.setLeftTime(tempPro.getLeftTime()-1);
								swap = readyQueue.poll();
								showRecorder(swap, doContent(content," 时间片到"+tempPro.getProcessName()+"让出cpu"), true);
								//swap.setCondition("ready");
								System.out.println(tempPro.getProcessName()+" is doing!!!");
								//readyQueue.add(readyQueue.poll());
								System.out.println("时间片到，"+tempPro.getProcessName()+"让出cpu！！！");
							}else{
								tempPro.setLeftTime(tempPro.getLeftTime()-1);
								showRecorder(tempPro, doContent(content," "), true);
								System.out.println(tempPro.getProcessName()+" is doing!!!");
							}
						}
						
						
					}
				}
//				else{
//					//当需求时间大于时间片时，判断是否为最后时间片
//					if(tempPro.getLeftTime()==1){
//						if("-".equals(tempPro.getOperation())){
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							tempPro.setCondition("finished");
//							showRecorder(tempPro, doContent(content,"消费者"+tempPro.getProcessName()+"结束"), true);
//							finishQueue.add(readyQueue.poll());
//							System.out.println(tempPro.getProcessName()+" has finsished!!!  让出CPU");
//						}else if(tempPro.doOperation()){
//							//readyQueue.poll();
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							tempPro.setCondition("finished");
//							showRecorder(tempPro, doContent(content,"释放资源，生产者结束，唤醒等待"), true);
//							finishQueue.add(readyQueue.poll());
//							awake();
//							System.out.println(tempPro.getProcessName()+" has finsished!!!  让出CPU");
//						}else{
//							swap = readyQueue.poll();
//							showRecorder(swap, doContent(content,"数据"), false);
//							//readyQueue.add(readyQueue.poll());
//							System.out.println(tempPro.getProcessName()+"进程调用的变量"+tempPro.getVar().getVarName()+"处于只读状态！！！");
//						}		
//											
//					}else{
//						if((tempPro.getNeedTime()-tempPro.getLeftTime()+1)%cpuTime==0&&tempPro.getNeedTime()!=tempPro.getLeftTime()){
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							swap = readyQueue.poll();
//							showRecorder(swap, doContent(content," 时间片到"+tempPro.getProcessName()+"让出cpu"), true);
//							//swap.setCondition("ready");
//							System.out.println(tempPro.getProcessName()+" is doing!!!");
//							//readyQueue.add(readyQueue.poll());
//							System.out.println("时间片到，"+tempPro.getProcessName()+"让出cpu！！！");
//						}else{
//							tempPro.setLeftTime(tempPro.getLeftTime()-1);
//							showRecorder(tempPro, doContent(content," "), true);
//							System.out.println(tempPro.getProcessName()+" is doing!!!");
//						}
//					}						
//				}
				
			}
		}else{
			showRecorder(null, doContent(content,"空闲"), true);
			System.out.println("CPU is unuse!!!");
		}

	}
	public void clearTable(JTable table){
		for(int i = 0;i<table.getRowCount();i++){
			for(int j = 0;j<table.getColumnCount();j++){
				table.setValueAt(null, i, j);
			}
		}
	}
	
	public void collection(Queue<ProcessH> queue,ArrayList<ProcessH> allProcess){
		for(int i =0;i<queue.size();i++){
			allProcess.add(queue.peek());
			queue.add(queue.poll());
		}
	}
	
	public void sort(ArrayList<ProcessH> allProcess){
		for(int i = 0;i<allProcess.size()-1;i++){
			for(int j = 0;j<allProcess.size()-i-1;j++){
				if(allProcess.get(j).getArriveTime()>allProcess.get(j+1).getArriveTime()){
					ProcessH temp = allProcess.get(j);
					allProcess.set(j, allProcess.get(j+1));
					allProcess.set(j+1,temp);
				}
			}
		}
	}
	//显示进程表列中的信息
	public void showProcess(){
		clearTable(tables[0]);
		ArrayList<ProcessH> allProcess = new ArrayList<ProcessH>();
		collection(processQueue, allProcess);
		collection(waitQueue,allProcess);
		collection(readyQueue, allProcess);
		collection(finishQueue, allProcess);
		if(swap!=null){
			allProcess.add(swap);
		}
		sort(allProcess);
		for(int j = 0;j<allProcess.size();j++){
			tables[0].setValueAt(allProcess.get(j).getProcessName(), j, 0);
			tables[0].setValueAt(allProcess.get(j).getArriveTime(), j, 1);
			tables[0].setValueAt(allProcess.get(j).getNeedTime(), j, 2);
			tables[0].setValueAt(allProcess.get(j).getCondition(), j, 3);
			tables[0].setValueAt(allProcess.get(j).getLeftTime(), j, 4);
			tables[0].setValueAt(allProcess.get(j).getOperation().equals("-")?"消费者":"生产者", j, 5);
		}
	}
	public void showWaitQueue(){
		clearTable(tables[2]);
		for(int i = 0;i<waitQueue.size();i++){
			tables[2].setValueAt(i+1, i, 0);
			tables[2].setValueAt(waitQueue.peek().getProcessName(), i, 1);
			waitQueue.add(waitQueue.poll());
		}
	}
	public void showReadyQueue(){
		clearTable(tables[3]);
		int i=0;
		for(;i<readyQueue.size();i++){
			tables[3].setValueAt(i+1, i, 0);
			tables[3].setValueAt(readyQueue.peek().getProcessName(), i, 1);
			readyQueue.add(readyQueue.poll());
		}
		if(swap!=null){
			tables[3].setValueAt(i+1, i, 0);
			tables[3].setValueAt(swap.getProcessName(), i, 1);
		}
	}
	public void showFinishQueue(){
		clearTable(tables[4]);
		for(int i = 0;i<finishQueue.size();i++){
			tables[4].setValueAt(i+1, i, 0);
			tables[4].setValueAt(finishQueue.peek().getProcessName(), i, 1);
			finishQueue.add(finishQueue.poll());
		}
	}
	public void showVar(){
		clearTable(tables[1]);
		for(int i = 0;i<varList.size();i++){
			tables[1].setValueAt(varList.get(i).getVarName(), i, 0);
			tables[1].setValueAt(varList.get(i).getVarValue(), i, 1);
		}
	}
	public void shows(){
		showProcess();
		showWaitQueue();
		showReadyQueue();
		showFinishQueue();
		showVar();
	}
	
	/*
	 * 定义计时器来进行间隔扫描等待队列，处理就绪队列，刷新页面数据
	 * 
	 */
	public void doExcute(){
		swap = null;
		Timer timer = new Timer(10, new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				clockTimeLabel.setText(clockTime+"");
				scanWaitQueue();
				doProcess();
				System.out.println("这是第"+(clockTime+1)+"次刷新数据");
				shows();
				if(waitQueue.isEmpty()&&readyQueue.isEmpty()&&swap==null&&processQueue.isEmpty()){
					Thread.currentThread().stop();
				}
				clockTime++;
			}
		});
		timer.start();
	}
	@Override
	public void run() {
		doExcute();
	}
}
