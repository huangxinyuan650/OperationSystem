package hxy;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainActivityForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public MainActivityForm(CPUh cpuh) {
		initialize();
		
		JPanel processPanel = new JPanel();
		processPanel.setBounds(10, 53, 330, 291);
		processPanel.setLayout(null);
		contentPane.add(processPanel);
		
		JTable processTable = new JTable(new Object[20][6], new Object[]{"进程名","到达时间","运行时间","状态","剩余时间","类型"});
		JScrollPane jspProcess = new JScrollPane(processTable);
		processPanel.add(jspProcess);
		jspProcess.setBounds(0,0,processPanel.getWidth(), processPanel.getHeight());
		
		JLabel processListLabel = new JLabel("进程表列");
		processListLabel.setBounds(145, 32, 54, 15);
		contentPane.add(processListLabel);
		
		JLabel productLabel = new JLabel("产品表列");
		productLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productLabel.setBounds(420, 135, 54, 15);
		contentPane.add(productLabel);
		
		JPanel productPanel = new JPanel();
		productPanel.setBounds(370, 160, 150, 144);
		contentPane.add(productPanel);
		productPanel.setLayout(null);
		JTable varTable = new JTable(new Object[10][2],new Object[]{"变量名","变量值"});
		JScrollPane jspVar = new JScrollPane(varTable);
		productPanel.add(jspVar);
		jspVar.setBounds(0, 0, productPanel.getWidth(), productPanel.getHeight());
		
		JPanel readyPanel = new JPanel();
		readyPanel.setBounds(190, 379, 150, 172);
		contentPane.add(readyPanel);
		readyPanel.setLayout(null);
		JTable readyQueueTable = new JTable(new Object[10][2], new Object[]{"序号","进程名"});
		JScrollPane jspReadyQueue = new JScrollPane(readyQueueTable);
		readyPanel.add(jspReadyQueue);
		jspReadyQueue.setBounds(0,0,readyPanel.getWidth(),readyPanel.getHeight());
		
		JLabel readyQueueLabel = new JLabel("就绪队列");
		readyQueueLabel.setBounds(229, 354, 54, 15);
		contentPane.add(readyQueueLabel);
		
		JLabel waitQueueLabel = new JLabel("等待队列");
		waitQueueLabel.setBounds(48, 354, 54, 15);
		contentPane.add(waitQueueLabel);
		
		JPanel waitQueuepanel = new JPanel();
		waitQueuepanel.setBounds(10, 379, 150, 172);
		contentPane.add(waitQueuepanel);
		waitQueuepanel.setLayout(null);
		JTable waitQueueTable = new JTable(new Object[10][2], new Object[]{"序号","进程名"});
		JScrollPane jspWaitQueue = new JScrollPane(waitQueueTable);
		waitQueuepanel.add(jspWaitQueue);
		jspWaitQueue.setBounds(0, 0, waitQueuepanel.getWidth(), waitQueuepanel.getHeight());
		
		JLabel finishQueueLabel = new JLabel("完成进程队列");
		finishQueueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		finishQueueLabel.setBounds(395, 325, 79, 15);
		contentPane.add(finishQueueLabel);
		
		JPanel finishQueuePanel = new JPanel();
		finishQueuePanel.setBounds(370, 350, 150, 194);
		contentPane.add(finishQueuePanel);
		finishQueuePanel.setLayout(null);
		JTable finishProcessTable = new JTable(new Object[20][2], new Object[]{"完成顺序","进程名"});
		JScrollPane finishJsp = new JScrollPane(finishProcessTable);
		finishQueuePanel.add(finishJsp);
		finishJsp.setBounds(0,0,finishQueuePanel.getWidth(),finishQueuePanel.getHeight());
		
		
		JLabel processRecorderLabel = new JLabel("CPU调度记录");
		processRecorderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		processRecorderLabel.setBounds(687, 32, 85, 15);
		contentPane.add(processRecorderLabel);
		
		JPanel cpuRecorderPanel = new JPanel();
		cpuRecorderPanel.setBounds(568, 57, 406, 420);
		contentPane.add(cpuRecorderPanel);
		cpuRecorderPanel.setLayout(null);
		JTable recorderTable = new JTable(new Object[100][4],new Object[]{"时钟时间","进程名","调度内容","调度结果"});
		

		
		
		recorderTable.getColumnModel().getColumn(0).setResizable(false);
		recorderTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		recorderTable.getColumnModel().getColumn(1).setPreferredWidth(30);
		recorderTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		recorderTable.getColumnModel().getColumn(3).setPreferredWidth(25);
		JScrollPane jspRecorder = new JScrollPane(recorderTable);
		cpuRecorderPanel.add(jspRecorder);
		jspRecorder.setBounds(0, 0, cpuRecorderPanel.getWidth(), cpuRecorderPanel.getHeight());
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(370, 32, 150, 93);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel clockTimeLabel = new JLabel("时钟时间");
		clockTimeLabel.setBounds(52, 10, 54, 15);
		panel.add(clockTimeLabel);
		
		JLabel clockTime = new JLabel("Time");
		clockTime.setFont(new Font("宋体", Font.BOLD, 16));
		clockTime.setBounds(41, 56, 65, 15);
		panel.add(clockTime);
		clockTime.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 199, 21);
		getContentPane().add(menuBar);
		
		JMenu menu = new JMenu("添加");
		menu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu);
		//进程添加菜单监视器
		JMenuItem menuItem = new JMenuItem("添加进程");
		menuItem.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				new ProcessAddForm(cpuh.getProcessQueue(),cpuh.getVarList());
			}
		});
		menu.add(menuItem);
	
		//共有变量菜单添加监视器
		JMenuItem menuItem_1 = new JMenuItem("添加公有变量");
		menuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new VarAddForm(cpuh.getVarList());
			}
		});
		menu.add(menuItem_1);
		
		JMenu menu_1 = new JMenu("刷新");
		menu_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		menu_1.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu_1);
		
		JMenuItem menuItem_2 = new JMenuItem("进程表列刷新");
		menuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				for(int i =0 ;i<cpuh.getProcessQueue().size();i++){
					processTable.setValueAt(cpuh.getProcessQueue().peek().getProcessName(), i, 0);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getArriveTime(), i, 1);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getNeedTime(), i, 2);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getCondition(), i, 3);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getLeftTime(), i, 4);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getOperation().equals("-")?"消费者":"生产者", i, 5);
					cpuh.getProcessQueue().add(cpuh.getProcessQueue().poll());
				}
			}
		});
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("刷新");
		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				for(int i =0 ;i<cpuh.getProcessQueue().size();i++){
					processTable.setValueAt(cpuh.getProcessQueue().peek().getProcessName(), i, 0);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getArriveTime(), i, 1);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getNeedTime(), i, 2);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getCondition(), i, 3);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getLeftTime(), i, 4);
					processTable.setValueAt(cpuh.getProcessQueue().peek().getOperation().equals("-")?"消费者":"生产者", i, 5);
					cpuh.getProcessQueue().add(cpuh.getProcessQueue().poll());
				}
				for(int i = 0;i<cpuh.getVarList().size();i++){
					varTable.setValueAt(cpuh.getVarList().get(i).getVarName(), i, 0);
					varTable.setValueAt(cpuh.getVarList().get(i).getVarValue(), i, 1);
				}
			}
		});
		menu_1.add(mntmNewMenuItem_2);
		menu_1.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("公有变量表列刷新");
		menuItem_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				for(int i = 0;i<cpuh.getVarList().size();i++){
					varTable.setValueAt(cpuh.getVarList().get(i).getVarName(), i, 0);
					varTable.setValueAt(cpuh.getVarList().get(i).getVarValue(), i, 1);
				}
			}
		});
		menu_1.add(menuItem_3);
		
		JMenu mnNewMenu = new JMenu("设置");
		mnNewMenu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("设置系统时间片");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new ChangeCpuTimeDialog(cpuh);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("启动");
		mnNewMenu_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("启动CPU");
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				cpuh.setClockTimeLabel(clockTime);
				for(int i = 0;i<cpuh.getProcessQueue().size();i++){
					cpuh.getProcessQueue().peek().setLeftTime(cpuh.getProcessQueue().peek().getNeedTime());
					cpuh.getProcessQueue().add(cpuh.getProcessQueue().poll());
				}
				cpuh.doExcute(new JTable[]{processTable,varTable,waitQueueTable,readyQueueTable,finishProcessTable,recorderTable});
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_2 = new JMenu("小实验");
		mnNewMenu_2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("1+1=2?");
		mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new MainForm();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JButton startButton = new JButton("启动CPU");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cpuh.setClockTimeLabel(clockTime);
