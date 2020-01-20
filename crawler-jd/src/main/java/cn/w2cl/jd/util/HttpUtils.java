package cn.w2cl.jd.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Authror 卫骏
 * @Date 2020/1/20 10:56
 */
@Component
public class HttpUtils {

    //HttpClient连接池管理器
    private PoolingHttpClientConnectionManager cm;

    /**
     * 在该工具类构造的时候创建连接池管理器对象
     */
    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        this.cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        this.cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址获取页面数据
     * @param url
     * @return 页面数据
     */
    public String doGetHtml(String url){
        //1、获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        //2、获取HttpGet请求
        HttpGet httpGet = new HttpGet(url);

        //2.1、设置请求配置参数(如：超时时间等)
        httpGet.setConfig(this.getConfig());
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

        CloseableHttpResponse response = null;
        try {
            //3、使用HttpClient发起Get请求
            response = httpClient.execute(httpGet);
            //4、获取数据
            if(response.getStatusLine().getStatusCode() == 200){
                if(response.getEntity() != null){
                    String content = EntityUtils.toString(response.getEntity());
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 请求参数的配置（如：超时时间等）
     * @return
     */
    private RequestConfig getConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000) //创建连接的最大时间
                .setConnectionRequestTimeout(500) //获取请求的最大时间
                .setSocketTimeout(10*1000) //数据传输的最大时间 10秒
                .build();
        return requestConfig;
    }

    /**
     * 下载图片
     * @param url
     * @return 图片名称
     */
    public String doGetImage(String url){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200){
                if(response.getEntity() != null){
                    //下载图片
                    //获取图片后缀
                    String pre = url.substring(url.lastIndexOf("."));
                    //生成图片不重复名称
                    String picName = UUID.randomUUID() + pre;
                    //图片存入
                    //输出流
                    OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\admin123\\Desktop\\新建文件夹 (2)\\" + picName));
                    response.getEntity().writeTo(outputStream);
                    //返回图片名称
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
