<!DOCTYPE html>
<html>
<head>
    <title>腾讯人工智能接入案例-微信端</title>
    <meta name="viewport" content="initial-scale=1, user-scalable=0, minimal-ui">
    <meta name="keywords" content="keyword1,keyword2,keyword3"></meta>
    <meta name="description" content="this is my page"></meta>
    <meta name="content-type" content="text/html; charset=UTF-8"></meta>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <link rel="stylesheet" href="../layui/css/layui.css" media="all">
    <script type="text/javascript" src="../layui/layui.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<style>
    .loading{
        width:250px;
        height:56px;
        margin: 0 auto;
        line-height:56px;
        color:#fff;
        padding-left:60px;
        font-size:15px;
        background: #000 url(layui/images/loader.gif) no-repeat 10px 50%;
        opacity: 0.7;
        z-index:9999;
        -moz-border-radius:20px;
        -webkit-border-radius:20px;
        border-radius:20px;
        filter:progid:DXImageTransform.Microsoft.Alpha(opacity=70);
    }
    .tag_confidence{
        color: #FD482C;
    }
    .tag_name{
        color: #FD482C;
    }
</style>
<script>
    var callbackNumber = {
        '4096':	'参数非法',
        '12289':'应用不存在',
        '12801':'素材不存在',
        '12802':'素材ID与应用ID不匹配',
        '16385':'缺少app_id参数',
        '16386':'缺少time_stamp参数',
        '16387':'缺少nonce_str参数',
        '16388':'请求签名无效',
        '16389':'缺失API权限',
        '16390':'time_stamp参数无效',
        '16391':'同义词识别结果为空	系统识别结果为空',
        '16392':'专有名词识别结果为空	系统识别结果为空',
        '16393':'意图识别结果为空	系统识别结果为空',
        '16394':'闲聊返回结果为空	系统处理结果为空',
        '16396':'图片格式非法	请检查图片格式是否符合API要求',
        '16397':'图片体积过大	请检查图片大小是否超过API限制',
        '16402':'图片没有人脸	请检查图片是否包含人脸',
        '16403':'相似度错误	请联系客服反馈问题',
        '16404':'人脸检测失败',
        '16405':'图片解码失败',
        '16406':'特征处理失败',
        '16407':'提取轮廓错误',
        '16408':'提取性别错误',
        '16409':'提取表情错误',
        '16410':'提取年龄错误',
        '16411':'提取姿态错误',
        '16412':'提取眼镜错误',
        '16413':'提取魅力值错误',
        '16414':'语音合成失败',
        '16415':'图片为空',
        '16416':'个体已存在',
        '16417':'个体不存在',
        '16418':'人脸不存在',
        '16419':'分组不存在',
        '16420':'分组列表不存在',
        '16421':'人脸个数超过限制',
        '16422':'个体个数超过限制',
        '16423':'组个数超过限制',
        '16424':'对个体添加了几乎相同的人脸',
        '16425':'无效的图片格式',
        '16426':'图片模糊度检测失败',
        '16427':'美食图片检测失败',
        '16428':'提取图像指纹失败',
        '16429':'图像特征比对失败',
        '16430':'OCR照片为空',
        '16431':'OCR识别失败',
        '16432':'输入图片不是身份证',
        '16433':'名片无足够文本',
        '16434':'名片文本行倾斜角度太大',
        '16435':'名片模糊',
        '16436':'名片姓名识别失败',
        '16437':'名片电话识别失败',
        '16438':'图像为非名片图像',
        '16439':'检测或者识别失败',
        '16440':'未检测到身份证',
        '16441':'请使用第二代身份证件进行扫描',
        '16442':'不是身份证正面照片',
        '16443':'不是身份证反面照片',
        '16444':'证件图片模糊',
        '16445':'请避开灯光直射在证件表面',
        '16446':'行驾驶证OCR识别失败',
        '16447':'通用OCR识别失败',
        '16448':'银行卡OCR预处理错误',
        '16449':'银行卡OCR识别失败',
        '16450':'营业执照OCR预处理失败',
        '16451':'营业执照OCR识别失败',
        '16452':'意图识别超时',
        '16453':'闲聊处理超时',
        '16454':'语音识别解码失败',
        '16455':'语音过长或空',
        '16456':'翻译引擎失败',
        '16457':'不支持的翻译类型'
    }
    function displayAllTranslate(displayAllTranslate){
        layer.open({
            title: '总体翻译',
            content: displayAllTranslate,
            success: function(layero, index){
                console.log(layero, index);
            }
        });
    }
    function wechatChosePhoto(action) {
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                document.getElementById("loading"+action).style.display = "block";
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                wx.uploadImage({
                    localId: localIds.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        var mediaId = res.serverId; // 返回图片的服务器端ID，即mediaId
                        $.post("/uploadImage",{media_id:mediaId,action:action} ,function (data) {
                        if(action==0) {   //身份证
                            var res = eval('(' + data + ')');
                            if (res.ret == 0) {  //表示上传成功
                                document.getElementById("loading0").style.display = "none";
                                layer.alert('姓名：' + res.data.name
                                        + '<br>身份证号码：' + res.data.id
                                        + '<br>性别：' + res.data.sex
                                        + '<br>民族：' + res.data.nation
                                        + '<br>生日：' + res.data.birth
                                        + '<br>住址：' + res.data.address
                                        , function (index) {
                                            layer.close(index);
                                        });
                            } else {
                                layer.alert('识别失败：' + callbackNumber[res.ret], function (index) {
                                    document.getElementById("loading0").style.display = "none";
                                    layer.close(index);
                                });
                            }
                        }
                            if(action==1) {
                                var res = eval('(' + data + ')');
                                if (res.ret == 0) {  //表示上传成功
                                    document.getElementById("loading1").style.display = "none";
                                    layer.alert('一句话：' + res.text, function (index) {
                                        layer.close(index);
                                    });
                                } else {
                                    layer.alert('识别失败：' + callbackNumber[res.ret], function (index) {
                                        document.getElementById("loading1").style.display = "none";
                                        layer.close(index);
                                    });
                                }
                            }

                            if(action==4) {
                                var res = eval('(' + data + ')');
                                if (res.ret == 0) {  //表示上传成功
                                    document.getElementById("loading4").style.display = "none";
                                    var title = '图像标签识别：<br>' + '置信度,范围0-100,越大置信度越高<br>';
                                    var list = res.labelPhotos;
                                    var content = '';
                                    for (var i = 0; i < list.length; i++) {
                                        content = content + '<span class="tag_name">' + list[i].tag_name + '</span>'
                                                + '   -->置信度:' + '<span class="tag_confidence">' + list[i].tag_confidence + '</span><br>';
                                    }
                                    layer.alert(title + content
                                            , function (index) {
                                                layer.close(index);
                                            });
                                } else {
                                    layer.alert('识别失败：' + callbackNumber[res.ret], function (index) {
                                        document.getElementById("loading4").style.display = "none";
                                        layer.close(index);
                                    });
                                }
                            }
                            if(action==5) {
                                document.getElementById("loading5").style.display = "none";
                                var res = eval('(' + data + ')');
                                if (res.meg == "fail") {
                                    layer.msg('图片大于500KB', {icon: 2});
                                } else {  //上传成功回调url
                                    layer.open({
                                        type: 2,
                                        title: '选择对应的模板融合图像',
                                        area: ['350px', '400px'],
                                        content: '/wechatface?url=' + res.image.toString()
                                    });
                                }
                            }
                            if(action==6) {
                                var res = eval('(' + data + ')');
                                if (res.ret == 0) {  //表示上传成功
                                    document.getElementById("loading6").style.display = "none";
                                    var image_recordsList = res.image_recordsList;
                                    var duanluocontent = '';
                                    var chineseContent = '';
                                    for(var i = 0;i<image_recordsList.length;i++){
                                        duanluocontent = duanluocontent+'<blockquote class="layui-elem-quote">'+image_recordsList[i].source_text+'<br><strong>'+image_recordsList[i].target_text+'</strong></blockquote><br>';
                                    }
                                    for(var i = 0;i<image_recordsList.length;i++){
                                        chineseContent = chineseContent+ image_recordsList[i].target_text;
                                    }
                                    layer.open({
                                        type: 1,
                                        title: '段落翻译内容',
                                        skin: 'layui-layer-rim', //加上边框
                                        offset: '80px',
                                        area: ['100%', '340px'], //宽高
                                        content: duanluocontent
                                        +'<div style="text-align: center">' +
                                        '<button onclick="displayAllTranslate(\''+chineseContent+'\')" ' +
                                        'style="width: 95%" class="layui-btn">点击显示总体翻译</button><br><br><br></div>'
                                    });
                                } else {
                                    layer.alert('识别失败：' + callbackNumber[res.ret], function (index) {
                                        document.getElementById("loading6").style.display = "none";
                                        layer.close(index);
                                    });
                                }
                            }

                        });
                    }
                });
            }
      });
    }

