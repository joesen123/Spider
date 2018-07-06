package edu_csuft_qs_spider;

import java.io.FileWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.gson.Gson;
/**
 * �������������
 * 
 * @author 12998
 *
 */
public class App {

	public static void main(String[] args) {
		

		String url = "https://movie.douban.com/top250";   //��һҳ

		List<Film> films = Collections.synchronizedList(new LinkedList<>());   //
		ExecutorService pool = Executors.newFixedThreadPool(5);   // 5���̵߳��̳߳�
		
		pool.execute(new Spider(url, films));
		
		for (int i = 1; i < 10; i++) {
			url = String.format("https://movie.douban.com/top250?start=%d&filter=", 25 * i); //10ҳ
			pool.execute(new Spider(url, films));    //�����߳�
		}
		
		pool.shutdown();
		
		while(true)
		{
			if(pool.isTerminated()) {
				writeDate(films);
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
	private static void writeDate(List<Film> films)
	{
		System.out.println(films.size());
		Collections.sort(films);
		String json = new Gson().toJson(films);
		try (FileWriter out = new FileWriter("Movie.json")) {
			out.write(json);
			System.out.println(" �����ļ����");
		} catch (Exception e) {
		}

		ExecutorService pool = Executors.newFixedThreadPool(8);
		for (Film film : films) {
			pool.execute(new ImageLoad(film));
		}
		pool.shutdown();

	}

}
