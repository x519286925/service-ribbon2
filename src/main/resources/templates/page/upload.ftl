<!DOCTYPE html>
<html>
<head>
    <title>腾讯人工智能接入案例</title>
    <meta name="viewport" content="initial-scale=1, user-scalable=0, minimal-ui">
    <meta name="keywords" content="keyword1,keyword2,keyword3"></meta>
    <meta name="description" content="this is my page"></meta>
    <meta name="content-type" content="text/html; charset=UTF-8"></meta>
    <link rel="stylesheet" href="../layui/css/layui.css" media="all">
    <script type="text/javascript" src="../layui/layui.js"></script>
</head>
<script>
    function is_weixn(){    //判断是否为微信浏览器
        var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i)=="micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    if(is_weixn()){
        window.location.href="/test2";
    }
</script>
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
<body>
<br><br><br>
<h2 style="text-align: center"><i class="layui-icon">&#xe681;</i> 腾讯人工智能接入案例 <i class="layui-icon">&#xe65d;</i></h2>
<br><br>
<div style="text-align: center">
    <button type="button" class="layui-btn" id="PersionID" onclick="">
        <i class="layui-icon">&#xe67c;</i>身份证识别(图片要小于1MB)
    </button><br><br>
        <div id="loading0" class="loading"  style="display: none">正在上传身份证解析图片,请稍后...</div>
        <br><br>
        <button type="button" class="layui-btn" id="PhotoSpeaek">
            <i class="layui-icon">&#xe67c;</i>看图说一句话(图片要小于1MB)
        </button><br><br>
        <div id="loading1" class="loading"  style="display: none">正在上传图片-看图说话,请稍后...</div>

    <br><br>
    <button type="button" class="layui-btn" id="Image_Label">
        <i class="layui-icon">&#xe67c;</i>图像标签识别(图片要小于1MB)
    </button><br><br>
    <div id="loading4" class="loading"  style="display: none">正在上传并标签识别中,请稍后...</div>
    <br><br>

    <button type="button" class="layui-btn" id="faceMerge">
        <i class="layui-icon">&#xe67c;</i>人脸融合(图片要小于500KB)
    </button><br><br>
    <div id="loading5" class="loading"  style="display: none">正在上传图片中,请稍后...</div>
    <br><br>
    </div>
<script>
    layui.use('upload', function(){
            var upload = layui.upload;
            //执行实例
            var uploadInst0 = upload.render({    //身份证识别
                elem: '#PersionID', //绑定元素
                url: '/testuploadimg2', //上传接口
                data: {
                    "action": "0"
                }
                , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    document.getElementById("loading0").style.display = "block";
                }
                , done: function (res) {
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
                        layer.alert('识别失败,错误码：' + res.ret, function (index) {
                            document.getElementById("loading0").style.display = "none";
                            layer.close(index);
                        });
                    }
                }
                , error: function () {
                    document.getElementById("loading0").style.display = "none";
                    layer.msg('图片大于1MB或者格式不对', {icon: 2});
                }
            });
            var uploadInst1 = upload.render({    //看图说话
                elem: '#PhotoSpeaek', //绑定元素
                url: '/testuploadimg2', //上传接口
                data: {
                    "action": "1"
                }
                , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    document.getElementById("loading1").style.display = "block";
                }
                , done: function (res) {
                    if (res.ret == 0) {  //表示上传成功
                        document.getElementById("loading1").style.display = "none";
                        layer.alert('一句话：' + res.text, function (index) {
                            layer.close(index);
                        });
                    } else {
                        layer.alert('识别失败,错误码：' + res.ret, function (index) {
                            document.getElementById("loading1").style.display = "none";
                            layer.close(index);
                        });
                    }
                }
                , error: function () {
                    document.getElementById("loading1").style.display = "none";
                    layer.msg('图片大于1MB或者格式不对', {icon: 2});
                }
            });

            var uploadInst4 = upload.render({       //图像标签识别
                elem: '#Image_Label', //绑定元素
                url: '/testuploadimg2', //上传接口
                data: {
                    "action": "4"
                }
                , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    document.getElementById("loading4").style.display = "block";
                }
                , done: function (res) {
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
                        layer.alert('识别失败,错误码：' + res.ret, function (index) {
                            document.getElementById("loading4").style.display = "none";
                            layer.close(index);
                        });
                    }
                }
                , error: function () {
                    document.getElementById("loading4").style.display = "none";
                    layer.msg('图片大于1MB或者格式不对', {icon: 2});
                }
            });


            var uploadInst5 = upload.render({       //人脸融合
                elem: '#faceMerge', //绑定元素
                url: '/testuploadimg2', //上传接口
                data: {
                    "action": "5"
                }
                , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    document.getElementById("loading5").style.display = "block";
                }
                , done: function (res) {    //res为json
                    document.getElementById("loading5").style.display = "none";
                    if (res.meg == "fail") {
                        layer.msg('图片大于500KB', {icon: 2});
                    } else {  //上传成功回调url
                        layer.open({
                            type: 2,
                            title: '选择对应的模板融合图像',
                            area: ['400px', '410px'],
                            content: '/face?url=' + res.image // image是本地的一个url路径
                        });
                    }
                }
                , error: function () {
                    document.getElementById("loading5").style.display = "none";
                    layer.msg('图片大于1MB或者格式不对', {icon: 3});
                }
            });
    });
</script>
</body>
</html>