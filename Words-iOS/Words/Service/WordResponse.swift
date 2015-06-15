import Foundation

struct WordResponse {
    let words: [Word]

    init(values: [Word]) {
        words = values
    }
}