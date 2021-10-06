package com.sevenluo.springroad07.dao;

import com.sevenluo.springroad07.entity.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: 纯注解的方式写SQL，不需要XML文件
 * @author: 程序员七哥
 * @create: 2021-10-06 12:08
 **/
@Mapper
public interface VideoAnnotationMapper {

    @Select("select * from video where reader = #{reader}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "author", column = "author"),
            @Result(property = "description", column = "description")
    })
    List<Video> findVideoByReader(String reader);

    @Insert("insert into video(author, description, isbn, reader, title) VALUES (#{author},#{description}," +
            "#{isbn},#{reader},#{title})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void saveVideo(Video video);
}
