package com.jhmis.modules.index;

import com.jhmis.common.utils.WordUtil;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.utils.FreemarkerPager;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsExt;
import com.jhmis.modules.shop.entity.StoreGoods;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;


/**
 * 
 * <p>Title: LuceneUtil.java</p>
 * 
 * <p>Description: lucene工具</p>
 *
 * 
 * <p>============================================</p>
 * <p>Modification History
 * <p>Mender: </p>
 * <p>Date: </p>
 * <p>Reason: </p>
 * <p>============================================</p>
 */
@Component
public class IndexUtil {

	/**
	 * 全文检索索引路径
	 */

	public static  String INDEX_PATH ;


	@Value("${cms.index.path}")
	public  void setIndexPath(String indexPath) {
		IndexUtil.INDEX_PATH = indexPath;
	}


	/**
	 * 创建索引目录
	 * @param request
	 */
	public static void initFolder(HttpServletRequest request){
		File file=new File(IndexUtil.INDEX_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 创建信息类索引
	 * @param info
	 * @param request
	 * @throws Exception
	 */
	public static void createIndex(Info info, HttpServletRequest request) throws Exception {
		initFolder(request);
		if (info!=null) {
			File file=new File(INDEX_PATH+"/cmsinfo");
			createIndex(IndexDocument.createDocument(info), file,new IKAnalyzer());
		}
	}

	/**
	 * 删除信息类索引
	 */
	public static void deleteIndex(Info info, HttpServletRequest request)throws Exception{
		File file=new File(INDEX_PATH+"/cmsinfo");
		if (file.exists() && info!=null) {
			deleteIndex(info.getId(), file, new IKAnalyzer());
		}
	}

	/**
	 * 更新信息类索引
	 */
	public static void updateIndex(Info info, HttpServletRequest request)throws Exception{
		initFolder(request);
		if (info!=null) {
			//删除索引
			deleteIndex(info, request);
			//创建索引
			createIndex(info, request);
		}
	}

	/**
	 * 创建商品类索引
	 * @param goods
	 * @param request
	 * @throws Exception
	 */
	public static void createIndex(Goods goods, StoreGoods storeGoods, GoodsExt goodsExt, HttpServletRequest request) throws Exception {
		initFolder(request);
		if (goods!=null) {
			File file=new File(INDEX_PATH+"/product");
			createIndex(IndexDocument.createDocument(goods,storeGoods,goodsExt), file,new IKAnalyzer());
		}
	}

	/**
	 * 删除商品类索引
	 */
	public static void deleteIndex(Goods goods ,HttpServletRequest request)throws Exception{
		File file=new File(INDEX_PATH);
		if (file.exists() && goods!=null) {
			deleteIndex(goods.getCode(), file, new IKAnalyzer());
		}
	}

	/**
	 * 更新商品类索引
	 */
	public static void updateIndex(Goods goods, StoreGoods storeGoods, GoodsExt goodsExt, HttpServletRequest request)throws Exception{
		initFolder(request);
		if (goods!=null) {
			//删除索引
			deleteIndex(goods, request);
			//创建索引
			createIndex(goods,storeGoods,goodsExt, request);
		}
	}
	/**
	 * 创建索引
	 * @param file
	 * @param analyzer
	 * @throws IOException
	 */
	public static void createIndex(Document document, File file, Analyzer analyzer) throws Exception {
		Directory directory = null;
		IndexWriter iwriter = null;
		try {
			//建立索引对象
			directory = new SimpleFSDirectory(file);
			//配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(
					Version.LUCENE_34, analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory, iwConfig);
			//写入索引
			iwriter.addDocument(document);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iwriter != null) {
				try {
					iwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 删除索引
	 * @param file
	 * @param analyzer
	 * @throws IOException
	 */
	public static void deleteIndex(String id, File file, Analyzer analyzer) throws Exception {
		Directory directory = null;
		IndexWriter iwriter = null;
		try {
			//建立索引对象
			directory = new SimpleFSDirectory(file);
			//配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(
					Version.LUCENE_34, analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory, iwConfig);
			iwriter.deleteDocuments(new Term(IndexDocument.ID, id));
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iwriter != null) {
				try {
					iwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建查询对象
	 * @param queryString
	 * @param type
	 * @param categoryId
	 * @param shopCategoryId
	 * @param startDate
	 * @param endDate
	 * @param analyzer
	 * @return
	 * @throws ParseException
	 */
	public static Query createQuery(String queryString, String type,
                                    String categoryId, String shopCategoryId , Date startDate, Date endDate, Analyzer analyzer)
			throws ParseException {
		BooleanQuery bq = new BooleanQuery();
		Query q;
		if (!org.apache.commons.lang3.StringUtils.isBlank(queryString)) {
			q = MultiFieldQueryParser.parse(Version.LUCENE_34, queryString,
					IndexDocument.QUERY_FIELD, IndexDocument.QUERY_FLAGS, analyzer);
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(type)) {
			q = new TermQuery(new Term(IndexDocument.TYPE, type));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(categoryId)) {
			String[] categoryIds = categoryId.split(",");
			if (categoryIds.length==1){
				q = new TermQuery(new Term(IndexDocument.CATEGORY_ID, categoryId));
				bq.add(q, BooleanClause.Occur.MUST);
			} else {
				//tity 添加栏目逗号分割值为或者条件 2016/12/128
				BooleanQuery bqChannel = new BooleanQuery();
				for(String id:categoryIds){
					q = new TermQuery(new Term(IndexDocument.CATEGORY_ID, id));
					bqChannel.add(q, BooleanClause.Occur.SHOULD);
				}
				bq.add(bqChannel, BooleanClause.Occur.MUST);
			}

		}
		if (StringUtils.isNotEmpty(shopCategoryId)) {
			String[] shopCategoryIds = shopCategoryId.split(",");
			if(shopCategoryIds.length==1){
				q = new TermQuery(new Term(IndexDocument.SHOP_CATEGORY_ID, shopCategoryId));
				bq.add(q, BooleanClause.Occur.MUST);
			} else {
				//tity 添加栏目逗号分割值为或者条件 2016/12/128
				BooleanQuery bqCategory = new BooleanQuery();
				for(String id:shopCategoryIds){
					q = new TermQuery(new Term(IndexDocument.SHOP_CATEGORY_ID, id));
					bqCategory.add(q, BooleanClause.Occur.SHOULD);
				}
				bq.add(bqCategory, BooleanClause.Occur.MUST);
			}

		}
		if (startDate != null || endDate != null) {
			String start = null;
			String end = null;
			if (startDate != null) {
				start = DateTools.dateToString(startDate, Resolution.DAY);
			}
			if (endDate != null) {
				end = DateTools.dateToString(endDate, Resolution.DAY);
			}
			q = new TermRangeQuery(IndexDocument.ADDTIME, start, end, true, true);
			bq.add(q, BooleanClause.Occur.MUST);
		}
		return bq;
	}

	/**
	 * 查询索引
	 * @param queryString
	 * @param type
	 * @param categoryId
	 * @param shopCategoryId
	 * @param startDate
	 * @param endDate
	 * @param first
	 * @param max
	 * @param request
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<Document> searchList(String queryString, String type,
                                             String categoryId, String shopCategoryId, Date startDate, Date endDate,
                                            int first, int max, HttpServletRequest request) throws CorruptIndexException, IOException,
            ParseException {
		Directory dir = new SimpleFSDirectory(new File(request.getRealPath(INDEX_PATH)));
		return searchList(dir,type, queryString,categoryId,shopCategoryId, startDate,
				endDate, first, max);
	}

	/**
	 * 查询索引
	 * @param path
	 * @param type
	 * @param queryString
	 * @param categoryId
	 * @param shopCategoryId
	 * @param startDate
	 * @param endDate
	 * @param first
	 * @param max
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<Document> searchList(String path, String type, String queryString,
                                            String categoryId, String shopCategoryId, Date startDate, Date endDate,
                                            int first, int max) throws CorruptIndexException, IOException,
            ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		Searcher searcher = new IndexSearcher(dir);
		return searchList(dir,type, queryString, categoryId, shopCategoryId,startDate, endDate, first, max);
	}

	/**
	 * 查询索引
	 * @param dir
	 * @param type
	 * @param queryString
	 * @param categoryId
	 * @param shopCategoryId
	 * @param startDate
	 * @param endDate
	 * @param first
	 * @param max
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<Document> searchList(Directory dir, String type, String queryString,
                                             String categoryId,String shopCategoryId, Date startDate, Date endDate,
                                            int first, int max) throws CorruptIndexException, IOException,
            ParseException {
		Searcher searcher = new IndexSearcher(dir);
		try {
			Analyzer analyzer = new IKAnalyzer();
			Query query = createQuery(queryString,type,
					categoryId,shopCategoryId, startDate, endDate, analyzer);
			if (first < 0) {
				first = 0;
			}
			if (max < 0) {
				max = 0;
			}
			TopDocs docs = searcher.search(query, first + max);
			ScoreDoc[] hits = docs.scoreDocs;
			int last = first + max;
			int len = hits.length;
			if (last > len) {
				last = len;
			}
			List<Document> documentList=new ArrayList<Document>();
			for (int i = first; i < last; i++) {
				documentList.add(searcher.doc(hits[i].doc));
			}
			return documentList;
		} finally {
			searcher.close();
		}
	}

	/**
	 * 查询索引
	 * @param path
	 * @param type
	 * @param queryString
	 * @param categoryId
	 * @param shopCategoryId
	 * @param startDate
	 * @param endDate
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static FreemarkerPager searchPage(String path, String type, String queryString,
											 String categoryId, String shopCategoryId, Date startDate, Date endDate,
											 int currPage, int pageSize) throws CorruptIndexException, IOException,
            ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		return searchPage(dir,type, queryString, categoryId,shopCategoryId, startDate,
				endDate, currPage, pageSize);
	}

	/**
	 * 查询索引
	 * @param dir
	 * @param type
	 * @param queryString
	 * @param categoryId
	 * @param shopCategoryId
	 * @param startDate
	 * @param endDate
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static FreemarkerPager searchPage(Directory dir, String type, String queryString,String categoryId,String shopCategoryId, Date startDate, Date endDate,
                                             int currPage, int pageSize) throws CorruptIndexException, IOException,
            ParseException {
		Searcher searcher = new IndexSearcher(dir);
		try {
			FreemarkerPager pager=new FreemarkerPager();
			pager.setCurrPage(currPage);
			pager.setPageSize(pageSize);
			
			
			IKAnalyzer analyzer = new IKAnalyzer();
			Query query = createQuery(queryString,type,categoryId,shopCategoryId, startDate, endDate, analyzer);
			TopDocs docs = searcher.search(query, currPage * pageSize);
			pager.setTotalCount(docs.totalHits);
			ScoreDoc[] hits = docs.scoreDocs;
			int endIndex = currPage * pageSize;
			int len = hits.length;
			if (endIndex > len) {
				endIndex = len;
			}
			List<Document> documentList=new ArrayList<Document>();
			for (int i = (currPage - 1) * pageSize; i < endIndex; i++) {
				documentList.add(searcher.doc(hits[i].doc));
			}
			pager.setList(documentList);
			return pager;
		} finally {
			searcher.close();
		}
	}
	/**
	 * 获取关键字
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static String[] getKeyWord(String text) throws IOException {
		return WordUtil.getKeyWords(text);
	}
	public static int strlen(String text) {
		return strlen(text, "UTF-8");
	}
	public static int strlen(String text, String charsetName) {
		if (text == null || text.length() == 0) {
			return 0;
		}
		int length = 0;
		try {
			length = text.getBytes(charsetName).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}
	public static Entry<String, Float>[] getSortedHashtableByValue(Map<String, Float> h) {
		Set<Entry<String, Float>> set = h.entrySet();
		Entry<String, Float>[] entries = set.toArray(new Entry[set.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Entry entry1 = (Entry) arg0;
				Entry entry2 = (Entry) arg1;
				Float value1 = (Float) entry1.getValue();
				Float value2 = (Float) entry2.getValue();
				int size = value2.compareTo(value1);
				if (size == 0) {
					String key1 = (String) entry1.getKey();
					String key2 = (String) entry2.getKey();
					return key1.compareTo(key2);
				}
				return size;
			}
		});
		return entries;
	}
}
