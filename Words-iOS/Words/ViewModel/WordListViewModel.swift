import Foundation
import ReactiveCocoa

class WordListViewModel {

    let isLoading = MutableProperty<Bool>(false)
    let words = MutableProperty<[WordViewModel]>([WordViewModel]())

    private let wordService: WordService
  
    init(wordService: WordService) {

        self.wordService = wordService

        // retrieve the words, create a view model for each words, and update 
        // the overall view model
        self.wordService.getWords(1, year: 2015)
            |> on(started: { self.isLoading.put(true) })
            |> flatMap(FlattenStrategy.Latest, { SignalProducer(values: $0.words) })
            |> map({ WordViewModel(word: $0)})
            |> collect
            |> observeOn(QueueScheduler.mainQueueScheduler)
            |> start(next: {
                wordViewModelList in
                self.isLoading.put(false)
                self.words.put(wordViewModelList)
            }, error: {
                self.isLoading.put(false)
                println("Error \($0)")
            })
    }
}
