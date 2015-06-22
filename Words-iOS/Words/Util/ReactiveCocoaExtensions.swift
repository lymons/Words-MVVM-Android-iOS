//
//  ReactiveCocoaExtensions.swift
//  ReactiveTwitterSearch
//
//  Created by Colin Eberhardt on 12/05/2015.
//  Copyright (c) 2015 Colin Eberhardt. All rights reserved.
//

import Foundation
import ReactiveCocoa

extension RACSignal {
    func asSignal() -> Signal<AnyObject?, NSError> {
        return Signal {
            sink in
            self.subscribeNext({
                any in
                sendNext(sink, any)
                }, error: {
                    error in
                    sendError(sink, error)
                }, completed: {
                    sendCompleted(sink)
            })
        }
    }
}

public func toVoidSignal<T, E>(signal: Signal<T, E>) -> Signal<(), NoError> {
    return Signal {
        sink in
        signal.observe(SinkOf {
            event in
            switch event {
            case let .Next:
                sendNext(sink, ())
            default:
                break
            }
            })
    }
}

public func ignoreError<T: Any, E: ErrorType>(signalProducer: SignalProducer<T, E>) -> SignalProducer<T, NoError> {
    return signalProducer
        |> catch { _ in
            SignalProducer<T, NoError>.empty
    }
}

public func ignoreSignalError<T, E>(signal: Signal<T, E>) -> Signal<T, NoError> {
    return Signal {
        sink in
        signal.observe(next: { sendNext(sink, $0) })
    }
}
