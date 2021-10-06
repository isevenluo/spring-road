package com.sevenluo.springroad07.dao;

import com.sevenluo.springroad07.entity.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-10-06 11:31
 **/
@Mapper
public interface VideoMapper {

    List<Video> findVideoByReader(String reader);

    void saveVideo(Video video);
}
