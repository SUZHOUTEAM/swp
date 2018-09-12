package com.mlsc.yifeiwang.enterprise.model;

import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;

import java.util.List;

/**
 * Created by user on 2018/5/7.
 */
public class EntEvaluateParam extends EntEvaluate {
    private Integer minScore;
    private Integer maxScore;
    private int startRowIndex;//第几页
    private int rows;//每页多少行
    List<String> comments;

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
