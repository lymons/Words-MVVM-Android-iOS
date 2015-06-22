//
//  TableViewBindingHelper.swift
//  ReactiveSwiftFlickrSearch
//
//  Created by Colin Eberhardt on 15/07/2014.
//  Copyright (c) 2014 Colin Eberhardt. All rights reserved.
//

import Foundation
import ReactiveCocoa
import UIKit

@objc protocol ReactiveView {
  func bindViewModel(viewModel: AnyObject)
}

// a helper that makes it easier to bind to UITableView instances
// see: http://www.scottlogic.com/blog/2014/05/11/reactivecocoa-tableview-binding.html
class TableViewBindingHelper<T: AnyObject> : NSObject, UITableViewDelegate {
  
    //MARK: Properties

    private let tableView: UITableView
    private let templateCell: UITableViewCell
    private let dataSource: DataSource

    //MARK: Public API

    init(tableView: UITableView, sourceSignal: SignalProducer<[T], NoError>, nibName: String) {
        self.tableView = tableView

        let nib = UINib(nibName: nibName, bundle: nil)

        // create an instance of the template cell and register with the table view
        templateCell = nib.instantiateWithOwner(nil, options: nil)[0] as! UITableViewCell
        tableView.registerNib(nib, forCellReuseIdentifier: templateCell.reuseIdentifier!)

        dataSource = DataSource(data: [AnyObject](), templateCell: templateCell)

        super.init()

        sourceSignal.start(next: {
            data in
            self.dataSource.data = data.map { $0 as AnyObject }
            self.tableView.reloadData()
        })

        tableView.dataSource = dataSource
    }

    // TODO: maybe a solution using Action is better?
    func getTableViewSelectedItemSignal() -> Signal<T, NoError> {
        let tableSelectedSignal = self.rac_signalForSelector("tableView:didSelectRowAtIndexPath:", fromProtocol:UITableViewDelegate.self).asSignal()
            |> map({ $0 as! RACTuple })
            |> map({ $0.second as! NSIndexPath})
            |> ignoreSignalError
            |> map({ i in self.dataSource.data[i.row] as! T })

        tableView.delegate = self
        return tableSelectedSignal
    }
}

class DataSource: NSObject, UITableViewDataSource
{
    private let templateCell: UITableViewCell
    private let selectionAction: ReactiveCocoa.Action<AnyObject, AnyObject, NoError>?
    var data: [AnyObject]

    init(data: [AnyObject], templateCell: UITableViewCell, selectionAction: ReactiveCocoa.Action<AnyObject, AnyObject, NoError>? = nil) {
        self.data = data
        self.templateCell = templateCell
        self.selectionAction = selectionAction
    }

    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }

    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let item: AnyObject = data[indexPath.row]
        let cell = tableView.dequeueReusableCellWithIdentifier(templateCell.reuseIdentifier!) as! UITableViewCell
        if let reactiveView = cell as? ReactiveView {
            reactiveView.bindViewModel(item)
        }
        return cell
    }
}
