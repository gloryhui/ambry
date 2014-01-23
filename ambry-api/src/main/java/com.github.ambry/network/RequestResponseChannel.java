package com.github.ambry.network;

import com.github.ambry.metrics.MetricsHistogram;

/**
 * RequestResponse Channel used by the network layer to queue new requests and
 * send responses over the network from the channel. This is used by the server
 * and hence cannot be used to receive response or open a connection as they would
 * be client related operations.
 */
public interface RequestResponseChannel {

  /**
   * Queue's the response into the channel for the network server to pick up
   * @param payloadToSend The payload to be sent over the network
   * @param originalRequest The original request this response belongs to
   * @param responseQueueTime The time spent by the response in the response queue is measured using this metric
   * @param responseSendTime The time spent sending the response is measured using this metric
   * @throws InterruptedException
   */
  public void sendResponse(Send payloadToSend,
                           Request originalRequest,
                           MetricsHistogram responseQueueTime,
                           MetricsHistogram responseSendTime) throws InterruptedException;

  /**
   * Receives the request from the channel
   * @return The request that was queued by the network layer into the channel
   * @throws InterruptedException
   */
  public Request receiveRequest() throws InterruptedException;

  /**
   * Sends a request over the network. The request gets queued by the channel.
   * @param request The request to be queued by the channel
   * @throws InterruptedException
   */
  public void sendRequest(Request request) throws InterruptedException;

  /**
   * Closes the connection on which the original request came
   * @param request The request whose connection needs to be closed
   * @throws InterruptedException
   */
  public void closeConnection(Request request) throws InterruptedException;

  /**
   * Shuts down the request response channel
   */
  public void shutdown();
}
