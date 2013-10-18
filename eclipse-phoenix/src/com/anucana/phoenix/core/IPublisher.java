package com.anucana.phoenix.core;


public interface IPublisher {

    void registerSubscribers(ISubscriber s);

    void removeSubscribers(ISubscriber s);
}
