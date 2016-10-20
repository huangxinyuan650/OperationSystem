package hxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowRecordDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShowRecordDialog(List<RecordH> recordList) {
		setVisible(true);
		setBounds(100, 100, 475, 607);
		getContentPane().setLayout(null);
		JTable recorderTable = new JTable(new Object[100][4],new Object[]{"时钟时间","进程名","调度内容","调度结果"});
		JScrollPane jsp = new JScrollPane(recorderTable);
		
		JLabel label = new JLabel("\u8C03\u5EA6\u8BB0\u5F55");
		label.setBounds(185, 10, 69, 15);
		getContentPane().add(label);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 35, 439, 490);
		getContentPane().add(panel);
		panel.add(jsp);
		panel.setLayout(null);
		jsp.setBounds(0, 0, panel.getWidth(), panel.getHeight());
		
		for(int i = 0;i<recordList.size();i++){
			recorderTable.setValueAt(recordList.get(i).getpTime(), i, 0);
			recorderTable.setValueAt(recordList.get(i).getpName(), i, 1);
			recorderTable.setValueAt(recordList.get(i).getpContent(), i, 2);
			recorderTable.setValueAt(recordList.get(i).getpResult(), i, 3);
		}
		
		JButton button = new JButton("\u5173\u95ED");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBounds(323, 535, 93, 23);
		getContentPane().add(button);
	}
}
