package com.wasupstudio.enums;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;

import java.util.List;
import java.util.Map;

public enum FileTypeEnum {

    PDF("application/docs","/docs/","文件檔"),
    IMAGE("application/image","/image/","圖片檔"),
    VIDEO("application/video","/video/", "影音檔")
    ;



    String type;

    String desc;

    String path;
    FileTypeEnum(String type,String path, String desc) {
        this.type = type;
        this.path = path;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getPath(){
        return path;
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
