package com.sevenluo.springroad06.service.impl;

import com.sevenluo.springroad06.dao.ReadingListRepository;
import com.sevenluo.springroad06.entity.Video;
import com.sevenluo.springroad06.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-10-04 22:33
 **/
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private ReadingListRepository readingListRepository;

    @Override
    @Cacheable(value = "demoCache", key = "'video_' + #reader")
    public List<Video> findVideoByReader(String reader) {
        System.out.println("从DB中获取数据");
        List<Video> byReader = readingListRepository.findByReader(reader);
        return byReader;
    }

    @Override
    @CacheEvict(value = "demoCache",key = "'video_' + #video.reader")
    public void saveVideo(Video video) {
        readingListRepository.save(video);
    }
}
