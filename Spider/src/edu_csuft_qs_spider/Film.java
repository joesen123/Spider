package edu_csuft_qs_spider;



/**
 * 
 * ӰƬ��Ϣ ��ʵ���ࣩ
 * @author 12998
 *
 */
public class Film implements Comparable<Film> {
	
	//���ע���ֶ�
	private int id;           //����
	private String title ;    //Ƭ��
	private String director ; //����
	private String mainactor; //����
	private double star;      //����
	private int ratting  ;    //��������
	private String poster;    //����
	private String quote;     //��Ҫ
	private String country;   //����
	private int timestart;    //��ӳ����
	
	public Film()
	{
		
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getMainactor() {
		return mainactor;
	}

	public void setMainactor(String mainactor) {
		this.mainactor = mainactor;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}




	public Double getStar() {
		return star;
	}


	public void setStar(double star) {
		this.star = star;
	}


	public int getRating() {
		return ratting;
	}


	public void setRatting(int ratting) {
		this.ratting = ratting;
	}


	public String getPoster() {
		return poster;
	}


	public void setPoster(String poster) {
		this.poster = poster;
	}


	public String getQuote() {
		return quote;
	}


	public void setQuote(String quote) {
		this.quote = quote;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}

	public int getTimestart() {
		return timestart;
	}

	public void setTimestart(int timestart) {
		this.timestart = timestart;
	}


	@Override
	public String toString() {
		return "Film { \n���� :" + id + ", \n���� :" + title + ", \n����:" + director + "\n����:" + mainactor + "\n��ӳ����"
				+ country +"\n��ӳʱ��:" +timestart + ", \n��������" + ratting + ",\n���� :"+ star 
				+", \n��Ҫ :" + quote+ ", \n������ַ:" + poster + " \n     }";
	}

	@Override
	public int compareTo(Film o) {
		return id - o.id;
	}

	
}

