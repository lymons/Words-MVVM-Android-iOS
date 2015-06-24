import UIKit
import ReactiveCocoa

class WordsViewController: UIViewController {

    @IBOutlet weak var loadActivityIndicator: UIActivityIndicatorView!

    @IBOutlet weak var wordsTable: UITableView!

    private var bindingHelper: TableViewBindingHelper<WordViewModel>!

    override func viewDidLoad() {
        super.viewDidLoad()

        let wordService = WordService()
        let viewModel = WordListViewModel(wordService: wordService)

        // bind ui to current loading status
        loadActivityIndicator.rac_hidden <~ viewModel.isLoading.producer |> map { !$0 }
        wordsTable.rac_alpha <~ viewModel.isLoading.producer |> map { $0 ? CGFloat(0.5) : CGFloat(1.0) }

        // bind the table view with the view model
        bindingHelper = TableViewBindingHelper(tableView: wordsTable,
            sourceSignal: viewModel.words.producer, nibName: "WordCell")

        // observe selection event and show another view with the selected content
        bindingHelper.getTableViewSelectedItemSignal()
            |> observeOn(UIScheduler())
            |> observe(next: { self.showWordDetail($0) })
    }
    
    func showWordDetail(wordViewModel: WordViewModel) {
        // simply showing an alert..
        let alert = UIAlertView(title: "Selection", message: "You selected: \(wordViewModel.wordTitle.value)", delegate: nil, cancelButtonTitle: nil, otherButtonTitles: "Ok")
        alert.show()
    }
}
