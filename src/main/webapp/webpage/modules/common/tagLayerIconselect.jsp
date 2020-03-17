<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>图标选择</title>
	<meta name="decorator" content="blank"/>
    <link href="${ctxStatic}/plugin/layui/dist/css/layui-icon.css" rel="stylesheet" />
    <style type="text/css">
    	.page-header {clear:both;margin:0 20px;padding-top:20px;}
        .site-doc-icon {
            margin-bottom: 50px;
            font-size: 0;
        }
        .site-doc-icon li {
            display: inline-block;
            vertical-align: middle;
            width: 127px;
            height: 105px;
            line-height: 25px;
            padding: 20px 0;
            margin-right: -1px;
            margin-bottom: -1px;
            border: 1px solid #e2e2e2;
            font-size: 14px;
            text-align: center;
            color: #666;
            transition: all .3s;
            -webkit-transition: all .3s;
        }
        .site-doc-icon li .layui-icon {
            display: inline-block;
            font-size: 36px;
        }
        .site-doc-icon li .doc-icon-name, .site-doc-icon li .doc-icon-code {
            color: #c2c2c2;
        }
        .site-doc-icon li .doc-icon-fontclass {
            height: 40px;
            line-height: 20px;
            padding: 0 5px;
            font-size: 13px;
            color: #333;
        }

		.site-doc-icon li:hover {background-color:#efefef;}
        .site-doc-icon li.active {background-color:#0088CC;color:#ffffff;}
    </style>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	$("#icons li").click(function(){
	    		$("#icons li").removeClass("active");
	    		$("#icons li i").removeClass("icon-white");
	    		$(this).addClass("active");
	    		$(this).children("i").addClass("icon-white");
	    		$("#icon").val($(this).find('i').attr('class').split(" ")[0]+' '+$(this).find('i').attr('class').split(" ")[1]);
	    	});
	    	$("#icons li").each(function(){
	    		if ($(this).find('i').attr('class')=="${value}"){
	    			$(this).click();
	    		}
	    	});
	    });
    </script>
