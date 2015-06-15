import Foundation

struct Word {

    let id: Int
    let word: String
    let day: Int
    let month: Int
    let year: Int
    let imageUrl: String

    init(id: Int, word: String, day: Int, month: Int, year: Int, imageUrl: String){
        self.id = id
        self.word = word
        self.day = day
        self.month = month
        self.year = year
        self.imageUrl = imageUrl
    }
}

extension Word: Printable {
    var description: String {
        return "Word \(id) - \(word) - \(day)/\(month)/\(year) - \(imageUrl)"
    }
}