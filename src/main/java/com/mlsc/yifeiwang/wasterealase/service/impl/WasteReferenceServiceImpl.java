package com.mlsc.yifeiwang.wasterealase.service.impl;

import com.mlsc.yifeiwang.wasterealase.entity.WasteReference;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.wasterealase.mapper.WasteReferenceMapper;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailParam;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceModel;
import com.mlsc.yifeiwang.wasterealase.model.WasteReferencePriceParam;
import com.mlsc.yifeiwang.wasterealase.service.IWasteReferenceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-05-24
 */
@Service
public class WasteReferenceServiceImpl extends ServiceImpl<WasteReferenceMapper, WasteReference> implements IWasteReferenceService {

    @Override
    public List<WasteReferencePriceModel> listDefDisposition(List<EntReleaseDetailParam> releaseDetailParams, String cantonCode) {
        List<EntReleaseDetailParam> querParams = new ArrayList<>(releaseDetailParams);
        List<WasteReferencePriceModel> wasteReferencePriceModels = listDefDispositionWithKeyWord(querParams, cantonCode);
        if (querParams.size() > 0) {
            wasteReferencePriceModels.addAll(listDefDispositionWithNoneKeyWord(querParams, cantonCode));
        }

        return wasteReferencePriceModels;


    }

    private List<WasteReferencePriceModel> listDefDispositionWithKeyWord(List<EntReleaseDetailParam> releaseDetailParams, String cantonCode) {

        List<WasteReferencePriceModel> priceModels = new ArrayList<>();
        WasteReferencePriceParam priceParam = new WasteReferencePriceParam();
        priceParam.setCantonCode(cantonCode);
        Iterator<EntReleaseDetailParam> it = releaseDetailParams.iterator();
        while (it.hasNext()) {
            EntReleaseDetailParam item = it.next();
            priceParam.setKeyword(Constant.KEYWORD);
            priceParam.setWasteCode(item.getWasteCode());
            List<WasteReference> wasteReferences = this.baseMapper.listDefDisposition(priceParam);
            wasteReferences.forEach(wasteReference -> {
                List<String> keywords = Stream.of(wasteReference.getKeyword().split(Constant.COMMA)).collect(Collectors.toList());
                List<String> filterKeyWords = keywords.stream().filter(keyword -> item.getWasteName().indexOf(keyword) != -1).collect(Collectors.toList());
                if (filterKeyWords.size() > 0) {
                    WasteReferencePriceModel priceModel = new WasteReferencePriceModel();
                    priceModel.setDefaultDispositionType(wasteReference.getDefaultDispositionType());
                    priceModel.setEntWasteId(item.getEntWasteId());
                    priceModel.setAmount(Util.changeUnitoT(item.getReleaseAmount(), item.getUnitCode()));
                    priceModel.setId(wasteReference.getId());
                    priceModel.setRemark(wasteReference.getRemark());
                    priceModels.add(priceModel);
                    it.remove();
                    return;
                }

            });
        }
        return priceModels;

    }

    private List<WasteReferencePriceModel> listDefDispositionWithNoneKeyWord(List<EntReleaseDetailParam> releaseDetailParams, String cantonCode) {
        List<WasteReferencePriceModel> priceModels = new ArrayList<>();
        WasteReferencePriceParam priceParam = new WasteReferencePriceParam();
        priceParam.setCantonCode(cantonCode);
        priceParam.setKeyword(Constant.NONE_KEYWORD);
        Iterator<EntReleaseDetailParam> it = releaseDetailParams.iterator();
        while (it.hasNext()) {
            EntReleaseDetailParam releaseDetail = it.next();
            priceParam.setWasteCode(releaseDetail.getWasteCode());
            List<WasteReference> wasteReferences = this.baseMapper.listDefDisposition(priceParam);
            wasteReferences.forEach(wasteReference -> {
                WasteReferencePriceModel priceModel = new WasteReferencePriceModel();
                priceModel.setDefaultDispositionType(wasteReference.getDefaultDispositionType());
                priceModel.setEntWasteId(releaseDetail.getEntWasteId());
                priceModel.setAmount(Util.changeUnitoT(releaseDetail.getReleaseAmount(), releaseDetail.getUnitCode()));
                priceModel.setId(wasteReference.getId());
                priceModel.setRemark(wasteReference.getRemark());
                priceModels.add(priceModel);
                return;
            });
        }
        return priceModels;
    }
}