</script>
<body>
<br><br><br>
<h2 style="text-align: center"><i class="layui-icon">&#xe681;</i> 腾讯人工智能接入案例 <i class="layui-icon">&#xe65d;</i></h2>
<br><br>
<div style="text-align: center">
    <button type="button" class="layui-btn" id="photoForTranslate" onclick="wechatChosePhoto(6)" style="width:95%;">
        <i class="layui-icon">&#xe67c;</i>图片翻译(图片要小于1MB)
    </button><br><br>
    <div id="loading6" class="loading"  style="display: none">正在解析翻译中,请稍后...</div>
    <br><br>
    <button type="button" class="layui-btn" id="PersionID" onclick="wechatChosePhoto(0)"  style="width:95%;">
        <i class="layui-icon">&#xe67c;</i>身份证识别(图片要小于1MB)
    </button><br><br>
        <div id="loading0" class="loading"  style="display: none">正在解析身份证图片,请稍后...</div>
        <br><br>
        <button type="button" class="layui-btn" id="PhotoSpeaek" onclick="wechatChosePhoto(1)"  style="width:95%;">
            <i class="layui-icon">&#xe67c;</i>看图说一句话(图片要小于1MB)
        </button><br><br>
        <div id="loading1" class="loading"  style="display: none">正在解析图片-看图说话,请稍后...</div>

    <br><br>
    <button type="button" class="layui-btn" id="Image_Label" onclick="wechatChosePhoto(4)"  style="width:95%;">
        <i class="layui-icon">&#xe67c;</i>图像标签识别(图片要小于1MB)
    </button><br><br>
    <div id="loading4" class="loading"  style="display: none">正在标签识别中,请稍后...</div>
    <br><br>

    <button type="button" class="layui-btn" id="faceMerge" onclick="wechatChosePhoto(5)"  style="width:95%;">
        <i class="layui-icon">&#xe67c;</i>人脸融合(图片要小于500KB)
    </button><br><br>
    <div id="loading5" class="loading"  style="display: none">正在上传照片图片中,请稍后...</div>
    <br><br>
    </div>
