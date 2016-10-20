package hxy;

public class Application {
/*
 * 主方法，业务逻辑部分
 * 声明cpu，以及进程
 */
	public static void main(String[] args) {		
		//声明CPU对象，参数为时间片的大小
		CPUh cpuh = new CPUh(5);
//		//声明cpu中公用变量（变量名，变量的值）
		ResourceVar varX = new ResourceVar("x", 0);
		//ResourceVar varY = new ResourceVar("y", 0);
		//将变量对象添加进cpu
		cpuh.addVar(varX);
//		//cpuh.addVar(varY);
//		//声明进程（进程名，进程需要花费时间，进程进入内存时间，进程需要使用的公有变量，进程对公有变量的操作）
//		ProcessH process1 = new ProcessH("process1", 13, 1, varX, "+");
//		ProcessH process2 = new ProcessH("process2", 13, 2, varX, "-");
//		ProcessH process3 = new ProcessH("process3", 13, 3, varX, "-");
//		ProcessH process4 = new ProcessH("process4", 13, 4, varX, "+");
//		//将进程添加进等待队列
//		cpuh.getProcessQueue().add(process1);
//		//设置进程的状态
//		process1.setCondition("free");
//		process1.setLeftTime(process1.getNeedTime());
//		cpuh.getProcessQueue().add(process2);
//		process2.setCondition("free");
//		process2.setLeftTime(process2.getNeedTime());
//		cpuh.getProcessQueue().add(process3);
//		process3.setCondition("free");
//		process3.setLeftTime(process3.getNeedTime());
//		cpuh.getProcessQueue().add(process4);
//		process4.setCondition("free");
//		process4.setLeftTime(process4.getNeedTime());
		new MainActivityForm(cpuh);
		//new MainForm(cpuh.getWaitQueue(), cpuh.getVarList());
		//new ProcessAddForm(cpuh.getWaitQueue(), cpuh.getVarList());
		//启动cpu对象，处理进程
		//cpuh.doExcute();
		//new BackgroundFrame();
	}

}
