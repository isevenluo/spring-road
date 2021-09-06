class JdbcReadingListRepository implements ReadingListRepository {
    @Autowired
    JdbcTemplate jdbc;

    List<Video> findByReader(String reader) {
        jdbc.query("select id,reader, isbn,title,author,description from Video where reader = ?",
        {rs, row -> 
            new Video(id:rs.getLong(1),
            reader: rs.getString(2),
            isbn: rs.getString(3),
            title: rs.getString(4),
            author: rs.getString(5),
            description: rs.getString(6))
        
        } as RowMapper, reader)
}