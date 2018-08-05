package com.ccb.kaoshi.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.ccb.kaoshi.model.Question;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class KaoshiMainWindow {

	public ArrayList<String> questionslist=new ArrayList<String>();
	JTextArea textArea_html = new JTextArea();
	private JFrame frmCcbui;
	public  BrowserThread bt;
	
    public JTree DOMtree;
    public DefaultMutableTreeNode rootNode;
    private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KaoshiMainWindow window = new KaoshiMainWindow();
					window.frmCcbui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KaoshiMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCcbui = new JFrame();
		frmCcbui.setTitle("基于Selenium的CCB 党员答题UI辅助助手 By litertiger");
		frmCcbui.setBounds(100, 100, 1025, 601);
		frmCcbui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCcbui.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmCcbui.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("2.打开Chrome浏览器");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				  bt=new BrowserThread();
				 Thread t = new Thread(bt);
				 t.start();
				 
			}
		});
		btnNewButton.setBounds(10, 62, 174, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("1.清理Chrome及Driver");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   // String USER_NAME = System.getProperty("user.name");
				    try {

						   Runtime.getRuntime().exec("taskkill /im chrome.exe /f ");
						   Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f ");
	
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			     
			}
		});
		btnNewButton_1.setBounds(10, 10, 174, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("分析HTML");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content=bt.getHTML();
				System.out.println(content);
				//textArea_html.setText(content);
			}
		});
		btnNewButton_3.setBounds(749, 95, 174, 23);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("3. 分析题目");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questionslist.clear();
				String questions=(String)bt.js.executeScript("var qs='';for(var i=0;i<100;i++){  qs=qs+'####'+document.getElementsByClassName('exam-question')[i].outerText;} return qs;");
				String []temp=questions.split("####");
				int num=1;
				for( String o :temp)
				{
					if(o.trim().length()>3){
					System.out.println("======="+num+"========================");
					
					//去掉1.0分
					o=o.trim();

					// 表达式对象
					Pattern p = Pattern.compile("\\(1\\.0分\\)", Pattern.MULTILINE | Pattern.COMMENTS);
					// 创建 Matcher 对象
					Matcher m = p.matcher(o);
					// 替换
					String newstring = m.replaceAll("");
					questionslist.add(newstring.trim());
					textArea_html.append(newstring.trim()+"\n");
					System.out.println(newstring.trim());
					num++;
					}
				}
				
				System.out.println("总计题目数量:"+questionslist.size());
				
			}
		});
		btnNewButton_4.setBounds(10, 109, 174, 23);
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("4.加载题目信息");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnalysisQuestion dialog = new AnalysisQuestion();
				dialog.setVisible(true);
			}
		});
		btnNewButton_5.setBounds(10, 169, 174, 23);
		panel.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("显示题目信息");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("单选题:"+AnalysisQuestion.single.size());
				System.out.println("多选题:"+AnalysisQuestion.multi.size());
				System.out.println("判断题:"+AnalysisQuestion.judge.size());
				System.out.println("填空题:"+AnalysisQuestion.inputs.size());
			}
		});
		btnNewButton_6.setBounds(749, 62, 174, 23);
		panel.add(btnNewButton_6);
		
		JButton button = new JButton("5.分析单选答案");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int start=1;
				int end=40;
				int num=1;
				for(int i=start;i<=end;i++)
				{
					String q=questionslist.get(i-1);
					String shortq=CountStringDis.getOnlyQuestion(q);
					Question resq=AnalysisQuestion.single.get(shortq);
					if(resq!=null){
						//System.out.println(num+"\t"+"题目:"+q+"Ans:"+resq.answer);
						System.out.println(num+"\t"+"题目:"+"Ans:"+resq.answer);
						}else
						{
							System.out.println(num+"\t"+"题目:"+q+"Ans:null");	
						}
					if(num%5==0)
					{
						System.out.println("=============================");
					}
					num++;
				}
				
			}
		});
		button.setBounds(268, 10, 125, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("6.分析多选答案");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("多选题");
				int start=41;
				int end=60;
				int num=1;
				for(int i=start;i<=end;i++)
				{
					String q=questionslist.get(i-1);
					String shortq=CountStringDis.getOnlyQuestion(q);
					Question resq=AnalysisQuestion.multi.get(shortq);
					if(resq!=null){
						System.out.println(num+"\t"+"题目:"+"Ans:"+resq.answer.trim());
						}else
						{
							System.out.println(num+"\t"+"题目:"+q+"Ans:null");	
						}
					if(num%5==0)
					{
						System.out.println("=============================");
					}
					num++;
				}
				
			}
		});
		button_1.setBounds(268, 62, 125, 23);
		panel.add(button_1);
		
		JButton button_2 = new JButton("7.分析判断答案");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("判断题答案");
				int start=61;
				int end=80;
				int num=1;
				for(int i=start;i<=end;i++)
				{
					String q=questionslist.get(i-1);
					String shortq=CountStringDis.getOnlyQuestion(q);
					Question resq=AnalysisQuestion.judge.get(shortq);
					if(resq!=null){
						System.out.println(num+"\t"+"题目:"+"Ans:"+resq.answer);
						}else
						{
							System.out.println(num+"\t"+"题目:"+q+"Ans:null");	
						}
					
					if(num%5==0)
					{
						System.out.println("=============================");
					}
					num++;
				
				}
				
				
			}
		});
		button_2.setBounds(268, 109, 125, 23);
		panel.add(button_2);
		
		JButton button_3 = new JButton("8.分析填空答案");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("填空题答案");
				int start=81;
				int end=100;
				int num=1;
				for(int i=start;i<=end;i++)
				{
					String q=questionslist.get(i-1);
					String shortq=CountStringDis.getOnlyQuestion(q);
					Question resq=AnalysisQuestion.inputs.get(shortq);
					if(resq!=null){
					System.out.println(num+"\t"+"题目:"+"Ans:"+resq.answer);
					}else
					{
						System.out.println(num+"\t"+"题目:"+q+"Ans:null");	
					}
					if(num%5==0)
					{
						System.out.println("=============================");
					}
					num++;
				}
				
				
			}
		});
		button_3.setBounds(268, 169, 125, 23);
		panel.add(button_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 307, 927, 191);
		panel.add(scrollPane);
		
		
		scrollPane.setViewportView(textArea_html);
		
		JButton btnNewButton_7 = new JButton("解析题目到List");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String [] ss=textArea_html.getText().split("\n");
				questionslist.clear();
				for(String s:ss)
				{
					if(s.trim().length()>5)
					questionslist.add(s);
				}
				
			 System.out.println("加载题目:"+questionslist.size());
			}
		});
		btnNewButton_7.setBounds(749, 128, 174, 23);
		panel.add(btnNewButton_7);
		
		JLabel lblNewLabel = new JLabel("调试功能");
		lblNewLabel.setBounds(744, 30, 54, 15);
		panel.add(lblNewLabel);
		
		JButton btnNewButton_2 = new JButton("附加：手工加载题目");
		btnNewButton_2.setBounds(10, 244, 174, 23);
		panel.add(btnNewButton_2);
		
		textField = new JTextField();
		textField.setBounds(221, 245, 577, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frmCcbui.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu menu = new JMenu("帮助");
		menuBar.add(menu);
	}
}
