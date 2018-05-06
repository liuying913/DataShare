<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="title" content="GNSS数据资源共享与信息发布平台新闻编辑">
<meta name="description" content="GNSS数据资源共享与信息发布平台新闻编辑">
<meta name="keywords" content="GNSS数据资源共享与信息发布平台新闻编辑">
<title>GNSS数据资源共享与信息发布平台新闻编辑</title>

<!-- Le styles -->
<link href="/DataShare/main/newsEdit/css/bootstrap-combined.min.css" rel="stylesheet">
<link href="/DataShare/main/newsEdit/css/layoutit.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
		<script src="js/html5shiv.js"></script>
	<![endif]-->

	<!-- Fav and touch icons -->
	<link rel="shortcut icon" href="/DataShare/main/newsEdit/img/favicon.png">
	
	<script type="text/javascript" src="/DataShare/main/newsEdit/js/jquery-2.0.0.min.js"></script>
	<!--[if lt IE 9]>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<![endif]-->
	<script type="text/javascript" src="/DataShare/main/newsEdit/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/DataShare/main/newsEdit/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/DataShare/main/newsEdit/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="/DataShare/main/newsEdit/js/jquery.htmlClean.js"></script>
<script type="text/javascript" src="/DataShare/main/newsEdit/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/DataShare/main/newsEdit/ckeditor/config.js"></script>
<script type="text/javascript" src="/DataShare/main/newsEdit/js/scripts.js"></script>
</head>

