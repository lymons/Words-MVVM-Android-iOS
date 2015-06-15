import Foundation
import UIKit
import ReactiveCocoa

class WordCellView: UITableViewCell, ReactiveView {
  
    @IBOutlet weak var yearLabel: UILabel!
    @IBOutlet weak var monthLabel: UILabel!
    @IBOutlet weak var dayLabel: UILabel!
    @IBOutlet weak var wordLabel: UILabel!
    @IBOutlet weak var wordImageView: UIImageView!

    lazy var scheduler: QueueScheduler = {
        let queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_LOW, 0)
        return QueueScheduler(queue: queue)
        }()

    func bindViewModel(viewModel: AnyObject) {

        let triggerSignal = self.rac_prepareForReuseSignal.asSignal() |> toVoidSignal

        if let wordViewModel = viewModel as? WordViewModel {
            yearLabel.rac_text <~ wordViewModel.year.producer |> map { "\($0)" }
            monthLabel.rac_text <~ wordViewModel.month.producer |> map { "\($0)" }
            dayLabel.rac_text <~ wordViewModel.day.producer |> map { "\($0)" }
            wordLabel.rac_text <~ wordViewModel.wordTitle.producer

            picImageSignalProducer(wordViewModel.imageUrl.value)
                |> startOn(scheduler)
                |> takeUntil(triggerSignal)
                |> observeOn(QueueScheduler.mainQueueScheduler)
                |> on(started: { self.wordImageView.image = nil })
                |> start(next: {
                    self.wordImageView.image = $0
                })
        }
    }

    private func picImageSignalProducer(imageUrl: String) -> SignalProducer<UIImage, NoError> {
        return SignalProducer {
            sink, _ in
            let data = NSData(contentsOfURL: NSURL(string: imageUrl)!)
            let image = UIImage(data: data!)
            sendNext(sink, image!)
            sendCompleted(sink)
        }
    }

}
