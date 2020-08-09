package com.kyle.demo.entity;

import lombok.Data;

/**
 * @author 小邓
 * @date 2020/8/8 - 11:52
 */
@Data
public class RequestInfo {

    private static final String SPACE = "  ";
    private static final String TIME_UNIT = "ms";

    private String ip;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private Long timeCost;
    private Exception exception;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(classMethod).append(SPACE)
          .append(httpMethod).append(SPACE)
          .append(ip).append(SPACE)
          .append(requestParams).append(SPACE);
        if(exception == null){
            sb.append(timeCost).append(TIME_UNIT);
        }else{
            sb.append(exception);
        }

        return sb.toString();
    }
}
