package com.mlsc.solr.util;


import com.mlsc.solr.model.EnterpriseIndex;
import com.mlsc.solr.model.WasteIndex;
import com.mlsc.waste.utils.Util;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DocumentUtil {
    public static <T> T toBean(SolrDocument record, Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = null;
            if (field.getName().equals("wasteName") || field.getName().equals("dispositionType")) {
                List<String> list = (List<String>) record.get(field.getName());
                if (list != null && list.size() > 0) {
                    value = Util.listToString(list);
                }
            } else if (field.getName().equals("wasteCode")) {
                List<String> list = (List<String>) record.get(field.getName());
                if (list != null && list.size() > 0) {
                    value = groupWasteCodeByWasteType(list);
                }
            } else {
                value = record.get(field.getName());
            }

            try {
                if (value != null) {
                    BeanUtils.setProperty(obj, field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    private static Object groupWasteCodeByWasteType(List<String> list) {
        Map<String, StringBuilder> wasteCodeMap = new HashMap<String, StringBuilder>();
        for (String wasteCode : list) {
            String replaceWasteCode = wasteCode.replace("<font color=\"red\">", "").replace("</font>", "");
            String wasteType = replaceWasteCode.substring(replaceWasteCode.length() - 2, replaceWasteCode.length());
            if (wasteCodeMap.get(wasteType) != null) {
                wasteCodeMap.get(wasteType).append(",").append(wasteCode);
            } else {
                StringBuilder wasteCodeBuilder = new StringBuilder();
                wasteCodeBuilder.append(wasteCode);
                wasteCodeMap.put(wasteType, wasteCodeBuilder);
            }
        }
        return wasteCodeMap;
    }


    public static <T> T toBean4Waste(SolrDocument record, Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = record.get(field.getName());
            try {
                BeanUtils.setProperty(obj, field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static <T extends EnterpriseIndex> List<T> toBeanList(SolrDocumentList records, Class<T> clazz, Map<String, Map<String, List<String>>> hightlightList, String key) {

        List<T> list = new ArrayList<T>();
        for (SolrDocument record : records) {
            list.add(toBean(record, clazz));
        }
        if (hightlightList == null || hightlightList.size() == 0) {
            return list;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            EnterpriseIndex enterpriseInfo = it.next();
            for (Map.Entry<String, Map<String, List<String>>> entry : hightlightList.entrySet()) {
                if (entry.getKey().equals(enterpriseInfo.getId())) {
                    for (Map.Entry<String, List<String>> entryLayer2 : entry.getValue().entrySet()) {
                        if (null != entryLayer2.getKey() && "wasteName".equals(entryLayer2.getKey())) {
                            String wasteNameStr = Util.listToString(entryLayer2.getValue());
                            if (wasteNameStr.indexOf("font color") == -1) {
                                enterpriseInfo.setWasteName(Util.listToString(entryLayer2.getValue()));
                            } else {
                                List<String> wasteNameList = entryLayer2.getValue();
                                Iterator<String> wasteNameIt = wasteNameList.iterator();
                                while (wasteNameIt.hasNext()) {
                                    String wasteName = wasteNameIt.next();
                                    if (wasteName.indexOf("font color") == -1) {
                                        wasteNameIt.remove();
                                    }
                                }
                                enterpriseInfo.setWasteName(Util.listToString(wasteNameList));

                                if (key.indexOf("wasteCode") != -1 || key.indexOf("wasteTypeCode") != -1) {
                                    enterpriseInfo.setWasteName(Util.listToString(entryLayer2.getValue()).replace("<font color=\"red\">", "").replace("</font>", ""));
                                } else {
                                    enterpriseInfo.setWasteName(Util.listToString(entryLayer2.getValue()));
                                }

                            }
                        }
                        if (null != entryLayer2.getKey() && "dispositionType".equals(entryLayer2.getKey())) {
                            enterpriseInfo.setDispositionType(Util.listToString(entryLayer2.getValue()));
                        }
                        if (null != entryLayer2.getKey() && key.indexOf("wasteCode") != -1 && "wasteCode".equals(entryLayer2.getKey())) {
                            String wasteCodeStr = Util.listToString(entryLayer2.getValue());
                            if (wasteCodeStr.indexOf("font color") == -1) {
//                                enterpriseInfo.setWasteCode(Util.listToString(entryLayer2.getValue()));
                            } else {
                                List<String> WasteCodeList = entryLayer2.getValue();
                                Iterator<String> subIt = WasteCodeList.iterator();
                                while (subIt.hasNext()) {
                                    String wasteCode = subIt.next();
                                    if (wasteCode.indexOf("font color") == -1) {
                                        subIt.remove();
                                    }
                                }
                                enterpriseInfo.setWasteCode((Map<String, StringBuilder>) groupWasteCodeByWasteType(WasteCodeList));
                            }
                        }
                        if (null != entryLayer2.getKey() && key.indexOf("wasteTypeCode") != -1 && "wasteTypeCode".equals(entryLayer2.getKey())) {
                            String wasteCodeStr = Util.listToString(entryLayer2.getValue());
                            if (wasteCodeStr.indexOf("font color") == -1) {
                                enterpriseInfo.setWasteCode((Map<String, StringBuilder>) groupWasteCodeByWasteType(entryLayer2.getValue()));
                            } else {
                                List<String> WasteCodeList = entryLayer2.getValue();
                                Iterator<String> subIt = WasteCodeList.iterator();
                                while (subIt.hasNext()) {
                                    String wasteCode = subIt.next();
                                    if (wasteCode.indexOf("font color") == -1) {
                                        subIt.remove();
                                    }
                                }
                                enterpriseInfo.setWasteCode((Map<String, StringBuilder>) groupWasteCodeByWasteType(WasteCodeList));
                            }
                        }
                        if (null != entryLayer2.getKey() && "entName".equals(entryLayer2.getKey())) {
                            enterpriseInfo.setEntName(Util.listToString(entryLayer2.getValue()));
                        }
                        if (null != entryLayer2.getKey() && "shortName".equals(entryLayer2.getKey())) {
                            enterpriseInfo.setShortName(Util.listToString(entryLayer2.getValue()));
                        }
                        if (null != entryLayer2.getKey() && "entAddress".equals(entryLayer2.getKey())) {
                            enterpriseInfo.setEntAddress(Util.listToString(entryLayer2.getValue()));
                        }
                    }
                }
            }
        }
        return list;
    }


    public static <T extends WasteIndex> List<T> toBeanList4Waste(SolrDocumentList records, Class<T> clazz, Map<String, Map<String, List<String>>> hightlightList) {
        List<T> list = new ArrayList<>();
        for (SolrDocument record : records) {
            list.add(toBean4Waste(record, clazz));
        }


        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            WasteIndex wasteIndex = it.next();
            for (Map.Entry<String, Map<String, List<String>>> entry : hightlightList.entrySet()) {
                if (entry.getKey().equals(wasteIndex.getId())) {
                    for (Map.Entry<String, List<String>> entryLayer2 : entry.getValue().entrySet()) {
                        if (null != entryLayer2.getKey() && "wasteName".equals(entryLayer2.getKey())) {
                            wasteIndex.setWasteNameDisplay(Util.listToString(entryLayer2.getValue()).replace("\"", "\'"));
                        }
                        if (null != entryLayer2.getKey() && "wasteCode".equals(entryLayer2.getKey())) {
                            wasteIndex.setWasteCodeDisplay(Util.listToString(entryLayer2.getValue()).replace("\"", "\'"));
                        }
                        if (null != entryLayer2.getKey() && "wasteDesc".equals(entryLayer2.getKey())) {
                            wasteIndex.setWasteDescDisplay(Util.listToString(entryLayer2.getValue()).replace("\"", "\'"));
                        }
                    }
                }
            }
        }
        return list;
    }
}
