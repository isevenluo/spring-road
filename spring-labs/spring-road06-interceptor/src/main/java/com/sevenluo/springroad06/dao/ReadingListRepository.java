package com.sevenluo.springroad06.dao;



import com.sevenluo.springroad06.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<Video,Long> {

    List<Video> findByReader(String reader);
}
