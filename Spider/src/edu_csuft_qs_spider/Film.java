package edu_csuft_qs_spider;



/**
 * 
 * 影片信息 （实体类）
 * @author 12998
 *
 */
public class Film implements Comparable<Film> {
	
	//你关注的字段
	private int id;           //排名
	private String title ;    //片名
	private String director ; //导演
	private String mainactor; //主演
	private double star;      //评分
	private int ratting  ;    //评分人数
	private String poster;    //海报
	private String quote;     //概要
	private String country;   //国家
	private int timestart;    //上映日期
	
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
		return "Film { \n排名 :" + id + ", \n标题 :" + title + ", \n导演:" + director + "\n主演:" + mainactor + "\n上映国家"
				+ country +"\n上映时间:" +timestart + ", \n评价人数" + ratting + ",\n评分 :"+ star 
				+", \n概要 :" + quote+ ", \n海报地址:" + poster + " \n     }";
	}

	@Override
	public int compareTo(Film o) {
		return id - o.id;
	}

	
}

