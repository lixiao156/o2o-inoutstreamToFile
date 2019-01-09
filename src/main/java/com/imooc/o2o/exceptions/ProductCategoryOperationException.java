package com.imooc.o2o.exceptions;

/**
 * @author lixw
 * @date created in 17:21 2019/1/8
 */
public class ProductCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = -5736666464591192103L;

    public ProductCategoryOperationException(String msg) {
        super(msg);
    }

}
