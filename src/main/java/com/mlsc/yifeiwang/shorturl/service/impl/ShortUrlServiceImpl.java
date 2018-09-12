package com.mlsc.yifeiwang.shorturl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.shorturl.entity.ShortUrl;
import com.mlsc.yifeiwang.mapper.ShortUrlMapper;
import com.mlsc.yifeiwang.shorturl.service.IShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wudang
 * @since 2017-12-11
 */
@Service
@Repository("shortUrlService")
public class ShortUrlServiceImpl extends ServiceImpl<ShortUrlMapper,ShortUrl> implements IShortUrlService {
    private final static Logger logger = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    @Override
    public void saveShortUrl(String shortCode, String longUrl) {
        ShortUrl shortUrl=new ShortUrl();
        shortUrl.setLongUrl(longUrl);
        shortUrl.setShortCode(shortCode);
        Date currentDate = new Date();
        shortUrl.setCreateTime(currentDate);
        this.insert(shortUrl);
    }

    @Override
    public String getLongUrlByShortCode(String shortCode){
        EntityWrapper<ShortUrl> appWrapper = new EntityWrapper<ShortUrl>();
        appWrapper.setSqlSelect("longUrl");
        appWrapper.eq("shortCode", shortCode);
        List<ShortUrl> shortUrlList = this.selectList(appWrapper);
        if (shortUrlList != null && shortUrlList.size() > 0) {
            return shortUrlList.get(0).getLongUrl();
        } else {
            return null;
        }
    }
}
