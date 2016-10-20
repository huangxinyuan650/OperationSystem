package hxy;

public class ProcessH {
	private String proName;
	private int needTime;
	private int arriveTime;
	private int leftTime;
	private String condition;
	private ResourceVar var;
	private String operation;
	private int startTime;
	//进程名，需要时间，到达时间，进程调用的公有变量，对公有变量的操作（+或者-）
	public ProcessH(String proName,int needTime,int arriveTime,ResourceVar var,String operation){
		this.proName = proName;
		this.needTime = needTime;
		this.setArriveTime(arriveTime);
		this.var = var;
		this.operation = operation;
	}
	public void setOperation(String operation){
		this.operation = operation;
	}
	public String getOperation(){
		return operation;
	}
	public void setVar(ResourceVar var){
		this.var = var;
	}
	public ResourceVar getVar(){
		return this.var;
	}
	public String getProcessName(){
		return this.proName;
	}
	public void setProcessName(String proName){
		this.proName = proName;
	}
	public void setStartTime(int startTime){
		this.startTime = startTime;
	}
	public int getStartTime(){
		return this.startTime;
	}
	public int getNeedTime(){
		return needTime;
	}
	public void setLeftTime(int leftTime){
		this.leftTime = leftTime;
	}
	public int getLeftTime(){
		return leftTime;
	}
	public void setCondition(String condition){
		this.condition = condition;
	}
	public String getCondition(){
		return condition;
	}
	public int getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	//进程对变量的操作
	public boolean doOperation(){
		boolean flag = true;
		//判断变量是否被使用，若使用进程进去等待序列，否则执行操作完成进程
		if(var.getFlag()==true){
			flag = false;
		}
		else{
			var.setFlag(true);
			if(operation.trim().equals("+")){
				var.setVarValue(var.getVarValue()+1);
				System.out.println(var.getVarName()+"==================="+var.getVarValue());
			}
			else{
				if(var.getVarValue()<=0){
					flag = false;
				}else{
					var.setVarValue(var.getVarValue()-1);
					System.out.println(var.getVarName()+"==================="+var.getVarValue());
				}				
			}
			var.setFlag(false);
		}
		return flag;
	}

}
