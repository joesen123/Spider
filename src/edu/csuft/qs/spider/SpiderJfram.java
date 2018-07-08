package edu.csuft.qs.spider;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.gson.Gson;



public class SpiderJfram extends JFrame{
	
	private JButton button_start;
	private JButton button_insert;
	private static void writeDate1(List<Film> films)   //将film写入json文件
	{
		
		Collections.sort(films);
		String json = new Gson().toJson(films);
		try (FileWriter out = new FileWriter("Movie.json")) {
			out.write(json);
		} catch (Exception e) {
		}
		ExecutorService pool = Executors.newFixedThreadPool(8);
		for (Film film : films) {
			pool.execute(new ImageLoad(film));
		}
		pool.shutdown();
	}

	public SpiderJfram() {
		
		super("Joeson的爬虫界面");
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		ImageIcon imageIcon = new ImageIcon("C:\\Users\\12998\\eclipse-workspace\\Spider\\ant.jpg");
		
		JLabel l = new JLabel(imageIcon);
		this.add(l);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JPanel pan1 = new JPanel();
		JLabel l1 = new JLabel("下面请开始你的操作：");
		pan1.add(l1);
		panel.add(pan1);
		JPanel pan2 = new JPanel();
		button_start = new JButton("启动程序");
		button_start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String url = "https://movie.douban.com/top250";   //第一页

				List<Film> films = Collections.synchronizedList(new LinkedList<>());   //
				ExecutorService pool = Executors.newFixedThreadPool(5);   // 5个线程的线程池
				
				pool.execute(new Spider(url, films));
				
				for (int i = 1; i < 10; i++) {
					url = String.format("https://movie.douban.com/top250?start=%d&filter=", 25 * i); //10页
					pool.execute(new Spider(url, films));    //运行线程
				}
				
				pool.shutdown();
				
				while(true)
				{
					if(pool.isTerminated()) {
						writeDate1(films);
						break;
					}
							
					else
					{
						try {
							Thread.sleep(1000);
							}catch(InterruptedException e)
						{
								e.printStackTrace();
						}
					}
					
				}
				
				
			}
				
		});
		button_insert = new  JButton("导入数据库");
		button_insert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String address[] = new String[10];
				for(int i=0; i<10; i++)
				{
					address[i] = "https://movie.douban.com/top250?start=" + i*25 + "&filter=" ;
				}
				
				ExecutorService pool_sql = Executors.newFixedThreadPool(5); 
				for(int i = 0; i<10; i++)
				{
					pool_sql.execute(new Spider_sql(address[i]));
				}
				pool_sql.shutdown();
			}
		});
		pan2.add(button_start);		
		pan2.add(button_insert);
		panel.add(pan2);
		this.add(panel,BorderLayout.SOUTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
		
	public static void main(String[] args) {
		new SpiderJfram();
	}

}
