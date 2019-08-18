package com.andlinks.data.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.andlinks.data.pojo.json.EntityJson;
import com.andlinks.data.pojo.json.MediaBaiKe;
import com.andlinks.data.pojo.json.PicBaike;
import com.andlinks.data.pojo.mid.Entity;
import com.andlinks.data.pojo.res.Res;
import com.andlinks.data.utils.ExcelExport;
import com.andlinks.data.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.media.jfxmedia.Media;
import org.apache.poi.ss.formula.functions.T;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 喜🐑
 * @date 2019-08-14 21:11
 */
public class MediaPic {
    public static void main(String[] args) throws IOException,Exception {
        List<EntityJson> entitys=getEntityJson();
        String media1 = "jsxxjs_baike_baidu_media";
        String media2 = "jsxxjs_baike_bdgjc_media";
        String pic1 = "jsxxjs_baike_baidu_pic";
        String pic2 = "jsxxjs_baike_bdgjc_pic";
        String basePath = "/Users/yalan/Desktop/onetonet/data/jsxxjs_baike/";
        List<MediaBaiKe> mediaList1 = generate(basePath + media1, MediaBaiKe.class);
        List<MediaBaiKe> mediaList2 = generate(basePath + media2, MediaBaiKe.class);
        List<PicBaike> picList1 = generate(basePath + pic1, PicBaike.class);
        List<PicBaike> picList2 = generate(basePath + pic2, PicBaike.class);
        //media
        mediaList1.addAll(mediaList2);
        picList1.addAll(picList2);
        //生成多媒体和图片文件
//        mediaList1.stream().forEach(n -> {
//           InputStream in= getInputStream("media",n.getMedia_id().substring(n.getMedia_id().lastIndexOf(".")),n.getMedia(),n.getMedia_id().substring(0,n.getMedia_id().lastIndexOf(".")));
//           if(in!=null){
//               n.setFileName(n.getMedia_id());
//           }else{
//               System.err.println(n.getMedia_id());
//           }
//        });
//        for (int i = 0; i < picList1.size(); i++) {
//
//            PicBaike n = picList1.get(i);
//            System.out.println(i + 1 + "\tid:" + n.getPicture_id());
//            InputStream in = getInputStream("pic", n.getPicture_id().substring(n.getPicture_id().lastIndexOf(".")), n.getPicture(), n.getPicture_id().substring(0, n.getPicture_id().lastIndexOf(".")));
//            if (in != null) {
//                n.setFileName(n.getPicture_id());
//            }
//        }

        //compare
//        List<EntityJson> entityJsons = getEntityJson();
//
//        mediaList1.stream().forEach(n -> {
//            entityJsons.stream().forEach(e -> {
//                if (n.getUrl().equals(e.getUrl())) {
//                    n.setName(e.getName());
//                    return;
//                }
//            });
//        });
//        picList2.stream().forEach(n -> {
//            entityJsons.stream().forEach(e ->
//            {
//                if (n.getUrl().equals(e.getUrl())) {
//                    n.setName(e.getName());
//                    return;
//                }
//            });
//        });
//        mediaList1.stream().forEach(n -> {
//            System.out.println(n.getMedia_id() + "\talt:" + n.getMed_alt() + "\t" + n.getName());
//        });
//        picList1.stream().forEach(n -> {
//            System.out.println(n.getPicture_id() + "\talt:" + n.getPic_alt() + "\t" + n.getName());
//        });
         entitys.stream().forEach(n->{
           StringBuilder pic=new StringBuilder();
           StringBuilder media=new StringBuilder();
             picList1.stream().forEach(p->{
                 if(n.getUrl().equals(p.getUrl())){
                    pic.append(p.getPicture_id().substring(0,p.getPicture_id().lastIndexOf("."))+",");
                 }
             });
             mediaList1.stream().forEach(m->{
                 if(m.getUrl().equals(n.getUrl())){
                     media.append(m.getMedia_id().substring(0,m.getMedia_id().lastIndexOf("."))+",");
                 }
             });
             n.setPic(pic.toString());
             n.setMedia(media.toString());
             n.setType(n.getName()+" a");
         });
//         entitys.stream().forEach(n-> {
//             System.out.println("-----------"+n.getName()+"----start---------");
//             if(n.get()!=null&&n.getRes().size()!=0){
//                 n.getRes().stream().forEach(r->{
//                     System.out.println("  "+r.getId()+"\t"+r.getType());
//                 });
//             }
//             System.out.println("-----------"+n.getName()+"----over---------\n");
//         });
        Map<String,List<EntityJson>> map= entitys.stream().collect(Collectors.groupingBy(EntityJson::getType));
        String[] rows=new String[]{"id","type","name","Id","pic","media"};
        for(Map.Entry<String,List<EntityJson>> type:map.entrySet()){
            ExcelExport.export(type.getKey(),rows,type.getValue(),new String[]{"type","name","pic","media"});
        }
    }

    public static InputStream getInputStream(String mediaType, String fileType, String content, String id) {
        String fdfsAddr = "http://42.123.99.62:57777/get_file?remote_file_id=" + content;
        Map<String, String> params = new HashMap<>();
        params.put("remote_file_id", content);
        try {
            InputStream inputStream = HttpUtils.getUrlFile(fdfsAddr, params);
            if (inputStream == null) {
                return null;
            } else {
                inputStream = new BufferedInputStream(inputStream);
            }
            File file = new File("/Users/yalan/Desktop/generator/" + mediaType + "/" + id + fileType);
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            // 判断输入或输出是否准备好
//            if (inputStream != null && out != null) {
            int temp = 0;
            // 开始拷贝
            byte[] flush = new byte[1024];
            while ((temp = inputStream.read(flush)) != -1) {
                // 边读边写
                out.write(flush, 0, temp);
            }
            // 关闭输入输出流
            out.flush();
            out.close();
            inputStream.close();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 文件中所有记录的对象集合
     *
     * @param file
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> generate(String file, Class<T> clazz) throws IOException {
        List<T> res = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        //简写如下
        //BufferedReader br = new BufferedReader(new InputStreamReader(
        //        new FileInputStream("E:/phsftp/evdokey/evdokey_201103221556.txt"), "UTF-8"));
        String line = "";
        while ((line = br.readLine()) != null) {
            res.add(JSON.parseObject(line, clazz));
        }
        br.close();
        isr.close();
        fis.close();
        return res;
    }


    public static List<EntityJson> getEntityJson() throws IOException {
        String bdgicDetail = "/Users/yalan/Desktop/onetonet/data/out-jsxxjs_baike_bdgjc_detail.json";
        String baiduDetail = "/Users/yalan/Desktop/onetonet/data/out-jsxxjs_baike_baidu_data.json";
        String resBdgic = readJsonData(bdgicDetail);
        String resBaidu = readJsonData(baiduDetail);
        List<EntityJson> listBdgic = JSON.parseObject(resBdgic, new TypeReference<List<EntityJson>>() {
        });
        List<EntityJson> listBaidu = JSON.parseObject(resBaidu, new TypeReference<List<EntityJson>>() {
        });

        listBdgic.addAll(listBaidu);
        return listBdgic;
    }

    /**
     * 读取文件数据加入到map缓存中
     *
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
