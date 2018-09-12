package com.mlsc.yifeiwang.bindserve.model;

import com.mlsc.yifeiwang.bindserve.entity.EntBitcionAccount;

/**
 * Created by user on 2018/4/26.
 */
public class EntBitcionAccountParam extends EntBitcionAccount {
    private int consumeAmount; //消费豆

    public int getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(int consumeAmount) {
        this.consumeAmount = consumeAmount;
    }
}