<script>
    function closeAll(){
        layer.closeAll('page'); //关闭所有页面层
    }
    layui.use('upload', function(){
//    //页面层
//        layer.open({
//            title: '微信浏览器访问通知',
//            offset: '150px',
//            area: ["320px"],
//            content: '为方便您使用调用了微信的JDK,您需要关注测试号才可以使用功能'
//        });
        wx.config({
            debug: 0, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: '${wxJsVO.appId}', // 必填，公众号的唯一标识
            timestamp: ${wxJsVO.timestamp}, // 必填，生成签名的时间戳
            nonceStr: '${wxJsVO.nonceStr}', // 必填，生成签名的随机串
            signature: '${wxJsVO.signature}',// 必填，签名，见附录1
            jsApiList: ['chooseImage','previewImage','uploadImage','downloadImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        wx.error(function(res){
            layer.open({
                offset: '90px',
                area: ['100%','340px'],
                title: '您没有关注测试号无法使用',
                type: 1,
                content: '<img src="../images/test.png" width="100%" height="300px">' //这里content是一个普通的String
            });
            // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        });
        wx.checkJsApi({
            jsApiList: ['chooseImage','previewImage','uploadImage','downloadImage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function(res) {
                console.log("接口是否可以调用："+res);
                // 以键值对的形式返回，可用的api值true，不可用为false
                // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
            }
        });
    });
</script>
</body>
</html>