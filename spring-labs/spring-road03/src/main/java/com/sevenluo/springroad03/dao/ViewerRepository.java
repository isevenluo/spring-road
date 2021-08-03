package com.sevenluo.springroad03.dao;

import com.sevenluo.springroad03.entity.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewerRepository extends JpaRepository<Viewer,String> {

    Viewer findViewerByUsername(String username);
}
