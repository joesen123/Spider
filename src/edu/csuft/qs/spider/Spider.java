package edu.csuft.qs.spider;



import java.io.IOException;
import java.util.List;
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
	public static String a[] = new String[250];
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
			
		
			for(Element item:items)
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
				try {film.setTimestart(Integer.parseInt(starttime));}catch (NumberFormatException e) {}       //�����ӳʱ��
				
				
				films.add(film);
				System.out.println("�Ի�ȡ��Ϣ��");
				System.out.println(film.toString());
				
			
				
			}
			
			
			
		
		
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
