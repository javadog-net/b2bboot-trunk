/*
*    jQuery文件上传插件,封装UI,上传处理操作采用Baidu WebUploader;
*/
(function ($) {

    $.fn.extend({
        /*
        *    上传方法 opt为参数配置;
        *    {
        *    input:fileurl,
        *    url:'http://localhost/manager/upload/uploadimage
        *    type:'image',
        *    ext:'.jpg,.jpeg,.gif,.pnp',
        *    size:1024*1024*2
        *    num:20,
        *    auto:true,
        *    }
        *    serverCallBack回调函数 每个文件上传至服务端后,服务端返回参数,无论成功失败都会调用 参数为服务器返回信息;
        */
        WebUpload: function (opt) {
            if (typeof opt != "object") {
                alert('参数错误!');
                return;
            }
            //上传的文件数组，保存为{id,url}
            var $fileArray = [];
            var $isImage = (opt.type == 'image' || opt.type == 'thumb') ? true : false;
            var $isThumb = opt.type == 'thumb' ? true : false;
            var $pickLabel = $isImage ? '选择图片' : '选择文件';
            //自身容器
            var $wrap = $(this),
                $fileInput = $("#" + opt.input),
                $fileInputId = $fileInput.attr('id'),
                $filepicker = $wrap.find('.filepicker'),
                $pickId = $filepicker.attr('id'),
                $queue = $wrap.find('.filelist'),
                // 状态栏，包括进度和控制按钮
                $statusBar = $wrap.find('.statusBar'),

                // 上传按钮
                $uploadBtn = $wrap.find('.uploadBtn'),

                // 总体进度条
                $progress = $statusBar.find('.progress').hide(),

                // 添加的文件数量
                fileCount = 0,

                // 添加的文件总大小
                fileSize = 0,

                // 优化retina, 在retina下这个值是2
                ratio = window.devicePixelRatio || 1,

                // 缩略图大小
                thumbnailWidth = 90 * ratio,
                thumbnailHeight = 90 * ratio,

                // 可能有pedding, ready, uploading, confirm, done.
                state = 'pedding',

                // 所有文件的进度信息，key为file id
                percentages = {},

                supportTransition = (function () {
                    var s = document.createElement('p').style,
                        r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                    s = null;
                    return r;
                })(),

                // WebUploader实例
                uploader;

            if (!WebUploader.Uploader.support()) {
                alert('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
                throw new Error('WebUploader does not support the browser you are using.');
            }
            // 实例化
            uploader = WebUploader.create({
                pick: {
                    id: '#' + $pickId,
                    label: $pickLabel
                },
                //paste: document.body,
                accept: {
                    title: opt.type == 'image' ? 'image' : 'file',
                    extensions: opt.ext,
                    mimeTypes: opt.type == 'image' ? 'image/*' : '*/*',
                },
                // swf文件路径
                swf: ctxStatic + '/plugin/webuploader-0.1.5/Uploader.swf',

                disableGlobalDnd: true,

                chunked: false,
                server: opt.url,
                fileNumLimit: opt.num,
                fileSizeLimit: opt.num * opt.size,    // 200 M
                fileSingleSizeLimit: opt.size    // 50 M
            });

            function newWebUploadFile(url) {
                var obj = {};
                var name = url.substring(url.lastIndexOf("/") + 1);
                obj.url = url;
                obj.name = name;
                obj.ext = name.substr(name.lastIndexOf(".") + 1);
                obj.size = 1;
                var file = new WebUploader.File(obj);
                file.setStatus('complete');
                file.url = url;
                return file;
            }

            function isPic(ext) {
                switch (ext) {
                    case "jpg":
                    case "jpeg":
                    case "png":
                    case "gif":
                    case "bmp":
                        return true;
                        break;
                    default:
                        return false;
                        break;
                }
            }

            function getExtStyle(ext) {
                var fileType = {};
                var suffix = '_icon_bg';
                fileType['mp3'] = 'mp3';
                fileType['mp4'] = 'mp4';
                fileType['avi'] = 'avi';
                fileType['doc'] = 'doc';
                fileType['docx'] = 'docx';
                fileType['csv'] = 'csv';
                fileType['xls'] = 'xls';
                fileType['xlsx'] = 'xlsx';
                fileType['ppt'] = 'ppt';
                fileType['pptx'] = 'pptx';
                fileType['pdf'] = 'pdf';
                fileType['txt'] = 'txt';
                fileType['rar'] = 'rar';
                fileType['zip'] = 'zip';
                fileType = fileType[ext] || 'default';
                return fileType + suffix;
            }

            /*var getFileBlob = function (url, cb) {
                var xhr = new XMLHttpRequest();
                xhr.open("GET", url);
                xhr.responseType = "blob";
                xhr.addEventListener('load', function() {
                    cb(xhr.response);
                });
                xhr.send();
            };

            var blobToFile = function (blob, name) {
                blob.lastModifiedDate = new Date();
                blob.name = name;
                return blob;
            };

            var getFileObject = function(filePathOrUrl, cb) {
                getFileBlob(filePathOrUrl, function (blob) {
                    var name = filePathOrUrl.substring(filePathOrUrl.lastIndexOf("/") + 1);
                    cb(blobToFile(blob, name));
                });
            };*/

            //初始化完成添加已经上传的文件
            uploader.on('ready', function () {
                var filevaluelist = [];
                var filevalueStr = $fileInput.val().trim();
                if(filevalueStr!=''){
                    filevaluelist = filevalueStr.split('|');
                }
                $.each(filevaluelist, function (i, item) {
                    var file = newWebUploadFile(item);
                    $fileArray.push({id: file.id, name: file.name, url: item});
                    uploader.addFiles(file);
                    //$fileArray.push({id: file.id, name: file.name, url: item});
                    //以下代码采用获取文件流的方式来添加文件，存在问题：生成缩略图失败，第二，如果是文件每次去获取文件流会存在大量消耗。
                    /*getFileObject(item, function (fileObject) {
                        var wuFile = new WebUploader.Lib.File(WebUploader.guid('rt_'),fileObject);
                        var file = new WebUploader.File(wuFile);
                        file.setStatus('complete');
                        uploader.addFiles(file);
                    })*/
                })
                //updateTotalProgress();
            })

            // 当有文件添加进来时执行，负责view的创建
            function addFile(file) {
                var $li = $('<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>' +
                    '<p class="progress"><span></span></p>' +
                    '</li>');
                var ispic = isPic(file.ext);
                if (ispic) {
                    var $btns = $('<div class="file-panel">' +
                        '<span class="cancel">删除</span>' +
                        '<span class="rotateRight">向右旋转</span>' +
                        '<span class="rotateLeft">向左旋转</span></div>').appendTo($li)
                } else {
                    var extStyle = getExtStyle(file.ext);
                    $li.addClass(extStyle);
                    var $btns = $('<div class="file-panel">' +
                        '<span class="cancel">删除</span></div>').appendTo($li)

                }
                var $prgress = $li.find('p.progress span'),
                    $wrap = $li.find('p.imgWrap'),
                    $info = $('<p class="error"></p>'),

                    showError = function (code) {
                        switch (code) {
                            case 'exceed_size':
                                text = '文件大小超出';
                                break;

                            case 'interrupt':
                                text = '上传暂停';
                                break;

                            default:
                                text = '上传失败，请重试';
                                break;
                        }

                        $info.text(text).appendTo($li);
                    };
                if (file.url) {
                    //原有文件
                    var alink = $('<a href="' + file.url + '" target=_blank></a>');
                    if (ispic) {
                        //alink = $('<a href="javascript:void(0)" onclick=jh.showPic("' + file.url + '")></a>');
                        var img = $('<img src="' + file.url + '">');
                        alink.append(img);
                    }
                    $wrap.empty().append(alink);
                    percentages[file.id] = [0, 0];
                    $li.append('<span class="success"></span>').addClass('state-complete');
                    var flag = true;
                    if($fileArray){
                        $fileArray.forEach(function (value) {
                            if(value.id == file.id){
                                flag = false;
                            }
                        })
                    }
                    if(flag){
                        $fileArray.push(file);
                    }
                } else {
                    if (file.getStatus() === 'invalid') {
                        showError(file.statusText);
                    } else {
                        // @todo lazyload
                        if (ispic) {
                            $wrap.text('预览中');
                            uploader.makeThumb(file, function (error, src) {
                                if (error) {
                                    $wrap.text('不能预览');
                                    return;
                                }

                                var img = $('<img src="' + src + '">');
                                $wrap.empty().append(img);
                            }, thumbnailWidth, thumbnailHeight);
                        }
                        percentages[file.id] = [file.size, 0];
                        file.rotation = 0;
                    }
                }
                if (!file.url) {
                    file.on('statuschange', function (cur, prev) {
                        if (prev === 'progress') {
                            $prgress.hide().width(0);
                        } else if (prev === 'queued') {
                            //删除按钮永久有效
                            //$li.off( 'mouseenter mouseleave' );
                            //$btns.remove();
                        }

                        // 成功
                        if (cur === 'error' || cur === 'invalid') {
                            console.log(file.statusText);
                            showError(file.statusText);
                            percentages[file.id][1] = 1;
                        } else if (cur === 'interrupt') {
                            showError('interrupt');
                        } else if (cur === 'queued') {
                            percentages[file.id][1] = 0;
                        } else if (cur === 'progress') {
                            $info.remove();
                            $prgress.css('display', 'block');
                        } else if (cur === 'complete') {
                            $li.append('<span class="success"></span>');
                        }

                        $li.removeClass('state-' + prev).addClass('state-' + cur);
                    })
                };

                $li.on('mouseenter', function () {
                    $btns.stop().animate({height: 30});
                });

                $li.on('mouseleave', function () {
                    $btns.stop().animate({height: 0});
                });

                $btns.on('click', 'span', function () {
                    var index = $(this).index(),
                        deg;

                    switch (index) {
                        case 0:
                            uploader.removeFile(file);
                            return;

                        case 1:
                            file.rotation += 90;
                            break;

                        case 2:
                            file.rotation -= 90;
                            break;
                    }

                    if (supportTransition) {
                        deg = 'rotate(' + file.rotation + 'deg)';
                        $wrap.css({
                            '-webkit-transform': deg,
                            '-mos-transform': deg,
                            '-o-transform': deg,
                            'transform': deg
                        });
                    } else {
                        $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                    }


                });

                $li.appendTo($queue);
            }

            // 负责view的销毁
            function removeFile(file) {
                var $li = $('#' + file.id);
                //获取父元素
                var fileIndex= $li.index();
                delete percentages[file.id];
                updateTotalProgress();
                $li.off().find('.file-panel').off().end().remove();
                $fileArray.splice(fileIndex, 1);
                //上面通过元素可以直接获取到索引，就可以不用循环查找了
                /*for (var i = $fileArray.length - 1; i >= 0; i--) {
                    if ($fileArray[i].id == file.id) {
                        $fileArray.splice(i, 1);
                    }
                }*/
                processInputVal();
            }

            function processInputVal() {
                var urlArray = [];
                $.each($fileArray, function (i, item) {
                    urlArray.push(item.url);
                })
                var urlStr = '';
                if (urlArray.length == 1) {
                    urlStr = urlArray[0];
                } else if (urlArray.length > 1) {
                    urlStr = urlArray.join('|');
                }
                $fileInput.val(urlStr);
            }

            function updateTotalProgress() {
                var loaded = 0,
                    total = 0,
                    spans = $progress.children(),
                    percent;

                $.each(percentages, function (k, v) {
                    total += v[0];
                    loaded += v[0] * v[1];
                });

                percent = total ? loaded / total : 0;

                spans.eq(0).text(Math.round(percent * 100) + '%');
                spans.eq(1).css('width', Math.round(percent * 100) + '%');
            }

            function setState(val) {
                var file, stats;

                if (val === state) {
                    return;
                }

                $uploadBtn.removeClass('state-' + state);
                $uploadBtn.addClass('state-' + val);
                state = val;

                switch (state) {
                    case 'pedding':
                        $queue.parent().removeClass('filled');
                        uploader.refresh();
                        break;

                    case 'ready':
                        $filepicker.removeClass('element-invisible');
                        $queue.parent().addClass('filled');
                        uploader.refresh();
                        break;

                    case 'uploading':
                        $filepicker.addClass('element-invisible');
                        $progress.show();
                        $uploadBtn.text('暂停上传');
                        break;

                    case 'paused':
                        $progress.show();
                        $uploadBtn.text('继续上传');
                        break;

                    case 'confirm':
                        $progress.hide();
                        $filepicker.removeClass('element-invisible');
                        $uploadBtn.text('开始上传').removeClass('disabled');
                        ;
                        //$uploadBtn.text('开始上传').addClass('disabled');

                        stats = uploader.getStats();
                        if (stats.successNum && !stats.uploadFailNum) {
                            setState('finish');
                            return;
                        }
                        break;
                    case 'finish':
                        $filepicker.removeClass('element-invisible');
                        $uploadBtn.removeClass('disabled');
                        stats = uploader.getStats();
                        if (stats.successNum) {
                            //alert( '上传成功' );
                        } else {
                            // 没有成功的图片，重设
                            state = 'done';
                            //location.reload();
                        }
                        break;
                }
            }

            uploader.onUploadProgress = function (file, percentage) {
                var $li = $('#' + file.id),
                    $percent = $li.find('.progress span');

                $percent.css('width', percentage * 100 + '%');
                percentages[file.id][1] = percentage;
                updateTotalProgress();
            };

            uploader.onFileQueued = function (file) {
                fileCount++;
                fileSize += file.size;
                addFile(file);
                setState('ready');
                updateTotalProgress();
            };

            uploader.onFileDequeued = function (file) {
                fileCount--;
                fileSize -= file.size;

                if (!fileCount) {
                    setState('pedding');
                }

                removeFile(file);
                updateTotalProgress();

            };
            uploader.onUploadAccept = function (file, response) {
                //console.log(response);
                if (!response.success) {
                    return false;
                }
            };
            uploader.onUploadSuccess = function (file, response) {
                //console.log(response);
                if (response.success) {
                    var url = response.url;
                    var thumb = response.thumb;
                    if($isThumb){
                        url = thumb || url;
                    }
                    $fileArray.push({id: file.id, name: file.name, url: url});
                    processInputVal();
                }
            };

            uploader.onUploadError = function (file, response) {
                console.log(response);
            };

            uploader.on('all', function (type) {
                var stats;
                switch (type) {
                    case 'uploadFinished':
                        //console.log('uploadFinished');
                        setState('confirm');
                        break;

                    case 'startUpload':
                        setState('uploading');
                        break;

                    case 'stopUpload':
                        setState('paused');
                        break;

                }
            });

            uploader.onError = function (code) {
                switch (code) {
                    case 'F_EXCEED_SIZE':
                        text = '文件大小超出';
                        break;
                    case 'Q_TYPE_DENIED':
                        text = '不支持的文件类型';
                        break;
                    case 'Q_EXCEED_NUM_LIMIT':
                        text = '文件数量超出';
                        break;
                    case 'F_DUPLICATE':
                        text = '文件重复';
                        break;
                    default:
                        text = '上传失败';
                        break;
                }

                layer.msg(text);
            };

            $uploadBtn.on('click', function () {
                if ($(this).hasClass('disabled')) {
                    return false;
                }
                if (state === 'ready') {
                    uploader.upload();
                } else if (state === 'paused') {
                    uploader.upload();
                } else if (state === 'uploading') {
                    uploader.stop();
                } else if (state === 'confirm') {
                    uploader.retry();
                }
            });

            $uploadBtn.addClass('state-' + state);
            updateTotalProgress();
            return {
                uploader:uploader,
                $fileArray:$fileArray
            };
        }
    });
})(jQuery);
