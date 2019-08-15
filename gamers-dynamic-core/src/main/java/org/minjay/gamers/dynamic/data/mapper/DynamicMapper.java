package org.minjay.gamers.dynamic.data.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.minjay.gamers.dynamic.data.domain.Dynamic;

@Mapper
public interface DynamicMapper {

    @Select("select * from dynamics where id = #{id}")
    Dynamic findById(@Param("id") Long id);

    Dynamic findAll();

    @Insert({"INSERT INTO dynamics(content,user_id, created_date, last_modified_date) VALUE (#{content}, #{userId}, #{createdDate}, #{lastModifiedDate})"})
    void save(Dynamic dynamic);
}
