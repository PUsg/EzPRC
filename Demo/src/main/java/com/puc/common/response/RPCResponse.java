package com.puc.common.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@Builder
public class RPCResponse implements Serializable {
    // 状态码
    private int code;
    // 回执的信息
    private String message;

    // 具体数据
    private Object data;


    public static RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).data(data).build();
    }

    public static RPCResponse fail(Object data){
        return RPCResponse.builder().code(500).message("服务器发生错误").build();
    }


}
