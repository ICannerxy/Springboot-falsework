package com.byavs.frame.core.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import javax.validation.Valid;
import java.util.List;

public interface CommonMapper<T, V> {
    long countByExample(V example);

    int deleteByExample(V example);

    int deleteByPrimaryKey(String id);

    int insert(@Valid T record);

    int insertSelective(@Valid T record);


    int insertBatch(@Valid List<T> list);

    List<T> selectByExample(V example);

    T selectByPrimaryKey(String id);

    int updateByExampleSelective(@Valid @Param("record") T record, @Param("example") V example);

    int updateByExample(@Valid @Param("record") T record, @Param("example") V example);

    int updateByPrimaryKeySelective(@Valid T record);

    int updateByPrimaryKey(@Valid T record);
}