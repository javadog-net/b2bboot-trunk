/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 信息管理Entity
 * @author lydia
 */
public class Info extends DataEntity<Info> {
	
	private static final long serialVersionUID = 1L;
	private String modelId;		// 模型ID
	private String categoryId;		// 栏目ID
	private String title;		// 标题
	private String titleColor;		// 标题颜色
	private String titleBlod;		// 标题是否加粗
	private String shortTitle;		// 短标题
	private String description;  //摘要
	private String thumb;		// 缩略图
	private String image;		// 主图
	private String author;		// 作者
	private String keywords;		// 关键词
	private String content;		// 内容
	private String source;		// 来源
	private String attchs;		// 附件（多个附件英文逗号分割）
	private String video;		// 视频文件地址
	private String htmlpath;		// 静态化生成html的路径
	private String posids;		// 推荐位
	private String status;		// {0:未审核,-1:未通过审核,1:通过审核}
	private String isLink;		// 是否转向连接（0否1是）
	private String isHot;		// 是否热点（0否1是）
	private String isTop;		// 是否置顶（0否1是）
	private String isRecommend;		// 是否推荐（0否1是）
	private String url;		// 转向链接地址
	private String sort;		// 序号
	private String releaseType;		// 1 只在PC发布 2 只在手机发布 3 在手机和PC上发布
	private String contentTemplate;		// 内容模板
	private String pageMark;		// 页面标识（可以通过该标识获取内容
	private String fileName;		// 静态化生成的文件名称（选填）
	private String allowComment;		// 是否允许评论 0否1是
	private int hits;		// 点击数
	private String tdkKeywords;		// 关键字
	private String tdkTitle;		// 标题
	private String tdkDescp;		// 描述
	private int htmlIndexnum; //生成文件的索引号
	private String images;  //图片集
	private String dateFormat;
	private String createDateStr;
	private String caseProducts; //案例所属产品
	private String industry; //案例所属行业
	private Category category; //栏目

	private String startDate; //仅做查询
	private String  endDate; //仅做查询

	private String [] categoryIds; //栏目ID
	private List<Comment> listComment;//评论
	private Map<String,Object> data;

	public List<Comment> getListComment() {
		return listComment;
	}

	public void setListComment(List<Comment> listComment) {
		this.listComment = listComment;
	}

	private Integer infoStartHits;  //非数据库字段 标签中遇到 点击量
	private Integer infoEndHits;  //非数据库字段 标签中遇到 点击量
	private Date infostarttimeNoeq; // 取上一篇标签时使用
	private Date infoendtimeNoeq;  //取下一篇标签时使用
	private String orderBySql;//动态排序
	private String family;// 文章类别
	private String familyLevel2;// 产品讲坛一级类别
    private Integer commentNum;//评论数
	private Integer isLikeNum;//点赞数（喜欢数量）
    private String isLike;//是否点赞的标识 1是 0否

	private List<Info> htmlInfoList; //静态化内容列表

	//仅做显示
	private String contextPath;

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getIsLikeNum() {
		return isLikeNum;
	}

	public void setIsLikeNum(Integer isLikeNum) {
		this.isLikeNum = isLikeNum;
	}

	public String getFamilyLevel2() {
		return familyLevel2;
	}

	public void setFamilyLevel2(String familyLevel2) {
		this.familyLevel2 = familyLevel2;
	}

	public String getFamilyLevel3() {
		return familyLevel3;
	}

	public void setFamilyLevel3(String familyLevel3) {
		this.familyLevel3 = familyLevel3;
	}

	private String familyLevel3;// 产品讲坛二级类别

	private List<String> categoryIdList ; // freemarker 标签时使用
	private String titleSuffix;//标题超过显示长度时加的后缀
	private int shortTitleLen; //freemarker标签使用  显示的标题长度
	private Integer newDays ;//freemarker标签使用  所少天內的文章信息
	private String showTitle; //freemarker标签使用 显示标题

