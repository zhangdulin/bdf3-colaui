package com.bstek.cola.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.security.orm.Url;
import com.bstek.cola.security.service.UrlService;


/** 
* 
* @author bob.yang
* @since 2017年12月15日
*
*/

@RestController("cola.urlController")
@RequestMapping("/api")
@Transactional(readOnly = true)
public class UrlController {

	@Autowired
	private UrlService urlService;
	
	@RequestMapping(path = "/url/load-tree", method = RequestMethod.GET)
	public List<Url> loadTree() {
		return urlService.loadTree();
	}
	
	@RequestMapping(path = "/url/loadTopByRoleId", method = RequestMethod.GET)
	public List<Url> loadTopByRoleId(@RequestParam(name = "roleId", required = false) String roleId) {
		return urlService.loadTopByRoleId(roleId);
	}
	
	@RequestMapping(path = "/url/loadSubByRoleId", method = RequestMethod.GET)
	public List<Url> loadSubByRoleId(@RequestParam(name = "parentId", required = false) String parentId, @RequestParam(name = "roleId", required = false) String roleId) {
		return urlService.loadSubByRoleId(parentId, roleId);
	}
	
	@RequestMapping(path = "/url/loadTop", method = RequestMethod.GET)
	public List<Url> loadTop() {
		return urlService.loadTop();
	}
	
	@RequestMapping(path = "/url/loadSub", method = RequestMethod.GET)
	public List<Url> loadSub(@RequestParam(required=false) String parentId) {
		return urlService.loadSub(parentId);
	}
	
	@RequestMapping(path = "/url/load", method = RequestMethod.GET)
	public List<Url> load() {
		return urlService.load();
	}
	
	@RequestMapping(path = "/url/remove", method = RequestMethod.POST)
	@Transactional
	public void remove(@RequestParam String id) {
		urlService.remove(id);
	}
	
	@RequestMapping(path = "/url/add", method = RequestMethod.POST)
	@Transactional
	public String add(@RequestBody Url url) {
		return urlService.add(url);
	}

	@RequestMapping(path = "/url/modify", method = RequestMethod.PUT)
	@Transactional
	public void modify(@RequestBody Url url) {
		urlService.modify(url);
	}
	
	@RequestMapping(path = "/url/exist", method = RequestMethod.GET)
	public boolean validate(@RequestParam String name) {
		return !urlService.isExist(name);
	}
	
}
