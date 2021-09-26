CREATE TABLE `video` (
                         `id` bigint(20) NOT NULL,
                         `author` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                         `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                         `isbn` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                         `reader` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                         `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE `hibernate_sequence` (
    `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert into video(id, author, description, isbn, reader, title) values ('1','seven','测试flyway','1','seven','test');