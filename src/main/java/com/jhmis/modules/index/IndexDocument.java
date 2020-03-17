package com.jhmis.modules.index;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsExt;
import com.jhmis.modules.shop.entity.StoreGoods;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.BooleanClause;

import java.util.Date;


/**
 * 
 * <p>Title: LuceneDocument.java</p>
 * 
 * <p>Description: lucene封装对象</p>
 * 
 * <p>Date: 2014-03-03</p>
 * 
 * <p>Time: 12:13:56 PM</p>
 * 
 * <p>Copyright: 2014</p>
 * 
 * <p>Company: b2b</p>
 * 
 * @author tity
 * @version 1.0
 * 
 * <p>============================================</p>
 * <p>Modification History
 * <p>Mender: </p>
 * <p>Date: </p>
 * <p>Reason: </p>
 * <p>============================================</p>
 */
public class IndexDocument {
	
	public static final String ID = "id";
	public static final String CATEGORY_ID = "categoryId";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String SHOP_CATEGORY_ID = "shopCategoryId";
	public static final String ADDTIME = "addtime";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String DESCRIPTION = "description";
	public static final String TAGS = "tags";
	public static final  String GOODS_CODE="goodscode";
	public static final String TYPE = "type";//对象类型,info表示信息,product表示商品
	public static final String[] QUERY_FIELD = { TITLE, CONTENT,DESCRIPTION,TAGS };//需要搜索的字段
	public static final BooleanClause.Occur[] QUERY_FLAGS = {
			BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
			BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD };//多个字段之间使用or

	/**
	 * 封装信息对象
	 * 
	 * @return
	 */
	public static Document createDocument(Info info) {
		Document doc = new Document();
		doc.add(new Field(ID, info.getId(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field(TYPE, "info",
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(ADDTIME, DateTools.dateToString(info.getCreateDate(), Resolution.DAY), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field(CATEGORY_ID, info.getCategoryId(),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		if(info != null && info.getCategory() != null && StringUtils.isNotEmpty(info.getCategory().getName())){
			doc.add(new Field(CATEGORY_NAME, info.getCategory().getName(), Field.Store.NO,
					Field.Index.ANALYZED));
		}
		doc.add(new Field(TITLE, info.getTitle(), Field.Store.NO,
				Field.Index.ANALYZED));
		if (!org.apache.commons.lang3.StringUtils.isBlank(info.getContent())) {
			doc.add(new Field(CONTENT, info.getContent(), Field.Store.NO,
					Field.Index.ANALYZED));
		}
		if (!org.apache.commons.lang3.StringUtils.isBlank(info.getDescription())) {
			doc.add(new Field(DESCRIPTION, info.getDescription(), Field.Store.NO,
					Field.Index.ANALYZED));
		}
//		if (!org.apache.commons.lang3.StringUtils.isBlank(info.getTags())) {
//			doc.add(new Field(TAGS, info.getTags(), Field.Store.NO,
//					Field.Index.ANALYZED));
//		}
		return doc;
	}
	/**
	 * 封装商品对象
	 * 
	 * @return
	 */
	public static Document createDocument(Goods goods, StoreGoods storeGoods, GoodsExt goodsExt) {
		Document doc = new Document();
		doc.add(new Field(ID, storeGoods.getId(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field(TYPE, "goods",
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		if(null != storeGoods.getShelfTime()) {
			doc.add(new Field(ADDTIME, DateTools.dateToString(storeGoods.getShelfTime(), Resolution.DAY), Field.Store.NO,
					Field.Index.NOT_ANALYZED));

		}else{
			doc.add(new Field(ADDTIME, DateTools.dateToString(new Date(), Resolution.DAY), Field.Store.NO,
					Field.Index.NOT_ANALYZED));
		}
		doc.add(new Field(CATEGORY_ID, storeGoods.getCategoryId(),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field(TITLE, storeGoods.getGoodsName(), Field.Store.NO,
				Field.Index.ANALYZED));
		doc.add(new Field(GOODS_CODE, goods.getCode(), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		if (!StringUtils.isBlank(goods.getDes())) {
			doc.add(new Field(DESCRIPTION, goods.getKeyword()+goods.getTitle()+goods.getDes(), Field.Store.NO,
					Field.Index.ANALYZED));
		}
		return doc;
	}
}
