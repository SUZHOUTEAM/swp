package com.mlsc.yifeiwang.operaction.common;

/**
 * Created by user on 2018/5/9.
 */
public enum OperationType {
    NOPLAN("1"), HASPlan("0");

    private String value;

    OperationType(String value) {
        this.value = value;
    }

    public OperationType getPriceType(String value) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.getValue().equalsIgnoreCase(value)) {
                return operationType;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
