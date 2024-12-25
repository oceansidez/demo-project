package com.telecom.manage.controller.official;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.config.WebConfig;
import com.telecom.manage.entity.WechatReply;
import com.telecom.manage.entity.WechatReply.ContentType;
import com.telecom.manage.entity.WechatReply.ReplyType;
import com.telecom.manage.service.WechatReplyService;
import com.telecom.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信回复
 *
 */
@Controller
@RequestMapping(value = "wechatReply")
public class WechatReplyController extends BaseController {

	@Autowired
	WechatReplyService wechatReplyService;
	
	@Autowired
	WebConfig webConfig;

	private final String PAGE_PRE = "/official/wechat_reply_";
	
	// 回复列表
	@GetMapping("list")
	public String list(@RequestParam(value = "type", required=true) String type,
			Pager pager, ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		if (pager != null && pager.getSearchMap() != null
				&& pager.getSearchMap().size() > 0) {
			map = pager.getSearchMap();
		}
		map.put("type", type);
		pager.setSearchMap(map);
		pager = wechatReplyService.findPager(pager);
		model.put("pager", pager);
		model.put("type", type);
		return PAGE_PRE + "list";
	}
	
	// 添加回复界面
	@GetMapping("add")
	public String add(@RequestParam(value = "type", required=true) String type, ModelMap model) {
		model.put("isAdd", true);
		model.put("type", type);
		model.put("contentTypeList", ContentType.values());
		return PAGE_PRE + "input";
	}
	
	// 编辑回复界面
	@GetMapping("edit/{id}")
	public String edit(@PathVariable(value="id") String id, ModelMap model) {
		WechatReply wechatReply = wechatReplyService.get(id);
		model.put("reply", wechatReply);
		model.put("isAdd", false);
		model.put("type", wechatReply.getType());
		model.put("contentTypeList", ContentType.values());
		return PAGE_PRE + "input";
	}
	
	// 上传图片附件
	@ResponseBody
	@RequestMapping("ajaxUpload")
	public String ajaxUpload(@RequestParam(value = "uploadImg", required=false) MultipartFile uploadImg){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (uploadImg == null) {
				jsonMap.put("error", 1);
				jsonMap.put("message", "请选择上传文件!");
				return ajax(jsonMap);
			}

			FileUtil.uploadFile(uploadImg, webConfig.getUploadPath() + "/wechat/", uploadImg.getOriginalFilename());
			String filePath = "/wechat/" + uploadImg.getOriginalFilename();
			jsonMap.put("error", 0);
			jsonMap.put("url", filePath);
			return ajax(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", 1);
			jsonMap.put("message", "处理异常!");
			return ajax(jsonMap);
		}

	}
	
	// 保存
	@PostMapping("save")
	public String save(WechatReply reply, ModelMap model) {
		if(StringUtils.isEmpty(reply.getContentType())) {
			setError(model, "请选择回复类型", null);
			return ERROR;
		}
		// 不同的回复类型需要验证的字段不一样
		if(reply.getContentType().equals(ContentType.image.toString())) { //图片
			if(StringUtils.isEmpty(reply.getImgUrl())) {
				setError(model, "请上传图片", null);
				return ERROR;
			}
		}else if(reply.getContentType().equals(ContentType.text.toString())) { //文本
			if(StringUtils.isEmpty(reply.getContent())) {
				setError(model, "请输入回复内容", null);
				return ERROR;
			}
		}else if(reply.getContentType().equals(ContentType.graphics.toString())) { //图文
			if(StringUtils.isEmpty(reply.getTitle())) {
				setError(model, "请输入回复标题", null);
				return ERROR;
			}
			if(StringUtils.isEmpty(reply.getImgUrl())) {
				setError(model, "请上传图片", null);
				return ERROR;
			}
			if(StringUtils.isEmpty(reply.getContent())) {
				setError(model, "请输入回复内容", null);
				return ERROR;
			}
			if(StringUtils.isEmpty(reply.getUrl())) {
				setError(model, "请输入链接地址", null);
				return ERROR;
			}
		}
		
		if(StringUtils.isEmpty(reply.getTitle())){
			reply.setTitle(null);
		}
		if(StringUtils.isEmpty(reply.getContent())){
			reply.setContent(null);
		}
		if(StringUtils.isEmpty(reply.getImgUrl())){
			reply.setImgUrl(null);
		}
		if(StringUtils.isEmpty(reply.getUrl())){
			reply.setUrl(null);
		}
		
		wechatReplyService.save(reply);
		
		String url = null;
		if(reply.getType().equals(ReplyType.defaults.toString())) {
			url = "wechatReply/list?type=" + ReplyType.defaults.toString();
		}else if(reply.getType().equals(ReplyType.keyword.toString())) {
			url = "wechatReply/list?type=" + ReplyType.keyword.toString();
		}else if(reply.getType().equals(ReplyType.attention.toString())) {
			url = "wechatReply/list?type=" + ReplyType.attention.toString();
		}else if(reply.getType().equals(ReplyType.click.toString())) {
			url = "wechatReply/list?type=" + ReplyType.click.toString();
		}
		setSuccess(model, "保存成功", url);
		return SUCCESS;
	}
	
