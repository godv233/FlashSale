package com.java.sale.redis;

public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaKey goodsOver = new MiaoshaKey(-1, "gl:");
}
