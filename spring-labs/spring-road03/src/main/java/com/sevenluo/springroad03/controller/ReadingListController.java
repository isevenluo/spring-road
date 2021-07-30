package com.sevenluo.springroad03.controller;

import com.sevenluo.springroad03.dao.ReadingListRepository;
import com.sevenluo.springroad03.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/viewerList")
public class ReadingListController {

    @Autowired
    private ReadingListRepository readingListRepository;

    @GetMapping(value = "/{viewer}")
    public String readersVideos(@PathVariable("viewer") String viewer, Model model) {
        List<Video> videoList = readingListRepository.findByReader(viewer);
        if (videoList != null) {
            model.addAttribute("videos", videoList);
        }
        return "readingList";
    }

    @PostMapping(value = "/{viewer}")
    public String addToReadingList(@PathVariable("viewer") String viewer, Video video) {
        video.setReader(viewer);
        readingListRepository.save(video);
        return "redirect:/{viewer}";
    }
}
