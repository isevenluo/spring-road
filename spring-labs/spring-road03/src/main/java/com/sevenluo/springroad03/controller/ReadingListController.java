package com.sevenluo.springroad03.controller;

import com.sevenluo.springroad03.config.MyProperties;
import com.sevenluo.springroad03.dao.ReadingListRepository;
import com.sevenluo.springroad03.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ReadingListController {

    @Resource
    private ReadingListRepository readingListRepository;

    @Resource
    private MyProperties myProperties;

    @GetMapping(value = "/viewerList/{viewer}")
    public String readersVideos(@PathVariable("viewer") String viewer, Model model) {
        List<Video> videoList = readingListRepository.findByReader(viewer);
        if (videoList != null) {
            model.addAttribute("videos", videoList);
        }
        return "readingList";
    }

    @PostMapping(value = "/viewerList/{viewer}")
    public String addToReadingList(@PathVariable("viewer") String viewer, Video video) {
        video.setReader(viewer);
        readingListRepository.save(video);
        return "redirect:/viewerList/{viewer}";
    }


}









