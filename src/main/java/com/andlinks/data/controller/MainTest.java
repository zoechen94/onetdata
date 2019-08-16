package com.andlinks.data.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.andlinks.data.pojo.EntityList;
import com.andlinks.data.pojo.mid.Entity;
import com.andlinks.data.pojo.mid.Property;
import com.andlinks.data.pojo.mid.RelationVO;
import com.andlinks.data.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 喜🐑
 * @date 2019-08-12 16:16
 */
public class MainTest {
    public static void main(String[] args) throws IOException {
        //获取模型下所有实体
        String result = HttpUtils.get("http://42.123.99.35:8880/api/meta/model/entity/list?modelId=3c13bdee99924a9fab0f5db8a0a32906", "payload");
        System.out.println(result);
        List<Entity> entityList = JSON.parseObject(result, new TypeReference<List<Entity>>() {
        });
        System.out.println("===========实体============");
        entityList.stream().forEach(n -> {
            System.out.println(n.getName() + "\t" + n.getIcon());
        });


        //分页获取模型关系类型
        String basic = "http://42.123.99.35:8880/api/meta/model/relation/list/page?modelId=3c13bdee99924a9fab0f5db8a0a32906&pageSize=5&pageNum=";
        String relationUrl = HttpUtils.get(basic + 1, "payload");

        Integer pages = JSONObject.parseObject(relationUrl).getInteger("pages");

        List<RelationVO> relationVos = new ArrayList<>();
        for (int i = 1; i <= pages; i++) {
            String res = HttpUtils.get(basic + i, "payload");
            String listStr = JSONObject.parseObject(res).get("list").toString();
            List<RelationVO> rvo = JSON.parseObject(listStr, new TypeReference<List<RelationVO>>() {
            });
            rvo.stream().forEach(n -> relationVos.add(n));
        }
        System.out.println("===========关系类型=============");
        System.out.println(relationUrl);
        System.out.println("pages:" + pages);
        relationVos.stream().forEach(n -> {
            System.out.println(n.getName() + "\t" + n.getModelName());
        });


        //查询实体或关系属性列表
        String params = "http://42.123.99.35:8880/api/meta/property/upperId/04d7ebeaa2864fbd9ab3ab9f951b135f";
        String paramsUrl = HttpUtils.get(params, "payload");
        List<Property> properties = JSON.parseObject(paramsUrl, new TypeReference<List<Property>>() {
        });
        System.out.println("============属性==============");
        properties.stream().forEach(n -> {
            System.out.println(n.getName() + "\t" + n.getUpperId() + "\tid:" + n.getId());
        });
    }
}
