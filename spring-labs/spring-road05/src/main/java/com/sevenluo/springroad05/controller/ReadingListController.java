package com.sevenluo.springroad05.controller;


import com.sevenluo.springroad05.dao.ReadingListRepository;
import com.sevenluo.springroad05.entity.Video;
import com.sevenluo.springroad05.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ReadingListController {

    @Resource
    private ReadingListRepository readingListRepository;

    @Autowired
    private VideoService videoService;


    @GetMapping(value = "/{viewer}")
    public String readersVideos(@PathVariable("viewer") String viewer, Model model) {
        List<Video> videoList = videoService.findVideoByReader(viewer);
        if (videoList != null) {
            model.addAttribute("videos", videoList);
        }
        return "readingList";
    }

    @PostMapping(value = "/{viewer}")
    public String addToReadingList(@PathVariable("viewer") String viewer, Video video) {
        video.setReader(viewer);
        videoService.saveVideo(video);
        return "redirect:/{viewer}";
    }


}
