package hxy;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ChangeCpuTimeDialog extends JDialog {

	private static final long serialVersionUID = 2410865437056957830L;
	private JTextField oldTimeTextField;
	private JTextField newTimeTextField;

	public ChangeCpuTimeDialog(CPUh cpu) {
		setTitle("CPU时间片修改");
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("时间片修改");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(163, 21, 83, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("原时间片");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(101, 68, 73, 15);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("新时间片");
		lblNewLabel_2.setBounds(101, 128, 54, 15);
		getContentPane().add(lblNewLabel_2);
		
		oldTimeTextField = new JTextField(cpu.getCPUTime()+"");
		oldTimeTextField.setBounds(212, 65, 93, 21);
		getContentPane().add(oldTimeTextField);
		oldTimeTextField.setColumns(10);
		
		newTimeTextField = new JTextField();
		newTimeTextField.setBounds(212, 125, 93, 21);
		getContentPane().add(newTimeTextField);
		newTimeTextField.setColumns(10);
		
		JButton OkButton = new JButton("确认修改");
		OkButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				if("".equals(newTimeTextField.getText().trim())){
					new JOptionPane().showMessageDialog(OkButton, "请输入新的时间片！！！");
				}else{
					cpu.setCPUTime(Integer.parseInt(newTimeTextField.getText().trim()));
					System.out.println("====================cpuTime"+cpu.getCPUTime());
					dispose();
				}
			}
		});
		OkButton.setBounds(101, 193, 93, 23);
		getContentPane().add(OkButton);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButton.setBounds(212, 193, 93, 23);
		getContentPane().add(cancelButton);
	}
}
