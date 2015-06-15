import Foundation
import ReactiveCocoa

class WordViewModel {

    let wordTitle: ConstantProperty<String>
    let day: ConstantProperty<Int>
    let month: ConstantProperty<Int>
    let year: ConstantProperty<Int>
    let imageUrl: ConstantProperty<String>

    private let word: Word
  
    init (word: Word) {
        self.word = word
        self.wordTitle = ConstantProperty(word.word)
        self.day = ConstantProperty(word.day)
        self.month = ConstantProperty(word.month)
        self.year = ConstantProperty(word.year)
        self.imageUrl = ConstantProperty(word.imageUrl)
    }

}