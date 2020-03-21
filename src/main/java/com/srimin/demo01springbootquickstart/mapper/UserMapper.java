package com.srimin.demo01springbootquickstart.mapper;

import com.srimin.demo01springbootquickstart.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);
    @Update("update user set token = #{token},gmt_modified = #{gmtModified},bio = #{bio},avatar_url = #{avatarUrl},name = #{name} where account_id = #{accountId}")
    void update(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token")String token);
    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId")String accountId);
    @Select("select * from user where id = #{id}")
    User findById(@Param("id")Integer id);
}
