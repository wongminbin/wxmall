<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/user/common/header.jsp"%>

<div class="imgs">web icon图片</div>
<img id="webico"  width="150px"/>
<textarea name="description" id="description" style="width:1024px;height:500px"></textarea>

<!--引入上传组件JS-->
<link rel="stylesheet" type="text/css" href="${basepath}/static/js/webuploader/webuploader.css">
<script type="text/javascript" src="${basepath}/static/js/webuploader/webuploader.js"></script>

<!-- kindeditor -->
<script charset="utf-8" src="${basepath}/static/js/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${basepath}/static/js/kindeditor/lang/zh_CN.js"></script>
<link rel="stylesheet" href="${basepath}/static/js/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="${basepath}/static/js/kindeditor/plugins/code/prettify.css" />
<script type="text/javascript">
var editor;
KindEditor.ready(function(K) {
	
	editor = K.create('textarea[name="description"]', {
			uploadJson : '/upload/keditor',
			fillDescAfterUploadImage: false,
			afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
			},
			afterBlur:function(){ 
	            this.sync(); 
	        } 
		});
});

$(function () {
    var uploader = WebUploader.create({
        auto: true,
        compress: false,
        swf: '${basepath}/static/js/webuploader/Uploader.swf',
        server: "${basepath}/upload/webupload",
        pick: '.imgs',
        fileVal: 'imageFile',
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });
    uploader.on('uploadSuccess', function (file, response) {
        $("#webico").attr("src", response.url);
        uploader.removeFile( file );
    });
    uploader.on('uploadError', function (file) {
        $('#' + file.id).find('p.state').text('上传出错');
    });
});
</script>
<%@include file="/WEB-INF/view/user/common/footer.jsp"%>