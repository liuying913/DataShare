<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css"/>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css"/>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css"/>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css"/>
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
</head>
<body>
<input type="hidden" id="earthQuakeId" name="earthQuakeId" value="${earthQuakeId}"/>

<div id="iframeheight" style="height: 459px;">
    <div class="pbox tspbox" style="padding-bottom:0px;">
        <div class="ptit">${applyTitle}</div>
    </div>
    <div class="pbox">

        <br>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
            <tr>
                <th style="white-space: nowrap;">申请人</th>
                <th style="white-space: nowrap;">所属单位</th>
                <th style="white-space: nowrap;">申请时间</th>
                <th style="white-space: nowrap;">下载有效期</th>
                <th style="white-space: nowrap;">申请状态</th>
                <th class="ts" style="white-space: nowrap;">操作</th>
            </tr>
            <tbody id="tbody">
            </tbody>
        </table>
        <!--分页-->
        <div class="newstable-content">
            <div class="newstable-fy">
                <div class="newstable-fy-pages">
                    <span>共<font color="#ff0000" id="zongyeshu"></font>页<font color="#ff0000" id="zongshuju"></font>条记录</span>
                    <a href="#" onclick="yeshu(1)">首页</a>
                    <a href="#" id="prevpage" onclick="shang();">上一页</a>
	                    <span class="everypages">
	                    </span>
                    <a href="#" onclick="next();">下一页</a>
                    <a href="#" onclick="yeshu('end')">末页</a>
	                    <span>跳转至
	                        <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);"
                                    class="optionpages">
                            </select>
	                    </span>
                </div>
            </div>
            
             <div class="warp-button" style="padding-left:0px;text-align: center">
	            <button type="button" id="apply" class="shengfen-button" style="float: none;display: inline-block">申请</button>
	            <button type="button" id="back" class="shengfen-button" style="margin-left: 15px;float: none;display: inline-block">返回</button>
	        </div>
            
            
        </div>
    </div>

</div>
<script>
    $.ajaxSetup({ cache: false });//全局禁用缓存
    var earthQuakeId = $("#earthQuakeId").val();
    $(document).ready(function () {
        var url = window.location.href;
        $(".base-info").addClass("base-info-back");
        $(".input01").attr("disabled", "disabled");

        //地震详情
        ajaxRequest("/DataShare/showData/earthQuakeMessage.action?earthQuakeId=" + earthQuakeId, "get", function (res) {
            $(".input01").eq(0).val(res[0].name);
            $(".input01").eq(1).val(res[0].strTime);
            $(".input01").eq(2).val(res[0].siteLat);
            $(".input01").eq(3).val(res[0].siteLon);
            $(".input01").eq(4).val(res[0].grade);
            $(".input01").eq(5).val(res[0].height);
            $(".input01").eq(6).val(res[0].address);
            $(".input01").eq(7).val(res[0].remark);
        });
        //申请列表
        ajaxRequest("/DataShare/earthQuake/applyEventListForUser.action?earthQuakeId=" + earthQuakeId, "get", function (res) {
            data = res;
            shua();
            page();
        });
        //判断是否出现社申请按钮
        ajaxRequest("/DataShare/earthQuake/applyEndTime.action?earthQuakeId=" + earthQuakeId, "get", function (res) {
            var flag = res['date'][0]['applyFlag'];
            if (flag.indexOf("-1") > -1) {
                $("#apply").hide();
            } else if (flag == "1") {
                $("#apply").show();
            }
        });
    })

    //申请列表分页
    function shua() {
        if (flag != 0) {
            page();
        } else {
            flag = 1;
        }

        str = "";
        //var stateArr = ["待审核","审核通过","未通过","申请过期"];
        var colorAll =["#e18cde","#47a447","#ff9873","#7bc0f2"];

        $.each(data, function (key, value) {
            /*申请状态添加不同的颜色*/
            var  strColor = "";
            if( value.type == 4 ){
                strColor = "<td style='color:"+colorAll[0]+"'>"+value.typeStr+"</td>";
            }else  	if( value.type == 5 ){
                strColor = "<td style='color:"+colorAll[1]+"'>"+value.typeStr+"</td>";
            }else  	if( value.type == 6 ){
                strColor = "<td style='color:"+colorAll[2]+"'>"+value.typeStr+"</td>";
            }else  	if( value.type == 7 ){
                strColor = "<td style='color:"+colorAll[3]+"'>"+value.typeStr+"</td>";
            }

            if (key >= (shu - 1) * num_value && key < shu * num_value) {

                if (key % 2 != 0) {
                    str = str + "<tr class='even'>"
                            + "	    <td>" + value.user_cname + "</td>"
                            + "	    <td>" + value.user_unit + "</td>"
                            + "	    <td>" + value.createTimeStr + "</td>"
                            + "	    <td>" + value.startTimeStr + "—" + value.endTimeStr + "</td>"
                            +       strColor
                            + "	    <td><a href='/DataShare/earthQuake/queryCheckToJsp.action?id="+value.id+"&applyTitle=应急数据申请查看'>详情查看</a></td>"
                            + "    </tr>"
                }
                else {
                    str = str + "<tr>"
                            + "	    <td>" + value.user_cname + "</td>"
                            + "	    <td>" + value.user_unit + "</td>"
                            + "	    <td>" + value.createTimeStr + "</td>"
                            + "	    <td>" + value.startTimeStr + "—" + value.endTimeStr + "</td>"
                            +       strColor
                            + "	    <td><a href='/DataShare/earthQuake/queryCheckToJsp.action?id="+value.id+"&applyTitle=应急数据申请查看'>详情查看</a></td>"
                            + "    </tr>"
                }
            }

        });
        $("#tbody").html(str);
        $('#iframe', parent.document).css("height", $("#iframeheight").height());
    }

    //点击申请按钮
    $("#apply").click(function () {
        ajaxRequest("/DataShare/earthQuake/insertApply.action?earthquake_id=" + earthQuakeId, "get", function (res) {
            var flag = res['date'][0]['code'];
            if (flag == "1") {
                init( "申请成功！",0);
                $(".confirm",window.parent.document).click( function(){
                    window.location.href = "/DataShare/earthQuake/applyListToJsp.action?earthQuakeId=" + earthQuakeId + "&applyTitle=数据申请";
                })
                //window.location.href = "/DataShare/earthQuake/applyListToJsp.action?earthQuakeId=" + earthQuakeId + "&applyTitle=数据申请";
            }
        });
    });
    //点击返回
    $("#back").click(function () {
        window.history.back(-1);
    });
</script>
</body>
</html>
