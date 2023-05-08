package com.wasupstudio.enums;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;

import java.util.List;
import java.util.Map;

public enum StudySheetEnum {

    TIGER_AND_RABBIT("TIGER_AND_RABBIT","虎兔篇-親子共讀任務學習單"),
    MOUSE_AND_OX("MOUSE_AND_OX","鼠牛篇-親子共讀任務學習單"),
    HORSE_AND_SHEEP("HORSE_AND_SHEEP","馬羊篇-親子共讀任務學習單"),
    DRAGON_AND_TIGER("DRAGON_AND_TIGER","龍虎篇-親子共讀任務學習單"),

    ;



    String type;

    String desc;

    StudySheetEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDesc(String type) {
        for (StudySheetEnum ele : StudySheetEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele.getDesc();
            }
        }
        return null;
    }

    public static StudySheetEnum getEnum(String type) {
        for (StudySheetEnum ele : StudySheetEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (StudySheetEnum ele : StudySheetEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("type", ele.getType());
            map.put("desc", ele.getDesc());
            list.add(map);
        }
        return list;
    }
}
