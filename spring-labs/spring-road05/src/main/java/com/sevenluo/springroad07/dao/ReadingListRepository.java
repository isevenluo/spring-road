package com.sevenluo.springroad07.dao;



import com.sevenluo.springroad07.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<Video,Long> {

    List<Video> findByReader(String reader);
}
