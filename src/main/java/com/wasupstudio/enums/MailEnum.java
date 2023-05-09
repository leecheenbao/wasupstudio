package com.wasupstudio.enums;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;

import java.util.List;
import java.util.Map;

public enum MailEnum {
    MAIL_SUBTITLE_SINGN("MAIL_SUBTITLE_SINGN","歡迎加入S&B！"),
    MAIL_SUBTITLE_FORGET("MAIL_SUBTITLE_FORGET","S&B【忘記密碼】"),
    MAIL_SUBTITLE_CONNECT("MAIL_SUBTITLE_CONNECT","S&B【來自用戶的問題】"),
    MAIL_SUBTITLE_REPORT_SEND("MAIL_SUBTITLE_REPORT_SEND","S&B【你的檢舉已經成立】"),
    MAIL_SUBTITLE_REPORT_REC("MAIL_SUBTITLE_REPORT_REC","S&B【你的行為已經被檢舉】")
            ;



    String type;

    String desc;

    MailEnum(String type, String desc) {
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