	private String pageUrl;
	private String htmlFileName;
	private List<InfoProduct> infoProductList;  //文章关联商品列表
	private List<InfoProduct> oldInfoProductList;// 更新关联商品

	
	public Info() {
		super();
	}

	public Info(String id){
		super(id);
	}

	@ExcelField(title="模型ID", dictType="", align=2, sort=1)
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@ExcelField(title="栏目ID", align=2, sort=2)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="标题", align=2, sort=3)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="标题颜色", align=2, sort=4)
	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}
	
	@ExcelField(title="标题是否加粗", align=2, sort=5)
	public String getTitleBlod() {
		return titleBlod;
	}

	public void setTitleBlod(String titleBlod) {
		this.titleBlod = titleBlod;
	}
	
	@ExcelField(title="短标题", align=2, sort=6)
	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getShowTitle() {
		if(StringUtils.isEmpty(shortTitle)){
			showTitle = title;
		}
		if(shortTitleLen != 0 && shortTitle.length()>shortTitleLen){
			showTitle = shortTitle.substring(0,shortTitleLen)+titleSuffix;
		}else{
			showTitle = shortTitle;
		}
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	@ExcelField(title="缩略图", align=2, sort=7)
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	@ExcelField(title="主图", align=2, sort=8)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@ExcelField(title="作者", align=2, sort=9)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@ExcelField(title="关键词", align=2, sort=10)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	@ExcelField(title="内容", align=2, sort=11)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="来源", align=2, sort=12)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@ExcelField(title="附件（多个附件英文逗号分割）", align=2, sort=14)
	public String getAttchs() {
		return attchs;
	}

	public void setAttchs(String attchs) {
		this.attchs = attchs;
	}
	@ExcelField(title="视频文件地址", align=2, sort=16)
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	@ExcelField(title="静态化生成html的路径", align=2, sort=17)
	public String getHtmlpath() {
		return htmlpath;
	}

	public void setHtmlpath(String htmlpath) {
		this.htmlpath = htmlpath;
	}
	
	@ExcelField(title="推荐位", dictType="", align=2, sort=18)
	public String getPosids() {
		return posids;
	}

	public void setPosids(String posids) {
		this.posids = posids;
	}
	
	@ExcelField(title="{0:未审核,-1:未通过审核,1:通过审核}", dictType="", align=2, sort=19)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="是否转向连接（0否1是）", dictType="yes_no", align=2, sort=20)
	public String getIsLink() {
		return isLink;
	}

	public void setIsLink(String isLink) {
		this.isLink = isLink;
	}
	
	@ExcelField(title="是否热点（0否1是）", dictType="yes_no", align=2, sort=21)
	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	
	@ExcelField(title="是否置顶（0否1是）", dictType="yes_no", align=2, sort=22)
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	
	@ExcelField(title="是否推荐（0否1是）", dictType="yes_no", align=2, sort=23)
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	@ExcelField(title="转向链接地址", align=2, sort=24)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="序号", align=2, sort=25)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="1 只在PC发布 2 只在手机发布 3 在手机和PC上发布", align=2, sort=26)
	public String getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}
	
	@ExcelField(title="内容模板", align=2, sort=27)
	public String getContentTemplate() {
		return contentTemplate;
	}

	public void setContentTemplate(String contentTemplate) {
		this.contentTemplate = contentTemplate;
	}
	
	@ExcelField(title="页面标识（可以通过该标识获取内容", align=2, sort=28)
	public String getPageMark() {
		return pageMark;
	}

	public void setPageMark(String pageMark) {
		this.pageMark = pageMark;
	}
	
	@ExcelField(title="静态化生成的文件名称（选填）", align=2, sort=29)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@ExcelField(title="是否允许评论 0否1是", align=2, sort=30)
	public String getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(String allowComment) {
		this.allowComment = allowComment;
	}
	
	@ExcelField(title="点击数", align=2, sort=32)
	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}
	
	@ExcelField(title="关键字", align=2, sort=33)
	public String getTdkKeywords() {
		return tdkKeywords;
	}

	public void setTdkKeywords(String tdkKeywords) {
		this.tdkKeywords = tdkKeywords;
	}
	
	@ExcelField(title="标题", align=2, sort=34)
	public String getTdkTitle() {
		return tdkTitle;
	}

	public void setTdkTitle(String tdkTitle) {
		this.tdkTitle = tdkTitle;
	}
	
	@ExcelField(title="描述", align=2, sort=35)
	public String getTdkDescp() {
		return tdkDescp;
	}

	public void setTdkDescp(String tdkDescp) {
		this.tdkDescp = tdkDescp;
	}

	public int getHtmlIndexnum() {
		return htmlIndexnum;
	}

	public void setHtmlIndexnum(int htmlIndexnum) {
		this.htmlIndexnum = htmlIndexnum;
	}
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Date getInfostarttimeNoeq() {
		return infostarttimeNoeq;
	}

	public void setInfostarttimeNoeq(Date infostarttimeNoeq) {
		this.infostarttimeNoeq = infostarttimeNoeq;
	}

	public Date getInfoendtimeNoeq() {
		return infoendtimeNoeq;
	}

	public void setInfoendtimeNoeq(Date infoendtimeNoeq) {
		this.infoendtimeNoeq = infoendtimeNoeq;
	}

	public String getOrderBySql() {
		return orderBySql;
	}

	public void setOrderBySql(String orderBySql) {
		this.orderBySql = orderBySql;
	}

	public List<String> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<String> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTitleSuffix() {
		return titleSuffix;
	}

	public void setTitleSuffix(String titleSuffix) {
		this.titleSuffix = titleSuffix;
	}

	public int getShortTitleLen() {
		return shortTitleLen;
	}

	public void setShortTitleLen(int shortTitleLen) {
		this.shortTitleLen = shortTitleLen;
	}

	public Integer getNewDays() {
		return newDays;
	}

	public void setNewDays(Integer newDays) {
		this.newDays = newDays;
	}

	public Integer getInfoStartHits() {
		return infoStartHits;
	}

	public void setInfoStartHits(Integer infoStartHits) {
		this.infoStartHits = infoStartHits;
	}

	public Integer getInfoEndHits() {
		return infoEndHits;
	}

	public void setInfoEndHits(Integer infoEndHits) {
		this.infoEndHits = infoEndHits;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getCreateDateStr() {
		if (dateFormat==null || dateFormat.trim().length()==0) {
			dateFormat="yyyy-MM-dd HH:mm:ss";
		}
		if (createDate!=null) {
			createDateStr=new SimpleDateFormat(dateFormat).format(createDate);
		}
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getPageUrl() {
		//判断是否有外部链接
		if (url!=null && url.trim().length()>0) {
			return pageUrl = url;
		}else if (StringUtils.isNotEmpty(htmlpath)) {
			return pageUrl=htmlpath;
		}else {
			if(getHtmlFileName() != null){
				return pageUrl="/info/"+(getCreateDate().getYear()+1900)+"/"+getHtmlFileName()+".html";
			}
		}
		return "";
	}
	public String getHtmlFileName() {
		if (htmlIndexnum>0) {
			htmlFileName=String.valueOf(htmlIndexnum);
		}else {
			htmlFileName=id;
		}
		return htmlFileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<InfoProduct> getInfoProductList() {
		return infoProductList;
	}

	public void setInfoProductList(List<InfoProduct> infoProductList) {
		this.infoProductList = infoProductList;
	}

	public List<InfoProduct> getOldInfoProductList() {
		return oldInfoProductList;
	}

	public void setOldInfoProductList(List<InfoProduct> oldInfoProductList) {
		this.oldInfoProductList = oldInfoProductList;
	}

	public String getCaseProducts() {
		return caseProducts;
	}

	public void setCaseProducts(String caseProducts) {
		this.caseProducts = caseProducts;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Info> getHtmlInfoList() {
		return htmlInfoList;
	}

	public void setHtmlInfoList(List<Info> htmlInfoList) {
		this.htmlInfoList = htmlInfoList;
	}
}