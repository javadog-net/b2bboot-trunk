package com.jhmis.modules.shop.service;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.purchasergoodsrel.entity.PurchaserGoodsRel;
import com.jhmis.modules.purchasergoodsrel.service.PurchaserGoodsRelService;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.mapper.GoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 可采商品
 */
@Service
@Transactional(readOnly = true)
public class CortryGoodsService extends CrudService<GoodsMapper, Goods> {

    @Resource
    private PurchaserGoodsRelService purchaserGoodsRelService;

    @Resource
    private StoreGoodsService storeGoodsService;


    public Goods get(String id) {
        return super.get(id);
    }

    public Goods findByCode(String code) {
        return super.mapper.findByCode(code);
    }

    public List<Goods> findList(Goods goods) {
        return super.findList(goods);
    }

    public Page<Goods> findPage(Page<Goods> page, Goods goods) {
        return super.findPage(page, goods);
    }


    @Transactional(readOnly = false)
    public AjaxJson search(String code, String name, String brand,Page page,String purchaserId) {
        Goods goods = new Goods();
        if (StringUtils.isBlank(code)){
            return AjaxJson.fail("产品账号不能为空");
        }
        if (StringUtils.isBlank(name)){
            return AjaxJson.fail("产品名称不能为空");
        }
        if (StringUtils.isBlank(brand)){
            return AjaxJson.fail("产品品牌不能为空");
        }
        goods.setCode(code);
        goods.setName(name);
        goods.setBrand(brand);
        //采购商可采商品Entity
        PurchaserGoodsRel purchaserGoodsRel = new PurchaserGoodsRel();
        //  商品账号不为空
        if (StringUtils.isNotEmpty(code)){
            purchaserGoodsRel.setGoodsSku(code);
        }
        purchaserGoodsRel.setPurchaserId(purchaserId);
        page = purchaserGoodsRelService.findPage(page, purchaserGoodsRel);
        Map<String ,Double> map = new HashMap<String ,Double>();
        if (page!=null &&page.getList()!=null&& page.getList().size()>0){
            List<PurchaserGoodsRel> list = page.getList();

            List<String> ls = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                PurchaserGoodsRel p = list.get(i);
                String sku = p.getGoodsSku();
                double exclusivePrice = p.getExclusivePrice();
                ls.add(sku);
               map.put(sku,exclusivePrice);
            }
            StoreGoods storeGoods = new StoreGoods();
            storeGoods.setIdList(ls);
            if (StringUtils.isNotEmpty(name)){
                storeGoods.setGoodsName(name);
            }
            if (StringUtils.isNotEmpty(code)){
                storeGoods.setBrand(brand);
            }

            page  = storeGoodsService.findPage(page, storeGoods);
            if (page!=null &&page.getList()!=null&& page.getList().size()>0){
                List<StoreGoods> lt = page.getList();
                for (int i = 0; i < lt.size(); i++) {
                    StoreGoods sg = lt.get(i);
                    if (StringUtils.isNotEmpty(sg.getCode())){
                        Double price = map.get(sg.getCode());
                        sg.setPrice(price);
                    }
                }
            }
        }
        return AjaxJson.layuiTable(page);
    }
}
