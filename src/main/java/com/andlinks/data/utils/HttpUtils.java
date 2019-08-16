package com.andlinks.data.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 喜🐑
 * @date 2019-08-12 17:04
 */
public class HttpUtils {
    public static String  get(String http,String key) throws IOException {
        URL url = new URL(http);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置连接方式
        conn.setRequestMethod("GET");
        //设置主机连接时间超时时间3000毫秒
        conn.setConnectTimeout(3000);
        //设置读取远程返回数据的时间3000毫秒
        conn.setReadTimeout(3000);

        //发送请求
        conn.connect();
        //获取输入流
        InputStream is = conn.getInputStream();
        //封装输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        //接收读取数据
        StringBuffer sb = new StringBuffer();

        String line = null;
        while((line = br.readLine())!=null) {
            sb.append(line);
            sb.append("\r\n");
        }
        if(null!=br) {
            br.close();
        }
        if(null!=is) {
            is.close();
        }
        //关闭连接
        conn.disconnect();
        if(key!=null){
          return   JSONObject.parseObject(sb.toString()).get(key).toString();
        }
        return sb.toString();
    }

    public String doPost(String httpurl, Map<Object, Object> param) throws IOException {
        String jsonparam = JSON.toJSONString(param);
        URL url = new URL(httpurl);
        //获取httpurlConnection连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置读取超时
        conn.setConnectTimeout(3000);
        //设置读取超时
        conn.setReadTimeout(3000);

        //传送数据
        conn.setDoOutput(true);
        //读取数据
        conn.setDoInput(true);
        //设置请求方式
        conn.setRequestMethod("POST");
        //设置传入参数格式
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
        conn.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");

        //获取输出流
        OutputStream os = conn.getOutputStream();
        //输出数据
        os.write(jsonparam.getBytes());
        //获取输入流
        InputStream is = conn.getInputStream();
        //封装输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        StringBuffer sb = new StringBuffer();
        String line = null;

        while((line = br.readLine())!=null) {
            sb.append(line);
            sb.append("\r\n");
        }
        if(null != br) {
            br.close();
        }
        if(null != is) {
            is.close();
        }
        if(null != os) {
            os.close();
        }
        //关闭连接
        conn.disconnect();

        return sb.toString();
    }

    public static InputStream getUrlFile(String httpurl, Map<String,String> parsms) throws IOException {
        String pa=JSONObject.toJSONString(parsms);
        try {
            Request request = Request.Post(httpurl).bodyString(pa, ContentType.APPLICATION_JSON).addHeader("Content-Type", "application/json");
            InputStream result = request.execute().returnContent().asStream();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}