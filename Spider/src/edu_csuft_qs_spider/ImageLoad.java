package edu_csuft_qs_spider;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImageLoad implements Runnable {

	private Film film;                       //��Ӱ
 	 
	public ImageLoad(Film film) {            //���췽��
		this.film = film;
	}

	@Override
	public void run() {
		File path = new File("pic");           //���ɴ洢ͼƬ���ļ�
		if (!path.exists())
			path.mkdir();                      //������ļ����ڣ���ɾ��
		String name = String.format("%03d_%s.jpg", film.getId(), film.getTitle().split(" ")[0]);  //��id��������ΪͼƬ����
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path, name)));) {

			byte[] data = new OkHttpClient.Builder()
					.connectTimeout(60, TimeUnit.SECONDS)
					.readTimeout(60, TimeUnit.SECONDS)
					.writeTimeout(60, TimeUnit.SECONDS)
					.build().newCall(new Request.Builder().url(film.getPoster()).build()).execute().body().bytes();  

			bos.write(data);           
			bos.close();				
			System.out.println(Thread.currentThread().getName() + " ���� " + name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

