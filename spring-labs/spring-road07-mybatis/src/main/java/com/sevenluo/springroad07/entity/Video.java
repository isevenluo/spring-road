package com.sevenluo.springroad07.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sevenluo
 */
@Data
public class Video implements Serializable {


    private Long id;

    private String reader;

    private String isbn;

    private String title;

    private String author;

    private String description;

}
