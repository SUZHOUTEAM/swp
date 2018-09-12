package com.mlsc.yifeiwang.shorturl.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.shorturl.entity.ShortUrl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudang
 * @since 2017-12-11
 */
public interface IShortUrlService extends IService<ShortUrl> {

    void saveShortUrl(String shortCode,String longUrl);

    String getLongUrlByShortCode(String shortCode);

}