</head>
<body>
<input type="hidden" id="icon" value="${value}" />
<div id="icons">
    <ul class="site-doc-icon">
        <li>
            <i class="layui-icon layui-icon-rate-half"></i>
            <div class="doc-icon-name">半星</div>
            <div class="doc-icon-code">&amp;#xe6c9;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-rate"></i>
            <div class="doc-icon-name">星星-空心</div>
            <div class="doc-icon-code">&amp;#xe67b;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-rate-solid"></i>
            <div class="doc-icon-name">星星-实心</div>
            <div class="doc-icon-code">&amp;#xe67a;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-cellphone"></i>
            <div class="doc-icon-name">手机</div>
            <div class="doc-icon-code">&amp;#xe678;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-vercode"></i>
            <div class="doc-icon-name">验证码</div>
            <div class="doc-icon-code">&amp;#xe679;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-login-wechat"></i>
            <div class="doc-icon-name">微信</div>
            <div class="doc-icon-code">&amp;#xe677;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-login-qq"></i>
            <div class="doc-icon-name">QQ</div>
            <div class="doc-icon-code">&amp;#xe676;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-login-weibo"></i>
            <div class="doc-icon-name">微博</div>
            <div class="doc-icon-code">&amp;#xe675;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-password"></i>
            <div class="doc-icon-name">密码</div>
            <div class="doc-icon-code">&amp;#xe673;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-username"></i>
            <div class="doc-icon-name">用户名</div>
            <div class="doc-icon-code">&amp;#xe66f;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-refresh-3"></i>
            <div class="doc-icon-name">刷新-粗</div>
            <div class="doc-icon-code">&amp;#xe9aa;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-auz"></i>
            <div class="doc-icon-name">授权</div>
            <div class="doc-icon-code">&amp;#xe672;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-spread-left"></i>
            <div class="doc-icon-name">左向右伸缩菜单</div>
            <div class="doc-icon-code">&amp;#xe66b;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-shrink-right"></i>
            <div class="doc-icon-name">右向左伸缩菜单</div>
            <div class="doc-icon-code">&amp;#xe668;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-snowflake"></i>
            <div class="doc-icon-name">雪花</div>
            <div class="doc-icon-code">&amp;#xe6b1;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-tips"></i>
            <div class="doc-icon-name">提示说明</div>
            <div class="doc-icon-code">&amp;#xe702;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-note"></i>
            <div class="doc-icon-name">便签</div>
            <div class="doc-icon-code">&amp;#xe66e;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-home"></i>
            <div class="doc-icon-name">主页</div>
            <div class="doc-icon-code">&amp;#xe68e;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-senior"></i>
            <div class="doc-icon-name">高级</div>
            <div class="doc-icon-code">&amp;#xe674;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-refresh"></i>
            <div class="doc-icon-name">刷新</div>
            <div class="doc-icon-code">&amp;#xe669;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-refresh-1"></i>
            <div class="doc-icon-name">刷新</div>
            <div class="doc-icon-code">&amp;#xe666;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-flag"></i>
            <div class="doc-icon-name">旗帜</div>
            <div class="doc-icon-code">&amp;#xe66c;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-theme"></i>
            <div class="doc-icon-name">主题</div>
            <div class="doc-icon-code">&amp;#xe66a;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-notice"></i>
            <div class="doc-icon-name">消息-通知</div>
            <div class="doc-icon-code">&amp;#xe667;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-website"></i>
            <div class="doc-icon-name">网站</div>
            <div class="doc-icon-code">&amp;#xe7ae;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-console"></i>
            <div class="doc-icon-name">控制台</div>
            <div class="doc-icon-code">&amp;#xe665;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-face-surprised"></i>
            <div class="doc-icon-name">表情-惊讶</div>
            <div class="doc-icon-code">&amp;#xe664;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-set"></i>
            <div class="doc-icon-name">设置-空心</div>
            <div class="doc-icon-code">&amp;#xe716;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-template-1"></i>
            <div class="doc-icon-name">模板</div>
            <div class="doc-icon-code">&amp;#xe656;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-app"></i>
            <div class="doc-icon-name">应用</div>
            <div class="doc-icon-code">&amp;#xe653;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-template"></i>
            <div class="doc-icon-name">模板</div>
            <div class="doc-icon-code">&amp;#xe663;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-praise"></i>
            <div class="doc-icon-name">赞</div>
            <div class="doc-icon-code">&amp;#xe6c6;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-tread"></i>
            <div class="doc-icon-name">踩</div>
            <div class="doc-icon-code">&amp;#xe6c5;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-male"></i>
            <div class="doc-icon-name">男</div>
            <div class="doc-icon-code">&amp;#xe662;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-female"></i>
            <div class="doc-icon-name">女</div>
            <div class="doc-icon-code">&amp;#xe661;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-camera"></i>
            <div class="doc-icon-name">相机-空心</div>
            <div class="doc-icon-code">&amp;#xe660;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-camera-fill"></i>
            <div class="doc-icon-name">相机-实心</div>
            <div class="doc-icon-code">&amp;#xe65d;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-more"></i>
            <div class="doc-icon-name">菜单-水平</div>
            <div class="doc-icon-code">&amp;#xe65f;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-more-vertical"></i>
            <div class="doc-icon-name">菜单-垂直</div>
            <div class="doc-icon-code">&amp;#xe671;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-rmb"></i>
            <div class="doc-icon-name">金额-人民币</div>
            <div class="doc-icon-code">&amp;#xe65e;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-dollar"></i>
            <div class="doc-icon-name">金额-美元</div>
            <div class="doc-icon-code">&amp;#xe659;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-diamond"></i>
            <div class="doc-icon-name">钻石-等级</div>
            <div class="doc-icon-code">&amp;#xe735;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-fire"></i>
            <div class="doc-icon-name">火</div>
            <div class="doc-icon-code">&amp;#xe756;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-return"></i>
            <div class="doc-icon-name">返回</div>
            <div class="doc-icon-code">&amp;#xe65c;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-location"></i>
            <div class="doc-icon-name">位置-地图</div>
            <div class="doc-icon-code">&amp;#xe715;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-read"></i>
            <div class="doc-icon-name">办公-阅读</div>
            <div class="doc-icon-code">&amp;#xe705;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-survey"></i>
            <div class="doc-icon-name">调查</div>
            <div class="doc-icon-code">&amp;#xe6b2;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-face-smile"></i>
            <div class="doc-icon-name">表情-微笑</div>
            <div class="doc-icon-code">&amp;#xe6af;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-face-cry"></i>
            <div class="doc-icon-name">表情-哭泣</div>
            <div class="doc-icon-code">&amp;#xe69c;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-cart-simple"></i>
            <div class="doc-icon-name">购物车</div>
            <div class="doc-icon-code">&amp;#xe698;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-cart"></i>
            <div class="doc-icon-name">购物车</div>
            <div class="doc-icon-code">&amp;#xe657;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-next"></i>
            <div class="doc-icon-name">下一页</div>
            <div class="doc-icon-code">&amp;#xe65b;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-prev"></i>
            <div class="doc-icon-name">上一页</div>
            <div class="doc-icon-code">&amp;#xe65a;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-upload-drag"></i>
            <div class="doc-icon-name">上传-空心-拖拽</div>
            <div class="doc-icon-code">&amp;#xe681;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-upload"></i>
            <div class="doc-icon-name">上传-实心</div>
            <div class="doc-icon-code">&amp;#xe67c;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-download-circle"></i>
            <div class="doc-icon-name">下载-圆圈</div>
            <div class="doc-icon-code">&amp;#xe601;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-component"></i>
            <div class="doc-icon-name">组件</div>
            <div class="doc-icon-code">&amp;#xe857;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-file-b"></i>
            <div class="doc-icon-name">文件-粗</div>
            <div class="doc-icon-code">&amp;#xe655;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-user"></i>
            <div class="doc-icon-name">用户</div>
            <div class="doc-icon-code">&amp;#xe770;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-find-fill"></i>
            <div class="doc-icon-name">发现-实心</div>
            <div class="doc-icon-code">&amp;#xe670;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-loading layui-icon layui-anim layui-anim-rotate layui-anim-loop"></i>
            <div class="doc-icon-name">loading</div>
            <div class="doc-icon-code">&amp;#xe63d;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-loading-1 layui-icon layui-anim layui-anim-rotate layui-anim-loop"></i>
            <div class="doc-icon-name">loading</div>
            <div class="doc-icon-code">&amp;#xe63e;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-add-1"></i>
            <div class="doc-icon-name">添加</div>
            <div class="doc-icon-code">&amp;#xe654;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-play"></i>
            <div class="doc-icon-name">播放</div>
            <div class="doc-icon-code">&amp;#xe652;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-pause"></i>
            <div class="doc-icon-name">暂停</div>
            <div class="doc-icon-code">&amp;#xe651;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-headset"></i>
            <div class="doc-icon-name">音频-耳机</div>
            <div class="doc-icon-code">&amp;#xe6fc;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-video"></i>
            <div class="doc-icon-name">视频</div>
            <div class="doc-icon-code">&amp;#xe6ed;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-voice"></i>
            <div class="doc-icon-name">语音-声音</div>
            <div class="doc-icon-code">&amp;#xe688;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-speaker"></i>
            <div class="doc-icon-name">消息-通知-喇叭</div>
            <div class="doc-icon-code">&amp;#xe645;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-fonts-del"></i>
            <div class="doc-icon-name">删除线</div>
            <div class="doc-icon-code">&amp;#xe64f;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-fonts-code"></i>
            <div class="doc-icon-name">代码</div>
            <div class="doc-icon-code">&amp;#xe64e;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-fonts-html"></i>
            <div class="doc-icon-name">HTML</div>
            <div class="doc-icon-code">&amp;#xe64b;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-fonts-strong"></i>
            <div class="doc-icon-name">字体加粗</div>
            <div class="doc-icon-code">&amp;#xe62b;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-unlink"></i>
            <div class="doc-icon-name">删除链接</div>
            <div class="doc-icon-code">&amp;#xe64d;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-picture"></i>
            <div class="doc-icon-name">图片</div>
            <div class="doc-icon-code">&amp;#xe64a;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-link"></i>
            <div class="doc-icon-name">链接</div>
            <div class="doc-icon-code">&amp;#xe64c;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-face-smile-b"></i>
            <div class="doc-icon-name">表情-笑-粗</div>
            <div class="doc-icon-code">&amp;#xe650;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-align-left"></i>
            <div class="doc-icon-name">左对齐</div>
            <div class="doc-icon-code">&amp;#xe649;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-align-right"></i>
            <div class="doc-icon-name">右对齐</div>
            <div class="doc-icon-code">&amp;#xe648;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-align-center"></i>
            <div class="doc-icon-name">居中对齐</div>
            <div class="doc-icon-code">&amp;#xe647;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-fonts-u"></i>
            <div class="doc-icon-name">字体-下划线</div>
            <div class="doc-icon-code">&amp;#xe646;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-fonts-i"></i>
            <div class="doc-icon-name">字体-斜体</div>
            <div class="doc-icon-code">&amp;#xe644;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-tabs"></i>
            <div class="doc-icon-name">Tabs 选项卡</div>
            <div class="doc-icon-code">&amp;#xe62a;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-radio"></i>
            <div class="doc-icon-name">单选框-选中</div>
            <div class="doc-icon-code">&amp;#xe643;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-circle"></i>
            <div class="doc-icon-name">单选框-候选</div>
            <div class="doc-icon-code">&amp;#xe63f;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-edit"></i>
            <div class="doc-icon-name">编辑</div>
            <div class="doc-icon-code">&amp;#xe642;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-share"></i>
            <div class="doc-icon-name">分享</div>
            <div class="doc-icon-code">&amp;#xe641;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-delete"></i>
            <div class="doc-icon-name">删除</div>
            <div class="doc-icon-code">&amp;#xe640;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-form"></i>
            <div class="doc-icon-name">表单</div>
            <div class="doc-icon-code">&amp;#xe63c;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-cellphone-fine"></i>
            <div class="doc-icon-name">手机-细体</div>
            <div class="doc-icon-code">&amp;#xe63b;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-dialogue"></i>
            <div class="doc-icon-name">聊天 对话 沟通</div>
            <div class="doc-icon-code">&amp;#xe63a;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-fonts-clear"></i>
            <div class="doc-icon-name">文字格式化</div>
            <div class="doc-icon-code">&amp;#xe639;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-layer"></i>
            <div class="doc-icon-name">窗口</div>
            <div class="doc-icon-code">&amp;#xe638;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-date"></i>
            <div class="doc-icon-name">日期</div>
            <div class="doc-icon-code">&amp;#xe637;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-water"></i>
            <div class="doc-icon-name">水 下雨</div>
            <div class="doc-icon-code">&amp;#xe636;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-code-circle"></i>
            <div class="doc-icon-name">代码-圆圈</div>
            <div class="doc-icon-code">&amp;#xe635;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-carousel"></i>
            <div class="doc-icon-name">轮播组图</div>
            <div class="doc-icon-code">&amp;#xe634;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-prev-circle"></i>
            <div class="doc-icon-name">翻页</div>
            <div class="doc-icon-code">&amp;#xe633;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-layouts"></i>
            <div class="doc-icon-name">布局</div>
            <div class="doc-icon-code">&amp;#xe632;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-util"></i>
            <div class="doc-icon-name">工具</div>
            <div class="doc-icon-code">&amp;#xe631;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-templeate-1"></i>
            <div class="doc-icon-name">选择模板</div>
            <div class="doc-icon-code">&amp;#xe630;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-upload-circle"></i>
            <div class="doc-icon-name">上传-圆圈</div>
            <div class="doc-icon-code">&amp;#xe62f;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-tree"></i>
            <div class="doc-icon-name">树</div>
            <div class="doc-icon-code">&amp;#xe62e;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-table"></i>
            <div class="doc-icon-name">表格</div>
            <div class="doc-icon-code">&amp;#xe62d;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-chart"></i>
            <div class="doc-icon-name">图表</div>
            <div class="doc-icon-code">&amp;#xe62c;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-chart-screen"></i>
            <div class="doc-icon-name">图标 报表 屏幕</div>
            <div class="doc-icon-code">&amp;#xe629;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-engine"></i>
            <div class="doc-icon-name">引擎</div>
            <div class="doc-icon-code">&amp;#xe628;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-triangle-d"></i>
            <div class="doc-icon-name">下三角</div>
            <div class="doc-icon-code">&amp;#xe625;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-triangle-r"></i>
            <div class="doc-icon-name">右三角</div>
            <div class="doc-icon-code">&amp;#xe623;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-file"></i>
            <div class="doc-icon-name">文件</div>
            <div class="doc-icon-code">&amp;#xe621;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-set-sm"></i>
            <div class="doc-icon-name">设置-小型</div>
            <div class="doc-icon-code">&amp;#xe620;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-add-circle"></i>
            <div class="doc-icon-name">添加-圆圈</div>
            <div class="doc-icon-code">&amp;#xe61f;</div>
        </li>


        <li>
            <i class="layui-icon layui-icon-404"></i>
            <div class="doc-icon-name">404</div>
            <div class="doc-icon-code">&amp;#xe61c;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-about"></i>
            <div class="doc-icon-name">关于</div>
            <div class="doc-icon-code">&amp;#xe60b;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-up"></i>
            <div class="doc-icon-name">箭头 向上</div>
            <div class="doc-icon-code">&amp;#xe619;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-down"></i>
            <div class="doc-icon-name">箭头 向下</div>
            <div class="doc-icon-code">&amp;#xe61a;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-left"></i>
            <div class="doc-icon-name">箭头 向左</div>
            <div class="doc-icon-code">&amp;#xe603;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-right"></i>
            <div class="doc-icon-name">箭头 向右</div>
            <div class="doc-icon-code">&amp;#xe602;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-circle-dot"></i>
            <div class="doc-icon-name">圆点</div>
            <div class="doc-icon-code">&amp;#xe617;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-search"></i>
            <div class="doc-icon-name">搜索</div>
            <div class="doc-icon-code">&amp;#xe615;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-set-fill"></i>
            <div class="doc-icon-name">设置-实心</div>
            <div class="doc-icon-code">&amp;#xe614;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-group"></i>
            <div class="doc-icon-name">群组</div>
            <div class="doc-icon-code">&amp;#xe613;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-friends"></i>
            <div class="doc-icon-name">好友</div>
            <div class="doc-icon-code">&amp;#xe612;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-reply-fill"></i>
            <div class="doc-icon-name">回复 评论 实心</div>
            <div class="doc-icon-code">&amp;#xe611;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-menu-fill"></i>
            <div class="doc-icon-name">菜单 隐身 实心</div>
            <div class="doc-icon-code">&amp;#xe60f;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-log"></i>
            <div class="doc-icon-name">记录</div>
            <div class="doc-icon-code">&amp;#xe60e;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-picture-fine"></i>
            <div class="doc-icon-name">图片-细体</div>
            <div class="doc-icon-code">&amp;#xe60d;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-face-smile-fine"></i>
            <div class="doc-icon-name">表情-笑-细体</div>
            <div class="doc-icon-code">&amp;#xe60c;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-list"></i>
            <div class="doc-icon-name">列表</div>
            <div class="doc-icon-code">&amp;#xe60a;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-release"></i>
            <div class="doc-icon-name">发布 纸飞机</div>
            <div class="doc-icon-code">&amp;#xe609;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-ok"></i>
            <div class="doc-icon-name">对 OK</div>
            <div class="doc-icon-code">&amp;#xe605;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-help"></i>
            <div class="doc-icon-name">帮助</div>
            <div class="doc-icon-code">&amp;#xe607;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-chat"></i>
            <div class="doc-icon-name">客服</div>
            <div class="doc-icon-code">&amp;#xe606;</div>
        </li>

        <li>
            <i class="layui-icon layui-icon-top"></i>
            <div class="doc-icon-name">top 置顶</div>
            <div class="doc-icon-code">&amp;#xe604;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-star"></i>
            <div class="doc-icon-name">收藏-空心</div>
            <div class="doc-icon-code">&amp;#xe600;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-star-fill"></i>
            <div class="doc-icon-name">收藏-实心</div>
            <div class="doc-icon-code">&amp;#xe658;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-close-fill"></i>
            <div class="doc-icon-name">关闭-实心</div>
            <div class="doc-icon-code">&amp;#x1007;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-close"></i>
            <div class="doc-icon-name">关闭-空心</div>
            <div class="doc-icon-code">&amp;#x1006;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-ok-circle"></i>
            <div class="doc-icon-name">正确</div>
            <div class="doc-icon-code">&amp;#x1005;</div>
        </li>
        <li>
            <i class="layui-icon layui-icon-add-circle-fine"></i>
            <div class="doc-icon-name">添加-圆圈-细体</div>
            <div class="doc-icon-code">&amp;#xe608;</div>
        </li>
    </ul>
</div>
</body>