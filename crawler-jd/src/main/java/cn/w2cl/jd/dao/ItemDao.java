package cn.w2cl.jd.dao;

import cn.w2cl.jd.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Authror 卫骏
 * @Date 2020/1/20 10:29
 */
public interface ItemDao extends JpaRepository<Item,Long> {

}
