package com.mlsc.EntFavoriteService;

import com.mlsc.BaseTest;
import com.mlsc.yifeiwang.enterprise.entity.EntFavorite;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.service.IEntFavoriteService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by user on 2017/10/16.
 */
public class EntFavoriteServiceTest  extends BaseTest {
    @Autowired
    private IEntFavoriteService entFavoriteService;

    @Test
    public void insertFavoriteServerTest(){
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235872864520192");
        EntFavorite entFavorite = new EntFavorite();
        entFavorite.setReferenceId("123123123132123");
        try{
            entFavoriteService.insertEntFavorite(user,entFavorite);
            Assert.assertTrue(entFavoriteService.selectById(entFavorite.getId())!=null);
        }catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Test
    public void cancelFavoriteServerTest(){
        User user = new User();
        user.setUserId("2255132750874624");
        user.setEnterpriseId("2235872864520192");
        EntFavorite entFavorite = new EntFavorite();
        entFavorite.setReferenceId("123123123132123");
        entFavorite.setFavoriteType("MSGID");
        try{
            entFavoriteService.insertEntFavorite(user,entFavorite);
            entFavoriteService.cancelEntFavorite(user,entFavorite);

            Assert.assertTrue(entFavoriteService.selectById(entFavorite.getId()) == null);
        }catch (Exception e){
            e.printStackTrace();;
        }
    }
}
