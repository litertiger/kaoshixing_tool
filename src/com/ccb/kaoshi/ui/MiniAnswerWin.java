package com.ccb.kaoshi.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ccb.kaoshi.model.Question;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MiniAnswerWin extends JDialog {
	
	public JDialog thisdialog=null;
    public 	JTextArea textArea = new JTextArea();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiniAnswerWin dialog = new MiniAnswerWin();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public MiniAnswerWin() {
		thisdialog=this;
	   Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Dimension scrnsize = toolkit.getScreenSize();
	   //System.out.println ("Screen size : " + scrnsize.width + " * " + scrnsize.height);
		//int width=600;
		//if(scrnsize.width -100)
		setBounds(50, scrnsize.height-200, 900, 80);
		//this.getContentPane().setBackground(new Color(150, 150, 150)); // 设置窗体背景颜色
		this.setAlwaysOnTop(true); //窗体最顶层显示
		this.setUndecorated(true);// 取消窗体修饰效果
		this.getContentPane().setLayout(null);// 窗体使用绝对布局
		
		JButton button = new JButton("单");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				int start=1;
				int end=40;
				int num=1;
				for(int i=start;i<=end;i++)
				{
					String q=KaoshiMainWindowNew.questionslist.get(i-1);
					String shortq=CountStringDis.getOnlyQuestion(q);
					Question resq=AnalysisQuestion.single.get(shortq);
					if(resq!=null){
						//System.out.println(num+"\t"+"题目:"+q+"Ans:"+resq.answer);
						    textArea.append(num+" "+"答案:"+resq.answer+"\t");
						}else
						{
							textArea.append(num+" "+"答案:"+"null"+"\t");	
						}
					if(num%5==0)
					{
						textArea.append("\n");
						//System.out.println("=============================");
					}
					num++;
				}
				
			}
		});
		button.setBounds(10, 10, 59, 23);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("多");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				int start=41;
				int end=60;
				int num=1;
				for(int i=start;i<=end;i++)
				{
					String q=KaoshiMainWindowNew.questionslist.get(i-1);
					String shortq=CountStringDis.getOnlyQuestion(q);
					Question resq=AnalysisQuestion.multi.get(shortq);
					if(resq!=null){
						 textArea.append(num+" "+"答案:"+resq.answer.trim()+"\t");
						}else
						{
							 textArea.append(num+" "+"答案:"+"null"+"\t");	
						}
					if(num%5==0)
					{
					    textArea.append("\n");
					}
					num++;
				}
				
			}
			
		});
		button_1.setBounds(73, 10, 59, 23);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton("判");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				int start=61;
				int end=80;
				int num=1;
				for(int i=start;i<=end;i++)
				{
					String q=KaoshiMainWindowNew.questionslist.get(i-1);
					String shortq=CountStringDis.getOnlyQuestion(q);
					Question resq=AnalysisQuestion.judge.get(shortq);
					if(resq!=null){
						 textArea.append(num+" "+"答案:"+resq.answer+"\t");
						}else
						{
							 textArea.append(num+" "+"答案:"+"null"+"\t");	
						}
					
					if(num%5==0)
					{
						 textArea.append("\n");
					}
					num++;
				
				}
				
				
			}
			
		});
		button_2.setBounds(10, 43, 59, 23);
		getContentPane().add(button_2);
		
		JButton button_3 = new JButton("空");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				int start=81;
				int end=100;
				int num=1;
				for(int i=start;i<=end;i++)
				{
					String q=KaoshiMainWindowNew.questionslist.get(i-1);
					String shortq=CountStringDis.getOnlyQuestion(q);
					Question resq=AnalysisQuestion.inputs.get(shortq);
					if(resq!=null){
						 textArea.append(num+" "+"答案:"+resq.answer+"\t");
					}else
					{
						 textArea.append(num+" "+"答案:"+"null"+"\t");	
					}
					if(num%5==0)
					{
						 textArea.append("\n");
					}
					num++;
				}
				
			}
		});
		button_3.setBounds(73, 43, 59, 23);
		getContentPane().add(button_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(179, 9, 711, 61);
		getContentPane().add(scrollPane);
		
	
		scrollPane.setViewportView(textArea);
		
		JButton btnH = new JButton("H");
		btnH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisdialog.setVisible(false);
			}
		});
		btnH.setBounds(142, 10, 20, 56);
		getContentPane().add(btnH);
	}
}
