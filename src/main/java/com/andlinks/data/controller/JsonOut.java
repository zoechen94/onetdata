package com.andlinks.data.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.andlinks.data.pojo.json.EntityJson;

import java.io.*;
import java.util.List;
import java.util.Set;

/**
 * @author 喜🐑
 * @date 2019-08-14 18:20
 */
public class JsonOut {
    public static void main(String[] args) throws IOException {
        String bdgicDetail="/Users/yalan/Desktop/onetonet/data/out-jsxxjs_baike_bdgjc_detail.json";
        String baiduDetail="/Users/yalan/Desktop/onetonet/data/out-jsxxjs_baike_baidu_data.json";
        String resBdgic=readJsonData(bdgicDetail);
        String resBaidu=readJsonData(baiduDetail);
        List<EntityJson> listBdgic= JSON.parseObject(resBdgic,new TypeReference<List<EntityJson>>(){});
        List<EntityJson> listBaidu= JSON.parseObject(resBaidu,new TypeReference<List<EntityJson>>(){});

        listBdgic.addAll(listBaidu);


    }


    /**
     * 读取文件数据加入到map缓存中
     * @throws IOException
     */
    public static String readJsonData(String fileName) throws IOException {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
