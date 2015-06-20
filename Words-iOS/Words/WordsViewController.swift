import UIKit
import ReactiveCocoa

class WordsViewController: UIViewController {

  @IBOutlet weak var searchTextField: UITextField!
  @IBOutlet weak var searchAcitivyIndicator: UIActivityIndicatorView!
    
  @IBOutlet weak var wordsTable: UITableView!
  
  private var bindingHelper: TableViewBindingHelper<WordViewModel>!

  override func viewDidLoad() {
    super.viewDidLoad()

    let wordService = WordService()
    let viewModel = WordListViewModel(wordService: wordService)

    searchAcitivyIndicator.rac_hidden <~ viewModel.isLoading.producer |> map { !$0 }
    wordsTable.rac_alpha <~ viewModel.isLoading.producer |> map { $0 ? CGFloat(0.5) : CGFloat(1.0) }

    bindingHelper = TableViewBindingHelper(tableView: wordsTable, sourceSignal: viewModel.words.producer, nibName: "WordCell")
  }
}

