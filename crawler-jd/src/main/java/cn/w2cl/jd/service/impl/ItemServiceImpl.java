package cn.w2cl.jd.service.impl;

import cn.w2cl.jd.dao.ItemDao;
import cn.w2cl.jd.pojo.Item;
import cn.w2cl.jd.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Authror 卫骏
 * @Date 2020/1/20 10:43
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    public void save(Item item) {
        itemDao.save(item);
    }

    @Override
    public List<Item> findAll(Item item) {
        //声明查询条件
        Example<Item> example = Example.of(item);
        //根据查询条件查询商品集合
        List<Item> items = itemDao.findAll(example);

        return items;
    }
}