<body style="min-height: 800px; cursor: auto;" class="edit">
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid">
      <button data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar" type="button"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      <a class="brand" href="#"><img src="/DataShare/main/newsEdit/img/favicon.png"> 可视化布局<span class="label">BETA</span></a>
      <div class="nav-collapse collapse">
      	<ul class="nav" id="menu-layoutit">
          <li class="divider-vertical"></li>
          <li>
            <div class="btn-group" data-toggle="buttons-radio">
              <button type="button" id="edit" class="btn btn-primary active"><i class="icon-edit icon-white"></i>编辑</button>
              <button type="button" class="btn btn-primary" id="devpreview"><i class="icon-eye-close icon-white"></i>布局编辑</button>
              <button type="button" class="btn btn-primary" id="sourcepreview"><i class="icon-eye-open icon-white"></i>预览</button>
            </div>
            <div class="btn-group">
              <button type="button" class="btn btn-primary" data-target="#downloadModal" rel="/build/downloadModal" role="button" data-toggle="modal"><i class="icon-share icon-white"></i>保存</button>
              
              <button class="btn btn-primary" href="#clear" id="clear"><i class="icon-trash icon-white"></i>清空</button>
            </div>
            <div class="btn-group">
				<button class="btn btn-primary" href="#undo" id="undo" ><i class="icon-arrow-left icon-white"></i>撤销</button>
				<button class="btn btn-primary" href="#redo" id="redo" ><i class="icon-arrow-right icon-white"></i>重做</button>
			</div>
          </li>
        </ul>
        <ul class="nav pull-right">
           	  <li>
           	  <div class="btn-group">
             	  <!--<span><iframe class="github-btn" src="http://ghbtns.com/github-btn.html?user=dodgepudding&repo=layoutit&type=watch&count=true" allowtransparency="true" frameborder="0" scrolling="0" width="80px" height="20px"></iframe></span>
	      <span><iframe class="github-btn" src="http://ghbtns.com/github-btn.html?user=dodgepudding&repo=layoutit&type=fork&count=true" allowtransparency="true" frameborder="0" scrolling="0" width="80px" height="20px"></iframe></span>-->
	      </div>
	      </li>
        </ul>
      </div>
      <!--/.nav-collapse --> 
    </div>
  </div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
    <div class="">
      <div class="sidebar-nav">
        <ul class="nav nav-list accordion-group">
          <li class="nav-header">
            <div class="pull-right popover-info"><i class="icon-question-sign "></i>
              <div class="popover fade right">
                <div class="arrow"></div>
                <h3 class="popover-title">功能</h3>
                <div class="popover-content">在这里设置你的栅格布局, 栅格总数默认为12, 用空格分割每列的栅格值</div>
              </div>
            </div>
            <i class="icon-plus icon-white"></i> 布局设置 </li>
          <li style="display: list-item;" class="rows" id="estRows">
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="12" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span12 column"></div>
                </div>
              </div>
            </div>
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="6 6" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span6 column"></div>
                  <div class="span6 column"></div>
                </div>
              </div>
            </div>
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="8 4" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span8 column"></div>
                  <div class="span4 column"></div>
                </div>
              </div>
            </div>
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="4 4 4" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span4 column"></div>
                  <div class="span4 column"></div>
                  <div class="span4 column"></div>
                </div>
              </div>
            </div>
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="2 6 4" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span2 column"></div>
                  <div class="span6 column"></div>
                  <div class="span4 column"></div>
                </div>
              </div>
            </div>
          </li>
        </ul>
        <ul class="nav nav-list accordion-group">
          <li class="nav-header"><i class="icon-plus icon-white"></i>内容设置
            <div class="pull-right popover-info"><i class="icon-question-sign "></i>
              <div class="popover fade right">
                <div class="arrow"></div>
                <h3 class="popover-title">帮助</h3>
                <div class="popover-content">这里提供了一系列基本元素样式，你可以通过区块右上角的编辑按钮修改样式设置</div>
              </div>
            </div>
          </li>
          <li style="display: none;" class="boxes" id="elmBase">
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span> 
            	 <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">对齐 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">默认</a></li>
                <li class=""><a href="#" rel="text-left">靠左</a></li>
                <li class=""><a href="#" rel="text-center">居中</a></li>
                <li class=""><a href="#" rel="text-right">靠右</a></li>
              </ul>
              </span> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">标记 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">默认</a></li>
                <li class=""><a href="#" rel="muted">禁用</a></li>
                <li class=""><a href="#" rel="text-warning">警告</a></li>
                <li class=""><a href="#" rel="text-error">错误</a></li>
                <li class=""><a href="#" rel="text-info">提示</a></li>
                <li class=""><a href="#" rel="text-success">成功</a></li>
              </ul>
              </span> </span>
              <div class="preview">标题栏</div>
              <div class="view">
                <h3 contenteditable="true">请输入新闻标题</h3>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">对齐 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">默认</a></li>
                <li class=""><a href="#" rel="text-left">靠左</a></li>
                <li class=""><a href="#" rel="text-center">居中</a></li>
                <li class=""><a href="#" rel="text-right">靠右</a></li>
              </ul>
              </span> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">标记 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">默认</a></li>
                <li class=""><a href="#" rel="muted">禁用</a></li>
                <li class=""><a href="#" rel="text-warning">警告</a></li>
                <li class=""><a href="#" rel="text-error">错误</a></li>
                <li class=""><a href="#" rel="text-info">提示</a></li>
                <li class=""><a href="#" rel="text-success">成功</a></li>
              </ul>
              </span> <a class="btn btn-mini" href="#" rel="lead">Lead</a> </span>
              <div class="preview">段落</div>
              <div class="view" contenteditable="true">
                <p>请输入新闻内容</p>
              </div>
            </div>
          </li>
        </ul>
        <ul class="nav nav-list accordion-group">
          <li class="nav-header"><i class="icon-plus icon-white"></i>常用组件
            <div class="pull-right popover-info"><i class="icon-question-sign "></i>
              <div class="popover fade right">
                <div class="arrow"></div>
                <h3 class="popover-title">帮助</h3>
                <div class="popover-content">拖放组件到布局容器. 拖入后, 你可以配置显示样式</div>
              </div>
            </div>
          </li>
          <li style="display: none;" class="boxes mute" id="elmJS">

           <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">单位地址</div>
              <div class="view">
                <address contenteditable="true">
                <strong>地壳运动监测工程研究中心</strong><br>
               		          北京市西城区三里河路56号<br>
                <abbr title="Phone">P:</abbr> (123) 456-7890
                </address>
              </div>
            </div>
           
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">样式 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">默认</a></li>
                <li class=""><a href="#" rel="table-striped">条纹</a></li>
                <li class=""><a href="#" rel="table-bordered">边框</a></li>
              </ul>
              </span> <a class="btn btn-mini" href="#" rel="table-hover">鼠标指示</a> <a class="btn btn-mini" href="#" rel="table-condensed">紧凑</a> </span>
              <div class="preview">表格</div>
              <div class="view">
                <table class="table" contenteditable="true">
                  <thead>
                    <tr>
                      <th>编号</th>
                      <th>数据产品</th>
                      <th>时间</th>
                      <th>状态</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1</td>
                      <td>陆态共享数据产品</td>
                      <td>01/03/2017</td>
                      <td>发布完成</td>
                    </tr>
                    <tr class="success">
                      <td>1</td>
                      <td>流动共享数据产品</td>
                      <td>01/03/2017</td>
                      <td>发布完成</td>
                    </tr>
                    <tr class="error">
                      <td>2</td>
                      <td>陆态应急数据产品</td>
                      <td>02/03/2017</td>
                      <td>待发布</td>
                    </tr>
                    <tr class="warning">
                      <td>3</td>
                      <td>共享数据产品</td>
                      <td>03/03/2017</td>
                      <td>建设中</td>
                    </tr>
                    <tr class="info">
                      <td>4</td>
                      <td>站点大事记</td>
                      <td>04/03/2017</td>
                      <td>未开始</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span> <span class="configuration"> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">样式 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">默认</a></li>
                <li class=""><a href="#" rel="img-rounded">圆角</a></li>
                <li class=""><a href="#" rel="img-circle">圆圈</a></li>
                <li class=""><a href="#" rel="img-polaroid">相框</a></li>
              </ul>
              </span> </span>
              <div class="preview">单位logo</div>
              <div class="view"> <img alt="140x140" src="/news/iniImg/logo.png"> </div>
            </div>

            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">位置 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">默认</a></li>
                <li class=""><a href="#" rel="tabs-below">底部</a></li>
                <li class=""><a href="#" rel="tabs-left">靠左</a></li>
                <li class=""><a href="#" rel="tabs-right">靠右</a></li>
              </ul>
              </span> </span>
              <div class="preview">切换卡</div>
              <div class="view">
                <div class="tabbable" id="myTabs"> <!-- Only required for left/right tabs -->
                  <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab1" data-toggle="tab" contenteditable="true">第一部分</a></li>
                    <li><a href="#tab2" data-toggle="tab" contenteditable="true">第二部分</a></li>
                  </ul>
                  <div class="tab-content">
                    <div class="tab-pane active" id="tab1" contenteditable="true">
                      <p>第一部分内容.</p>
                    </div>
                    <div class="tab-pane" id="tab2" contenteditable="true">
                      <p>第二部分内容.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span> <span class="configuration"> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">样式 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">默认</a></li>
                <li class=""><a href="#" rel="alert-info">提示</a></li>
                <li class=""><a href="#" rel="alert-error">错误</a></li>
                <li class=""><a href="#" rel="alert-success">成功</a></li>
              </ul>
              </span> </span>
              <div class="preview">提示框</div>
              <div class="view">
                <div class="alert" contenteditable="true">
                  <button type="button" class="close" data-dismiss="alert">×</button>
                  <h4>提示!</h4>
                  <strong>警告!</strong> 请注意你的个人隐私安全. </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">手风琴切换</div>
              <div class="view">
                <div class="accordion" id="myAccordion">
                  <div class="accordion-group">
                    <div class="accordion-heading"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#myAccordion" href="#collapseOne" contenteditable="true"> 选项卡 #1 </a> </div>
                    <div id="collapseOne" class="accordion-body collapse in">
                      <div class="accordion-inner" contenteditable="true"> 功能块</div>
                    </div>
                  </div>
                  <div class="accordion-group">
                    <div class="accordion-heading"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#myAccordion" href="#collapseTwo" contenteditable="true"> 选项卡 #2 </a> </div>
                    <div id="collapseTwo" class="accordion-body collapse">
                      <div class="accordion-inner" contenteditable="true"> 功能块</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">轮换图</div>
              <div class="view">
                <div class="carousel slide" id="myCarousel">
                  <ol class="carousel-indicators">
                    <li class="active" data-slide-to="0" data-target="#myCarousel"></li>
                    <li data-slide-to="1" data-target="#myCarousel" class=""></li>
                    <li data-slide-to="2" data-target="#myCarousel" class=""></li>
                  </ol>
                  <div class="carousel-inner">
                    <div class="item active"> <img alt="" src="/news/iniImg/1.jpg">
                      <div class="carousel-caption" contenteditable="true">
                        <h4>棒球</h4>
                        <p>棒球运动是一种以棒打球为主要特点，集体性、对抗性很强的球类运动项目，在美国、日本尤为盛行。</p>
                      </div>
                    </div>
                    <div class="item"> <img alt="" src="/news/iniImg/2.jpg">
                      <div class="carousel-caption" contenteditable="true">
                        <h4>冲浪</h4>
                        <p>冲浪是以海浪为动力，利用自身的高超技巧和平衡能力，搏击海浪的一项运动。运动员站立在冲浪板上，或利用腹板、跪板、充气的橡皮垫、划艇、皮艇等驾驭海浪的一项水上运动。</p>
                      </div>
                    </div>
                    <div class="item"> <img alt="" src="/news/iniImg/3.jpg">
                      <div class="carousel-caption" contenteditable="true">
                        <h4>自行车</h4>
                        <p>以自行车为工具比赛骑行速度的体育运动。1896年第一届奥林匹克运动会上被列为正式比赛项目。环法赛为最著名的世界自行车锦标赛。</p>
                      </div>
                    </div>
                  </div>
                  <a data-slide="prev" href="#myCarousel" class="left carousel-control">‹</a> <a data-slide="next" href="#myCarousel" class="right carousel-control">›</a> </div>
              </div>
            </div>
          </li>
        </ul>
        <ul class="nav nav-list accordion-group">
          <li class="nav-header"><i class="icon-plus icon-white"></i> 扩展控件 </li>
          <li style="display: none;" class="boxes mute" id="elmComm">
            <div class="preview">建设中...</div>
          </li>
        </ul>
      </div>
    </div>
    <!--/span-->
    <div style="min-height: 590px;" class="demo ui-sortable"></div>
    <!--/span-->
    <div id="download-layout">
      <div class="container-fluid"></div>
    </div>
  </div>
  <!--/row--> 