	// 更新
	@PostMapping("update")
	public String update(@RequestParam(value="id") String id, 
			WechatReply reply, ModelMap model) {
		if(StringUtils.isEmpty(reply.getContentType())) {
			setError(model, "请选择回复类型", null);
			return ERROR;
		}
		// 不同的回复类型需要验证的字段不一样
		if(reply.getContentType().equals(ContentType.image.toString())) { //图片
			if(StringUtils.isEmpty(reply.getImgUrl())) {
				setError(model, "请上传图片", null);
				return ERROR;
			}
		}else if(reply.getContentType().equals(ContentType.text.toString())) { //文本
			if(StringUtils.isEmpty(reply.getContent())) {
				setError(model, "请输入回复内容", null);
				return ERROR;
			}
		}else if(reply.getContentType().equals(ContentType.graphics.toString())) { //图文
			if(StringUtils.isEmpty(reply.getTitle())) {
				setError(model, "请输入回复标题", null);
				return ERROR;
			}
			if(StringUtils.isEmpty(reply.getImgUrl())) {
				setError(model, "请上传图片", null);
				return ERROR;
			}
			if(StringUtils.isEmpty(reply.getContent())) {
				setError(model, "请输入回复内容", null);
				return ERROR;
			}
			if(StringUtils.isEmpty(reply.getUrl())) {
				setError(model, "请输入链接地址", null);
				return ERROR;
			}
		}
		
		if(reply.getContentType().equals(ContentType.text.toString())){ //文本
			reply.setTitle(null);
			reply.setImgUrl(null);
		}else if(reply.getContentType().equals(ContentType.image.toString())) { //图片
			reply.setTitle(null);
		}
		
		WechatReply persistent = wechatReplyService.get(id);
		BeanUtils.copyProperties(reply, persistent, new String[]{"id", "createDate", "modifyDate"});
		
		if(StringUtils.isEmpty(persistent.getTitle())){
			persistent.setTitle(null);
		}
		if(StringUtils.isEmpty(persistent.getContent())){
			persistent.setContent(null);
		}
		if(StringUtils.isEmpty(persistent.getImgUrl())){
			persistent.setImgUrl(null);
		}
		if(StringUtils.isEmpty(persistent.getUrl())){
			persistent.setUrl(null);
		}
		wechatReplyService.update(persistent);
		
		String url = null;
		if(reply.getType().equals(ReplyType.defaults.toString())) {
			url = "wechatReply/list?type=" + ReplyType.defaults.toString();
		}else if(reply.getType().equals(ReplyType.keyword.toString())) {
			url = "wechatReply/list?type=" + ReplyType.keyword.toString();
		}else if(reply.getType().equals(ReplyType.attention.toString())) {
			url = "wechatReply/list?type=" + ReplyType.attention.toString();
		}else if(reply.getType().equals(ReplyType.click.toString())) {
			url = "wechatReply/list?type=" + ReplyType.click.toString();
		}
		setSuccess(model, "保存成功", url);
		return SUCCESS;
	}
	
	// 关闭/开放回复
	@ResponseBody
	@PostMapping("switch/{id}")
	public String switchItem(@PathVariable(value="id") String id, ModelMap model) {
		WechatReply reply = wechatReplyService.get(id);
		if(reply.getIsOpen()) {
			reply.setIsOpen(false);
		}else {
			reply.setIsOpen(true);
		}
		wechatReplyService.update(reply);
		return ajax(Status.success, "操作成功");
	}
	
	// 删除回复
	@ResponseBody
	@PostMapping("delete")
	public String delete(@RequestParam(value="ids") String[] ids) {
		for (String id : ids) {
			wechatReplyService.delete(id);
		}
		return ajax(Status.success, "删除成功!");
	}
	
	// 获取展示地址
	@ModelAttribute
	public void getViewUrl(ModelMap model) {
		model.addAttribute("viewUrl", webConfig.getViewPath());
	}
}
