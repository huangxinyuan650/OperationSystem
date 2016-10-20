package hxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainForm extends JFrame {

	private int i,j;//用于接收输入的i，j的值
	private static final long serialVersionUID = 1L;
	private JTextField textFieldI;
	private JTextField textFieldJ;
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainForm() {
		setTitle("小实验");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 250);
		getContentPane().setLayout(null);
				
		JPanel panel = new JPanel();
		panel.setBounds(41, 10, 273, 144);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		textFieldI = new JTextField();
		textFieldI.setBounds(10, 35, 66, 21);
		panel.add(textFieldI);
		textFieldI.setColumns(10);
		
		JLabel lblI = new JLabel("i");
		lblI.setBounds(10, 10, 54, 15);
		panel.add(lblI);
		lblI.setHorizontalAlignment(SwingConstants.CENTER);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(113, 35, 54, 21);
		panel.add(comboBox);
		
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"+", "-", "*", "/"}));
		
		JLabel lblJ = new JLabel("j");
		lblJ.setBounds(202, 10, 54, 15);
		panel.add(lblJ);
		lblJ.setHorizontalAlignment(SwingConstants.CENTER);
		
		textFieldJ = new JTextField();
		textFieldJ.setBounds(189, 35, 66, 21);
		panel.add(textFieldJ);
		textFieldJ.setColumns(10);
		
		JButton buttonij = new JButton("result");
		buttonij.setBounds(58, 66, 159, 23);
		panel.add(buttonij);
		
		JButton btnNewButton = new JButton("i++");
		btnNewButton.setBounds(58, 99, 159, 23);
		panel.add(btnNewButton);
		
		
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread process = new Thread(new Runnable() {					
					@Override
					public void run() {
						i++;
					}
				});
				process.start();
			}
		});
		buttonij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread process = new Thread(new Runnable() {
					
					@SuppressWarnings("static-access")
					@Override
					public void run() {
						try {
							i = Integer.parseInt(textFieldI.getText().toString().trim());
							j = Integer.parseInt(textFieldJ.getText().toString().trim());
							String cal = comboBox.getSelectedItem().toString().trim();
							//休眠5秒
							Thread.sleep(3000);
							int result;
							switch(cal){
							case "+":
								result = i+j;
								break;
							case "-":
								result = i-j;
								break;
							case "*":
								result = i*j;
								break;
							case "/":
								result = i/j;
								break;
						    default :
						    	result = i+j;
						    	break;
							}
							System.out.println("i"+cal+"j = "+result);
							new JOptionPane().showMessageDialog(buttonij, "i"+cal+"j = "+result);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
				process.start();
			}
		});
	}
}
