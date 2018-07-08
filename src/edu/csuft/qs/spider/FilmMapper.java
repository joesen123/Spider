package edu.csuft.qs.spider;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

// ×¢½â
@Mapper
public interface FilmMapper {

	//ta_1
	@Insert("insert into films(id,title,star,quote) values(#{id},#{title},#{star},#{quote})")
	void insert(Film m);
	// ·´Éä£¨ºÚÄ§·¨£©
	@Select("select * from films where id=#{id}")
	Film load(int id);

	@Select("select * from films")
	List<Film> find();

}
