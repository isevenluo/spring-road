package com.sevenluo.springroad05.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author sevenluo
 */
@Entity
@Data
public class Video {

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.AUTO) // 值是自动生成的
    private Long id;

    private String reader;

    private String isbn;

    private String title;

    private String author;

    private String description;

}
