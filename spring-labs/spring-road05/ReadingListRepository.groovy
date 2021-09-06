interface ReadingListRepository {
    List<Video> findByReader(String reader)
    void save(Video video)

}