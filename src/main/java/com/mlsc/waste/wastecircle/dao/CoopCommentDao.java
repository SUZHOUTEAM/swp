package com.mlsc.waste.wastecircle.dao;

import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.wastecircle.model.CoopComment;

import java.util.List;

public interface CoopCommentDao {
    
    void saveCoopComment(CoopComment coopComment) throws DaoAccessException;

    List<CoopComment> getCoopComment(CoopComment coopComment) throws DaoAccessException;
    
    void updateCoopComment(CoopComment coopComment) throws DaoAccessException;
}
