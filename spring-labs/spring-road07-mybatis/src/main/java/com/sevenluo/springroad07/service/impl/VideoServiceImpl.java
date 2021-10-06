package com.sevenluo.springroad07.service.impl;

import com.sevenluo.springroad07.dao.VideoAnnotationMapper;
import com.sevenluo.springroad07.dao.VideoMapper;
import com.sevenluo.springroad07.entity.Video;
import com.sevenluo.springroad07.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-10-06 12:02
 **/
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findByReader(String reader) {
        List<Video> videoByReader = videoMapper.findVideoByReader(reader);
        return videoByReader;
    }

    @Override
    public void saveVideo(Video video) {
        videoMapper.saveVideo(video);
    }
}
