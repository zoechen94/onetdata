package com.andlinks.data.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.andlinks.data.pojo.json.EntityJson;
import com.andlinks.data.pojo.rel.Rel;
import com.andlinks.data.utils.ExcelExport;
import com.andlinks.data.utils.HttpUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 喜🐑
 * @date 2019-08-14 18:20
 */
public class JsonOut {
    public static void main(String[] args) throws IOException,Exception {
        String bdgicDetail="/Users/yalan/Desktop/onetonet/data/out-jsxxjs_baike_bdgjc_detail.json";
        String baiduDetail="/Users/yalan/Desktop/onetonet/data/out-jsxxjs_baike_baidu_data.json";
        String resBdgic=readJsonData(bdgicDetail);
        String resBaidu=readJsonData(baiduDetail);
        List<EntityJson> listBdgic= JSON.parseObject(resBdgic,new TypeReference<List<EntityJson>>(){});
        List<EntityJson> listBaidu= JSON.parseObject(resBaidu,new TypeReference<List<EntityJson>>(){});

        listBdgic.addAll(listBaidu);
        /**
         * 匹配节点
         */
        List<Rel> relations=new ArrayList<>();
        long s=System.currentTimeMillis();
        for(int i=0;i<listBdgic.size();i++){
            EntityJson end=listBdgic.get(i);
            for(int j=0;j<listBdgic.size();j++){
                EntityJson start=listBdgic.get(j);
                if(i==j||end.getName().equals(start.getName()))continue;

                JSONObject jsonObject=start.getInfobox();
                for(Object o:jsonObject.keySet()){
                    JSONArray jsonArray=JSONArray.parseArray(jsonObject.getString(o.toString()));
                    String value=JSONObject.parseObject(jsonArray.get(0).toString()).get("value").toString();
                    if(end.getName().equals(value)){
                        relations.add(new Rel(UUID.randomUUID().toString().replace("-",""),
                               o.toString(),start.getName(),end.getName(),"-","-"));
                    }
                }
            }
        }
        for(int i=0;i<relations.size();i++){
            System.out.println(relations.get(i).getId()+"\t"+relations.get(i).getStart()+" --"+relations.get(i).getType()+"-> "+relations.get(i).getEnd());
        }
        long end=System.currentTimeMillis();
        System.out.println("一共用"+(end-s)+"毫秒");
        Map<String, List<Rel>> group = relations.stream().collect(Collectors.groupingBy(Rel::getType));
        String[] rows=new String[]{"id","type","start","end","startType","endType"};
        for(Map.Entry<String,List<Rel>> type:group.entrySet()){
            System.out.println("\n-------start------------"+type.getKey()+"\n");
            List<Rel> rels=type.getValue();
            ExcelExport.export(type.getKey(),rows,rels,new String[]{"id","type","start","end"});
            System.out.println("\n-------e-n-d----------"+type.getKey()+"\n");
        }
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
