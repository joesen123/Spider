package edu.csuft.qs.spider;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 * 爬虫
 * @author 12998
 *
 */


public class Spider_sql implements Runnable{
	
	String url;
	List<Film> filmList = new ArrayList<>();
	public Spider_sql(String url) {
		this.url = url;
	}

	public void run() {
		// TODO Auto-generated method stub
		//抓取数据
		
		try {
			
			Document doc = Jsoup.connect(url).timeout(10000).get();
			
			Elements items = doc.select(".grid_view .item");
			System.out.println(items.size());
		
			for(Element item : items)
			{
				
				Film film = new Film();
				String id = item.select(".pic em").first().text();           //排名
				String title = item.select(".title").get(0).text();          //片名
				String poster = item.select("img").get(0).attr("src");       // 海报
				String info = item.select(".bd p").first().text();
				String a[] = info.split("/");
				String b[] = info.split(":"); 
				String c[] = info.split(" ");
				String daoyan = (String) b[1].subSequence(0, b[1].length()-2);  //导演
				String ratting = item.select(".star span").last().text();    //评价人数
				String star = item.select(".rating_num").first().text();     //评分
				String quote = item.select(".quote .inq").text();            //概要信息
				String starttime = (a[a.length - 3].substring(a[a.length-3].length()-5,a[a.length-3].length()-1)); //上映时间
				
				film.setId(Integer.parseInt(id));                         //获得排名
				film.setTitle(title);                                     //获得片名          
				film.setPoster(poster);                                   //获得海报
				try {film.setQuote(quote); }catch (Exception e) {}        //获得概要
				film.setDirector(daoyan);                              //获得导演
				film.setRatting(Integer.parseInt(ratting.substring(0, ratting.length()-3))); //获得评价人数
				film.setStar(Double.parseDouble(star));                   //获得评分
				if(c[6].length() < 2 )
					film.setMainactor(c[7].replace(".", ""));
			    else
					film.setMainactor(c[6].replace(".", ""));             //获得主演
				film.setCountry(a[a.length-2]);                           //获得国家
				try {film.setTimestart(Integer.parseInt(starttime));}catch (NumberFormatException e) {}           //获得上映时间
				
				System.out.println(film);
				filmList.add(film);
 		}
				// 存储到数据库
				
				// 建立连接
				SqlSessionFactory factory = 
						new SqlSessionFactoryBuilder().build(
								new FileReader("config.xml"));
				
				SqlSession session = factory.openSession();
				
				// 获得了接口的具体实现（反射）
				FilmMapper mapper = session.getMapper(FilmMapper.class);
				
				for (Film f : filmList) {
					mapper.insert(f);
				}
				session.commit();
				session.close();
				System.out.println("存储成功");
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

				