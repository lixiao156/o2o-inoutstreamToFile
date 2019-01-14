package com.imooc.o2o.exceptions;

/**
 * @author lixw
 * @date created in 12:40 2019/1/9
 */
public class ProductOperationException extends RuntimeException {
    private static final long serialVersionUID = -1563134496031288457L;

    public ProductOperationException(String msg) {
        super(msg);
    }
}
