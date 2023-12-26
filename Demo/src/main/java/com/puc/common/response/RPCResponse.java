package com.puc.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RPCResponse implements Serializable {
    // 状态码
    private int code;
    // 回执的信息
    private String message;

    // 具体数据
    private Object data;

    private Class<?> dataType;


    public static RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).data(data).build();
    }

    public static RPCResponse fail(){
        return RPCResponse.builder().code(500).message("服务器发生错误").build();
    }


}
