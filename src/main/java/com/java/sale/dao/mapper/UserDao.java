package com.java.sale.dao.mapper;

import com.java.sale.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 曾伟
 * @date 2019/10/24 20:58
 */
@Mapper
public interface UserDao {
    @Select("select id,name from user where id=#{id}")
    public User getUserById(@Param("id") int id);
}
