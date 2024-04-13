package com.wasupstudio.enums;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;

import java.util.List;
import java.util.Map;

public enum ScriptEndingEnum {
    SCRIPT_ENDING_ONE("endingMovie-1","結局一"),
    SCRIPT_ENDING_TWO("endingMovie-2","結局二"),
    SCRIPT_ENDING_THREE("endingMovie-3","結局三"),
    SCRIPT_ENDING_FOUR("endingMovie-4", "結局四"),
    SCRIPT_ENDING("endingMovie", "結局")
    ;



    String type;

    String desc;

    ScriptEndingEnum(String type, String desc) {
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
        for (ScriptEndingEnum ele : ScriptEndingEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele.getDesc();
            }
        }
        return null;
    }

    public static ScriptEndingEnum getType(String desc) {
        for (ScriptEndingEnum ele : ScriptEndingEnum.values()) {
            if (desc.equals(ele.getDesc())) {
                return ele;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (ScriptEndingEnum ele : ScriptEndingEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("type", ele.getType());
            map.put("desc", ele.getDesc());
            list.add(map);
        }
        return list;
    }
}
