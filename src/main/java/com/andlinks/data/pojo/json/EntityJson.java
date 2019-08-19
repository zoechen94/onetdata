package com.andlinks.data.pojo.json;

import com.alibaba.fastjson.JSONObject;
import com.andlinks.data.pojo.res.Res;
import lombok.Data;

import java.util.List;

/**
 * @author 喜🐑
 * @date 2019-08-14 20:52
 */
@Data
public class EntityJson {
    private List<String> tables;
    private String name;
    private String url;
    private String lemma_summary;
    private JSONObject infobox;
    private String pic;
    private String media;
    private String type;
}
