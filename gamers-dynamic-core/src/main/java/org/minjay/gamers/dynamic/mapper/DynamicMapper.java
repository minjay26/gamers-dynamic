package org.minjay.gamers.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.minjay.gamers.dynamic.domain.Dynamic;

@Mapper
public interface DynamicMapper {

    @Select("select * from dynamics where id = #{id}")
    Dynamic findById(@Param("id") Long id);

    Dynamic findAll();
}
