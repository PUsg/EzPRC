package com.puc.client;
import com.puc.common.request.RPCRequest;
import com.puc.common.response.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
