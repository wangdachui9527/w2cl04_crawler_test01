package cn.w2cl.jd.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * @Authror 卫骏
 * @Date 2020/1/20 9:55
 */
@Entity
@Table(name = "jd_item")
public class Item {
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long spu; //标准产品单位
    private Long sku; //库存量单位，最小品类单位
    private String title; //商品标题
    private Double price; //商品价格
    private String pic; //商品图片
    private String url; //商品详情地址
    private Date created; //创建时间
    private Date updated; //更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpu() {
        return spu;
    }

    public void setSpu(Long spu) {
        this.spu = spu;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
