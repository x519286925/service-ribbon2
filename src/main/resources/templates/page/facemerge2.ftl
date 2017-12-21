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

<div id="layer-photos-demo" class="layer-photos-demo" style="margin-left: 20px">
<#list 1..50 as t>
    <div style="text-align: center;float: left;margin: 10px 20px" >
        <img  layer-src="/getFaceMerge2?url=${url}&model=${t}"
              src="https://yyb.gtimg.com/aiplat/ai/upload/doc/facemerge/${t}.png" style="width: 120px;height: 160px">
    </div>
</#list>
</div>
<script>
    //调用示例
    layui.use('flow', function(){
        var flow = layui.flow;
        //当你执行这样一个方法时，即对页面中的全部带有lay-src的img元素开启了懒加载（当然你也可以指定相关img）
        flow.lazyimg();
    });
    layui.use('layer', function(){

        layer.msg('当选择对应的融合可点击黑色部分关闭<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击图片的左右边可上下张', {
            icon: 6,
            time: 4000 //2秒关闭（如果不配置，默认是3秒）
        }, function(){
            //do something
        });
        layer.photos({
            photos: '#layer-photos-demo'
            , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });
    });
</script>

</body>
</html>