//				try {
//					Thread.currentThread().sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				for(int i = 0;i<cpuh.getProcessQueue().size();i++){
					cpuh.getProcessQueue().peek().setLeftTime(cpuh.getProcessQueue().peek().getNeedTime());
					cpuh.getProcessQueue().add(cpuh.getProcessQueue().poll());
				}
				cpuh.doExcute(new JTable[]{processTable,varTable,waitQueueTable,readyQueueTable,finishProcessTable,recorderTable});
			}
		});

		startButton.setBounds(82, 577, 181, 23);
		contentPane.add(startButton);
		
		JButton button = new JButton("\u663E\u793A\u8C03\u5EA6\u8BB0\u5F55");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//调度记录显示
				List<RecordH> recordList = new ArrayList<RecordH>();
				for(int i = 0;i<DProcess.recordCount;i++){
					System.out.println("=================="+i);
					try{
						recorderTable.getValueAt(i, 0);
					}catch(Exception e){
						System.out.println("报错");
						new ShowRecordDialog(recordList);
						break;
					}
					RecordH record = new RecordH();
					record.setpTime(recorderTable.getValueAt(i, 0).toString());
					record.setpName(recorderTable.getValueAt(i, 1).toString());
					record.setpContent(recorderTable.getValueAt(i, 2).toString());
					record.setpResult(recorderTable.getValueAt(i, 3).toString());
					recordList.add(record);
					System.out.println("------------"+record.getpTime());
				}
				new ShowRecordDialog(recordList);
			}
		});
		button.setBounds(350, 577, 124, 23);
		contentPane.add(button);
		
		/*
		 * 右边部分不显示
		 */
		cpuRecorderPanel.setVisible(false);
		processRecorderLabel.setVisible(false);
		mnNewMenu_2.setVisible(false);
		mnNewMenu.setVisible(false);
		menuItem_2.setVisible(false);
		menuItem_3.setVisible(false);
		menuItem_1.setVisible(false);
	}
	private void initialize(){
		setTitle("进程调度主页");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 649);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}
