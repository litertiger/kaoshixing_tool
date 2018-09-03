package com.ccb.kaoshi.ui;

import java.awt.EventQueue;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.ccb.kaoshi.model.Question;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class KaoshiMainWindowNew {

	public static ArrayList<String> questionslist=new ArrayList<String>();
	JTextArea textArea_html = new JTextArea();
	private JFrame frmCcbui;
	public  BrowserThread bt;
	public MiniAnswerWin maw=null;
    public JTree DOMtree;
    public DefaultMutableTreeNode rootNode;
    private TrayIcon trayIcon;//托盘图标 new
    private SystemTray systemTray;//系统托盘  //
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KaoshiMainWindowNew window = new KaoshiMainWindowNew();
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
	public KaoshiMainWindowNew() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frmCcbui = new JFrame();
		frmCcbui.setTitle("Mini答题");
		frmCcbui.setBounds(100, 100, 208, 272);
		frmCcbui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCcbui.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//托盘
		systemTray = SystemTray.getSystemTray();//获得系统托盘的实例
		try {
            trayIcon = new TrayIcon(ImageIO.read(new File("play.png")));
            systemTray.add(trayIcon);//设置托盘的图标，0.gif与该类文件同一目录
        }
        catch (IOException e1) {e1.printStackTrace();}
        catch (AWTException e2) {e2.printStackTrace();}
		trayIcon.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        if(e.getClickCount() == 2)//双击托盘窗口再现
                         
                        frmCcbui.setVisible(true);
                        if(maw!=null)
        				{
        					//maw=new MiniAnswerWin();
        					maw.setVisible(true);
        				}
                    }
                });     
		
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
		btnNewButton.setBounds(10, 43, 174, 23);
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
		btnNewButton_4.setBounds(10, 76, 174, 23);
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("4.加载题目信息");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnalysisQuestion dialog = new AnalysisQuestion();
				dialog.setVisible(true);
			}
		});
		btnNewButton_5.setBounds(10, 109, 174, 23);
		panel.add(btnNewButton_5);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 175, 174, 48);
		panel.add(scrollPane);
		
		
		scrollPane.setViewportView(textArea_html);
		
		JButton btnNewButton_8 = new JButton("显示迷你窗口");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(maw==null)
				{
					maw=new MiniAnswerWin();
					maw.setVisible(true);
				}
				else
				{
					maw.setVisible(true);
				}
				
				frmCcbui.setVisible(false);
				 
			}
		});
		btnNewButton_8.setBounds(10, 142, 174, 23);
		panel.add(btnNewButton_8);
	}
}
