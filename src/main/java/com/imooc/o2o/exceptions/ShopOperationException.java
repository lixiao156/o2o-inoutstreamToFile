package com.imooc.o2o.exceptions;

/**
 * 使用这个类实现 数据的事务管理  实现数据的原子性
 * @author lixw
 * @date created in 22:53 2019/1/3
 */
public class ShopOperationException extends RuntimeException {
    private static final long serialVersionUID = 2361446884822298905L;

    public ShopOperationException(String msg) {
        super(msg);
    }
}
