package com.imooc.o2o.enums;

/**
 * 枚举状态变量
 *
 * @author lixw
 * @date created in 12:26 2019/1/3
 */
public enum ShopSateEnum {
    /**
     * 设置成为private 是不希望外面的项目能够改变 设置的各状态值得属性
     * 只能内部构造器改造
     */
    CHECK(0, "审核中"), OFFLINE(-1, "非法店铺"), SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"), INNER_ERROR(-1001, "系统内部错误"),
    NULL_SHOPID(-1002, "ShopId为空"),NULL_SHOP(-1003,"shop信息为空");
    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }


    public String getStateInfo() {
        return stateInfo;
    }



    private ShopSateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
    /**
     * 依据传入的state返回相应的enum值
     */
    public static ShopSateEnum stateOf(int state){
        //遍历
        for(ShopSateEnum sateEnum : values()){
            if(sateEnum.getState() == state){
                return sateEnum;
            }
        }
        return null;
    }


}
