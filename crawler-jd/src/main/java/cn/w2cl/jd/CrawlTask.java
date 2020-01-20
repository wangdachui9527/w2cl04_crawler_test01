package cn.w2cl.jd;

import cn.w2cl.jd.pojo.Item;
import cn.w2cl.jd.service.ItemService;
import cn.w2cl.jd.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Authror 卫骏
 * @Date 2020/1/20 14:55
 */
@Component
public class CrawlTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Scheduled(fixedDelay = 100*1000) //100秒爬取一次数据
    public void crawlTask() throws IOException {
        //共145页
        String url1 = "https://list.jd.com/list.html?cat=9987,653,655&page=";
        String url2 = "&sort=sort_rank_asc&trans=1&JL=6_0_0&ms=10#J_main";
        for(int i = 1 ; i < 146; i++){
            String htmlContent = httpUtils.doGetHtml(url1 + i + url2);
            //解析数据
            this.parse(htmlContent);
        }
    }

    /**
     * 解析html数据
     * @param htmlContent
     */
    private void parse(String htmlContent) throws IOException {
        Document doc = Jsoup.parse(htmlContent);
        Elements spuEles = doc.select("div#plist > ul > li");
        for (Element spuEle : spuEles) {
            //该jd页面没有spu不再获取
            //获取sku
            Elements select = spuEle.select("li.ps-item");
            for (Element element : select) {
                Item item = new Item();
                long sku = Long.parseLong(element.select("[data-sku]").attr("data-sku"));
                item.setSku(sku);
                //查询该sku的商品是否已经在数据库中存在，如果不存在再存储
                List<Item> list = itemService.findAll(item);
                if(list.size() > 0){
                    continue;
                }

                //商品图片
                String imgSrc = "https:" + element.select("img[data-sku]").first().attr("src");
                String replace = imgSrc.replace("/n9/", "/n1/");
                //下载图片
                String picName = httpUtils.doGetImage(replace);
                item.setPic(picName);

                //商品详情地址
                String url = "https://item.jd.com/" + sku + ".html";
                item.setUrl(url);
                //商品价格
                String priceStr = this.httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuids=J_" + item.getSku());
                double price = MAPPER.readTree(priceStr).get(0).get("p").asDouble();
                item.setPrice(price);
                //商品标题
                String infoHtml = httpUtils.doGetImage(item.getUrl());
                Document itemDoc = Jsoup.parse(infoHtml);
                String title = itemDoc.select("div.sku-name").first().text();
                item.setTitle(title);
                //商品入库时间
                item.setCreated(new Date());
                //商品更新时间
                item.setUpdated(item.getCreated());
            }
        }

    }
}
