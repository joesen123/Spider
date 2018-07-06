package edu_csuft_qs_spider;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * ����
 * @author 12998
 *
 */


public class Spider implements Runnable{

	private String url;
	/**
	 * �洢����ӰƬ
	 */
	private List<Film> films;
	/**
	 * ���췽��
	 * @param url
	 */
	public Spider(String url)
	{
		this.url = url;
	}
	public Spider(String url, List<Film> films) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.films = films;
	}
		/**
		 * ִ��
		 * 
		 */
	public void run() {
		// TODO Auto-generated method stub
		//ץȡ����
		
		try {
			
			Document doc = Jsoup.connect(url).timeout(10000).get();
			
			Elements items = doc.select(".grid_view .item");
			
		
			for(Element item : items)
			{
				Film film = new Film();
				String title = item.select(".title").get(0).text();                          //Ƭ��
				film.setTitle(title);
				String poster = item.select("img").get(0).attr("src");                      // ����
				film.setPoster(poster);
				try {
					String quote = item.select(".quote .inq").text();                       //��Ҫ��Ϣ
					film.setQuote(quote);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					film.setQuote(null);                                 
				}            
				String info = item.select(".bd p").first().text();
				String b[] = info.split(":");
				String mainactor = (String) b[1].subSequence(0, b[1].length()-2);
				film.setDirector(mainactor);
				String c[] = info.split(" ");
 				
				if(c[6].length() < 2 )
						film.setMainactor(c[7]);
				else
						film.setMainactor(c[6]);
				String ratting = item.select(".star span").last().text();                    
				film.setRatting(Integer.parseInt(ratting.substring(0, ratting.length()-3))); //��������
				String star = item.select(".rating_num").first().text();                     //����
				film.setStar(Double.parseDouble(star));   
				String id = item.select(".pic em").first().text();                           //����
				film.setId(Integer.parseInt(id));
				String a[] = info.split("/");
				film.setCountry(a[a.length-2]);
				String starttime = (a[a.length - 3].substring(a[a.length-3].length()-5,a[a.length-3].length()-1));
				film.setTimestart(Integer.parseInt(starttime));
				
				
				
				films.add(film);
				System.out.println(Thread.currentThread().getName() + "���� :" + id);
				
			}
			System.out.println(Thread.currentThread() + "����");
		
		
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
