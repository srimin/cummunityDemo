package com.srimin.demo01springbootquickstart.mapper;

import com.srimin.demo01springbootquickstart.dto.PostsDTO;
import com.srimin.demo01springbootquickstart.model.Posts;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostsMapper {
    @Insert("insert into posts (title,content,gmt_create,gmt_modified,creator,tag) values (#{title},#{content},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Posts posts);

    @Select("select * from posts limit #{page},#{range}")
    List<Posts> list(@Param("page")Integer page, @Param("range")Integer range);

    @Select("select count(1) from posts ")
    Integer count();
    @Select("select * from posts where creator = #{userId} limit #{page},#{range}")
    List<Posts> listByUserId(@Param("userId")Integer userId,@Param("page")Integer page, @Param("range")Integer range);

    @Select("select count(1) from posts where creator = #{userId} ")
    Integer countByUserId(@Param("userId")Integer userId);

    @Select("select * from posts where id = #{id}")
    Posts getById(@Param("id")Integer Id);

    @Update("update posts set gmt_modified = #{gmtModified},title = #{title},content = #{content},tag = #{tag} where id = #{id}")
    void update(Posts posts);
}
