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
 * ����
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
		//ץȡ����
		
		try {
			
			Document doc = Jsoup.connect(url).timeout(10000).get();
			
			Elements items = doc.select(".grid_view .item");
			System.out.println(items.size());
		
			for(Element item : items)
			{
				
				Film film = new Film();
				String id = item.select(".pic em").first().text();           //����
				String title = item.select(".title").get(0).text();          //Ƭ��
				String poster = item.select("img").get(0).attr("src");       // ����
				String info = item.select(".bd p").first().text();
				String a[] = info.split("/");
				String b[] = info.split(":"); 
				String c[] = info.split(" ");
				String daoyan = (String) b[1].subSequence(0, b[1].length()-2);  //����
				String ratting = item.select(".star span").last().text();    //��������
				String star = item.select(".rating_num").first().text();     //����
				String quote = item.select(".quote .inq").text();            //��Ҫ��Ϣ
				String starttime = (a[a.length - 3].substring(a[a.length-3].length()-5,a[a.length-3].length()-1)); //��ӳʱ��
				
				film.setId(Integer.parseInt(id));                         //�������
				film.setTitle(title);                                     //���Ƭ��          
				film.setPoster(poster);                                   //��ú���
				try {film.setQuote(quote); }catch (Exception e) {}        //��ø�Ҫ
				film.setDirector(daoyan);                              //��õ���
				film.setRatting(Integer.parseInt(ratting.substring(0, ratting.length()-3))); //�����������
				film.setStar(Double.parseDouble(star));                   //�������
				if(c[6].length() < 2 )
					film.setMainactor(c[7].replace(".", ""));
			    else
					film.setMainactor(c[6].replace(".", ""));             //�������
				film.setCountry(a[a.length-2]);                           //��ù���
				try {film.setTimestart(Integer.parseInt(starttime));}catch (NumberFormatException e) {}           //�����ӳʱ��
				
				System.out.println(film);
				filmList.add(film);
 		}
				// �洢�����ݿ�
				
				// ��������
				SqlSessionFactory factory = 
						new SqlSessionFactoryBuilder().build(
								new FileReader("config.xml"));
				
				SqlSession session = factory.openSession();
				
				// ����˽ӿڵľ���ʵ�֣����䣩
				FilmMapper mapper = session.getMapper(FilmMapper.class);
				
				for (Film f : filmList) {
					mapper.insert(f);
				}
				session.commit();
				session.close();
				System.out.println("�洢�ɹ�");
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

				