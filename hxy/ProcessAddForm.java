package hxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProcessAddForm extends JDialog{
	private static final long serialVersionUID = 1L;
	private JTextField proNameTextField;
	private JTextField needTimeTextField;
	private JTextField arriveTimeTextField;
	private Queue<ProcessH> processQueue;
	private ArrayList<ResourceVar> varList;
	
	public Queue<ProcessH> getProcessQueue() {
		return processQueue;
	}

	public void setProcessQueue(Queue<ProcessH> waitQueue) {
		this.processQueue = waitQueue;
	}

	public ArrayList<ResourceVar> getVarList() {
		return varList;
	}

	public void setVarList(ArrayList<ResourceVar> varList) {
		this.varList = varList;
	}
	
	public ProcessAddForm(Queue<ProcessH> processQueue,ArrayList<ResourceVar> varList) {
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("添加进程");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		System.out.println(processQueue.size());
		
		JLabel label = new JLabel("进程名");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(98, 28, 75, 26);
		getContentPane().add(label);
		
		proNameTextField = new JTextField();
		proNameTextField.setBounds(215, 31, 110, 21);
		getContentPane().add(proNameTextField);
		proNameTextField.setColumns(10);
		
		JLabel label_1 = new JLabel("到达时间");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(98, 108, 75, 26);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("耗时");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(98, 72, 75, 26);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("操作变量");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(98, 144, 75, 27);
		getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("操作");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(98, 181, 75, 26);
		getContentPane().add(label_4);
		
		needTimeTextField = new JTextField();
		needTimeTextField.setBounds(215, 75, 110, 21);
		getContentPane().add(needTimeTextField);
		needTimeTextField.setColumns(10);
		
		arriveTimeTextField = new JTextField();
		arriveTimeTextField.setBounds(215, 111, 110, 21);
		getContentPane().add(arriveTimeTextField);
		arriveTimeTextField.setColumns(10);
		
		JComboBox<String> varComboBox = new JComboBox<String>();
		for(int i =0;i<varList.size();i++){
			varComboBox.addItem(varList.get(i).getVarName());
		}
		varComboBox.setSelectedIndex(-1);
		varComboBox.setBounds(215, 147, 110, 21);
		getContentPane().add(varComboBox);
		
		JComboBox<String> OperationVomboBox = new JComboBox<String>();
		OperationVomboBox.addItem("生产者");
		OperationVomboBox.addItem("消费者");
		OperationVomboBox.setSelectedIndex(0);
		OperationVomboBox.setBounds(215, 184, 110, 21);
		getContentPane().add(OperationVomboBox);
		
		//进程添加按钮监视器
		JButton addButton = new JButton("添加");
		addButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				if("".equals(proNameTextField.getText().trim())||"".equals(arriveTimeTextField.getText())||"".equals(needTimeTextField.getText())){
					new JOptionPane().showMessageDialog(addButton, "请完善进程信息！！！");				
				}else{
					String proName = proNameTextField.getText().trim();
					int arriveTime = Integer.parseInt(arriveTimeTextField.getText().trim());
					int needTime = Integer.parseInt(needTimeTextField.getText().trim());
					String varName = (String)varComboBox.getSelectedItem();
					ResourceVar var =null;
					String operation = (String)OperationVomboBox.getSelectedItem();
					if(operation.equals("生产者")){
						operation = "+";
					}else{
						operation = "-";
					}
					for(int i = 0;i<varList.size();i++){
						if(varName.equals(varList.get(i).getVarName())){
							var = varList.get(i);
						}
					}
					ProcessH process = new ProcessH(proName, needTime, arriveTime, var, operation);
					System.out.println(proName+" "+arriveTime+"  "+needTime+"  "+varName+"  "+operation);
					process.setCondition("free");
					processQueue.add(process);
					dispose();
					System.out.println(processQueue.size());
				}			
			}
		});
		addButton.setBounds(98, 217, 93, 23);
		getContentPane().add(addButton);
		
		JButton CancelButton = new JButton("取消");
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		CancelButton.setBounds(232, 217, 93, 23);
		getContentPane().add(CancelButton);
	}
}
