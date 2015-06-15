import Foundation
import Accounts
import Social
import ReactiveCocoa


class WordService {

    init() {}

    func getWords(month: Int, year: Int) -> SignalProducer<WordResponse, NSError> {
        let words: [Word] = [
            Word(id: 1, word: "Cat", day: 1, month: 1, year: 2015, imageUrl: "http://truestorieswithgill.com/wp-content/uploads/2013/09/20130915-190532.jpg"),
            Word(id: 2, word: "Dog", day: 2, month: 1, year: 2015, imageUrl:"https://media3.giphy.com/media/10we3R8dLZQ7hS/200_s.gif"),
            Word(id: 3, word: "Bacon", day: 3, month: 1, year: 2015, imageUrl:"http://rs1img.memecdn.com/Bacon_o_137966.jpg"),
            Word(id: 4, word: "Train", day: 4, month: 1, year: 2015, imageUrl:"http://rs2img.memecdn.com/nyan-train_o_289564.jpg"),
            Word(id: 5, word: "Onion", day: 5, month: 1, year: 2015, imageUrl:"http://rs1img.memecdn.com/sinister-onion_o_2425497.jpg")]

        return SignalProducer(value: WordResponse(values: words))
            |> delay(1.0, onScheduler: QueueScheduler(queue: dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_LOW, 0), name: ""))
    }

}
