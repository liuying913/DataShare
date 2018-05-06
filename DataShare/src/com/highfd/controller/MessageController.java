package com.highfd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.MessageBoard;
import com.highfd.bean.MessageBoardTotal;
import com.highfd.bean.MessageComment;
import com.highfd.bean.UserInfo;
import com.highfd.service.MessageService;

/**
 * 留言板
 * @author liuying
 *
 */
@Component
@Controller
@RequestMapping(value="/message")
public class MessageController {

	@Autowired
	MessageService messageService;
	
	/**
	 * 统计留言板总量
	 */
	@RequestMapping(value = "/total", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String total(HttpServletRequest request) {
		MessageBoardTotal messageBoardTotal  = messageService.getMessageBoardTotal(false);
		MessageBoardTotal messageBoardTotal2 = messageService.getMessageBoardTotal(true);
		messageBoardTotal.setMonthCou(messageBoardTotal2.getCou());
		messageBoardTotal.setMonthSumCom(messageBoardTotal2.getSumCom());
		messageBoardTotal.setMonthSumBro(messageBoardTotal2.getSumBro());
		JSONArray json = JSONArray.fromObject(messageBoardTotal);
		return json.toString();
	}
	
	/**
	 * 留言板 首页矩阵列表（前10条）
	 */
	@RequestMapping(value = "/boardTop10", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String boardTop10(HttpServletRequest request) throws NumberFormatException, Exception {
		List<MessageBoard> messageBoard = messageService.boardTop10("10");
		JSONArray json = JSONArray.fromObject(messageBoard);
		return json.toString();
	}
	
	/**
	 * 留言板 内容
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/board", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String board(HttpServletRequest request) throws NumberFormatException, Exception {
		String board_id = request.getParameter("board_id");
		String param = request.getParameter("param");
		List<MessageBoard> messageBoard = messageService.getMessageBoardList(board_id, param);
		if(null!=board_id){
			messageService.updateMessageBoard(board_id, Integer.valueOf(messageBoard.get(0).getBrowse_number())+1+"");
		}
		JSONArray json = JSONArray.fromObject(messageBoard);
		return json.toString();
	}
	//id,user_id,title,contents,browse_number,comment_number,contact_mobile,contact_people,orders,type,createtime 
	
	
	/**
	 * 跳转到留言板分页界面
	 */
	@RequestMapping(value = "boardPageToJsp")
	@ResponseBody
	public ModelAndView boardPageListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String  board_id = request.getParameter("board_id");
		ModelAndView mav = new ModelAndView();
		mav.addObject("board_id",  board_id);
		mav.setViewName("main/message/boardPage");
		return mav;
	}

	
	/**
	 * 跳转到留言板评论内容界面
	 */
	@RequestMapping(value = "commentToJsp")
	@ResponseBody
	public ModelAndView commentToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String  board_id = request.getParameter("board_id");
		ModelAndView mav = new ModelAndView();
		mav.addObject("board_id",  board_id);
		mav.setViewName("main/message/comment");
		return mav;
	}
	
	/**
	 * 留言 评论 信息
	 */
	@RequestMapping(value = "/comment", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String comment(HttpServletRequest request) {
		String board_id = request.getParameter("board_id");
		String parentid = request.getParameter("parentid");
		String type = request.getParameter("type");
		String param = request.getParameter("param");
		List<MessageComment> messageComment = messageService.getMessageCommentList(board_id,parentid, type, param);
		JSONArray json = JSONArray.fromObject(messageComment);
		return json.toString();
	}
	
	
	/**
	 * 添加 留言 大
	 */
	@RequestMapping(value = "insertBoard", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertBoard(HttpServletRequest request,@ModelAttribute MessageBoard mb) throws Exception {
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		int userId = userInfo.getUserId();
		mb.setUserId(userId);
		messageService.insertMessageBoard(mb);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"成功！\"}]}";
	}
	
	/**
	 * 添加 留言评论 信息
	 */
	@RequestMapping(value = "insertComment", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertComment(HttpServletRequest request,@ModelAttribute MessageComment mc) throws Exception {
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		int userId = userInfo.getUserId();
		mc.setUser_id(userId+"");
		
		if(null!=mc.getBoard_id()){
			List<MessageBoard> messageBoard = messageService.getMessageBoardList(mc.getBoard_id(), "");
			messageService.updateMessageComment(mc.getBoard_id(), Integer.valueOf(messageBoard.get(0).getComment_number())+1+"");
		}
		messageService.insertMessageComment(mc);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"成功！\"}]}";
	}
	
	
	/**
	 * 删除 留言评论 信息
	 */
	@RequestMapping(value = "deleteComment", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String deleteComment(HttpServletRequest request) throws Exception {
		String commentid = request.getParameter("commentid");
		String board_id = request.getParameter("board_id");
		messageService.deleteComment(board_id,commentid);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"成功！\"}]}";
	}
	
	/**
	 * 删除 留言评论 信息
	 */
	@RequestMapping(value = "deleteParent", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String deleteParent(HttpServletRequest request) throws Exception {
		String commentid = request.getParameter("commentid");
		messageService.deleteParentid(commentid);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"成功！\"}]}";
	}
}