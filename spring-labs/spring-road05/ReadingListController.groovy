@Controller
@RequestMapping("/)
class ReadingListController {
    String reader = "sevenluo"

    ReadingListRepository readingListRepository

    @RequestMapping(method = RequestMethod.GET)
    def readersBooks (Model model) {
        List<Video> readingList = readingListRepository.findByReader(reader)

        if(readingList) {
            model.addAttribute("videos", readingList)
        }

        "readingList"

    }

    @RequestMapping(method = RequestMethod.POST)
    def addToReadingList(Video video) {
        video.setReader(reader)
        readingListRepository.save(video)
        "redirect:/"
    }

}