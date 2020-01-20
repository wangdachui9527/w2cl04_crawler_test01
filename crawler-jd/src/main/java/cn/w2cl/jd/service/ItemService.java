package cn.w2cl.jd.service;

import cn.w2cl.jd.pojo.Item;

import java.util.List;

/**
 * @Authror 卫骏
 * @Date 2020/1/20 10:35
 */
public interface ItemService {

    /**
     * 保存商品
     * @param item
     */
    public void save(Item item);

    /**
     * 根据条件查询商品
     * @param item
     * @return
     */
    public List<Item> findAll(Item item);
}
