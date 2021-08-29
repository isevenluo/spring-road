package com.sevenluo.springroad02.dao;



import com.sevenluo.springroad02.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<Video,Long> {

    List<Video> findByReader(String reader);
}
