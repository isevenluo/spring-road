package com.sevenluo.springroad07.service;

import com.sevenluo.springroad07.entity.Video;

import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-10-04 22:32
 **/
public interface VideoService {

    List<Video> findVideoByReader(String reader);

    void saveVideo(Video video);
}
