package com.sevenluo.springroad03;

import com.sevenluo.springroad03.dao.ReadingListRepository;
import com.sevenluo.springroad03.entity.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class SpringRoad03ApplicationTests {

    @Autowired
    private ReadingListRepository readingListRepository;

    @Test
    void testDao() {
        List<Video> videos = readingListRepository.findByReader("sevenluo");
        assertEquals(Collections.emptyList(),videos);
    }

}
