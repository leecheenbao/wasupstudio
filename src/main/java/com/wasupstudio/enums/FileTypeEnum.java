package com.wasupstudio.enums;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;

import java.util.List;
import java.util.Map;

public enum FileTypeEnum {

    PDF("pdf","/pdf/","PDF文件"),
    IMAGE("image","/image/","圖片檔"),
    VIDEO("video","/video/", "影音檔")
    ;



    String type;

    String desc;

    FileTypeEnum(String type,String path, String desc) {
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
        for (FileTypeEnum ele : FileTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele.getDesc();
            }
        }
        return null;
    }

    public static FileTypeEnum getEnum(String type) {
        for (FileTypeEnum ele : FileTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (FileTypeEnum ele : FileTypeEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("type", ele.getType());
            map.put("desc", ele.getDesc());
            list.add(map);
        }
        return list;
    }
}