</div>
<!--/.fluid-container--> 
<div class="modal hide fade" role="dialog" id="editorModal">
  <div class="modal-header"> <a class="close" data-dismiss="modal">×</a>
    <h3>编辑</h3>
  </div>
  <div class="modal-body">
    <p>
      <textarea id="contenteditor"></textarea>
    </p>
  </div>
  <div class="modal-footer"> <a id="savecontent" class="btn btn-primary" data-dismiss="modal">保存</a> <a class="btn" data-dismiss="modal">关闭</a> </div>
</div>
<div class="modal hide fade" role="dialog" id="downloadModal">
  <div class="modal-header"> <a class="close" data-dismiss="modal">×</a>
    <h3>保存</h3>
  </div>
  <div class="modal-body">保存成功</div>
  <div class="modal-footer"> <a class="btn" data-dismiss="modal">Close</a> </div>
</div>
<div class="modal hide fade" role="dialog" id="shareModal">
  <div class="modal-header"> <a class="close" data-dismiss="modal">×</a>
    <h3>保存</h3>
  </div>
  <div class="modal-body">保存成功</div>
  <div class="modal-footer"> <a class="btn" data-dismiss="modal">Close</a> </div>
</div>

 <script>
   //var applyId = $("#applyId").val();
   //下一步
     	//$("#next").click(function(){
     	//$('#purl', parent.document).val("/DataShare/applyPage3ToJsp.action?applyId="+applyId);
        //$('#purl', parent.document).trigger("click");
     	//	window.location.href="/DataShare/applyPage3ToJsp.action?applyId="+applyId;
     	//})
        </script>
</body>
</html>