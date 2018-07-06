package edu_csuft_qs_spider;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImageLoad implements Runnable {

	private Film film;                       //电影
 	 
	public ImageLoad(Film film) {            //构造方法
		this.film = film;
	}

	@Override
	public void run() {
		File path = new File("pic");           //生成存储图片的文件
		if (!path.exists())
			path.mkdir();                      //如果该文件存在，则删除
		String name = String.format("%03d_%s.jpg", film.getId(), film.getTitle().split(" ")[0]);  //以id和中文名为图片命名
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path, name)));) {

			byte[] data = new OkHttpClient.Builder()
					.connectTimeout(60, TimeUnit.SECONDS)
					.readTimeout(60, TimeUnit.SECONDS)
					.writeTimeout(60, TimeUnit.SECONDS)
					.build().newCall(new Request.Builder().url(film.getPoster()).build()).execute().body().bytes();  

			bos.write(data);           
			bos.close();				
			System.out.println(Thread.currentThread().getName() + " 下载 " + name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

