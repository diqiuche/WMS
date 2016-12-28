package com.wms.controller;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.wms.bean.Godown;
import com.wms.bean.vo.UserVo;
import com.wms.commons.bean.ComboBox4EasyUI;
import com.wms.commons.utils.PageInfo;
import com.wms.commons.utils.StringUtils;
import com.wms.service.GodownService;
import com.wms.service.IUserService;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：仓库表控制器层
 * Created by ZhongYu on 2016/12/26.
 */
@RequestMapping("godown")
@Controller
public class GodownController {

    @Autowired
    private GodownService godownService;
    
    @Autowired
    private IUserService userService;

    /** 仓库EasyUI下拉框 */
    @ResponseBody
    @GetMapping("godownComboBox")
    public List<ComboBox4EasyUI> listComboBox4EasyUi() {
        List<ComboBox4EasyUI> comboBox4EasyUIS = new ArrayList<>();
        List<Godown> godowns = godownService.godownComboBox();
        for (Godown godown : godowns) {
            ComboBox4EasyUI comboBox4EasyUI = new ComboBox4EasyUI();
            comboBox4EasyUI.setId(String.valueOf(godown.getGoId()));
            comboBox4EasyUI.setText(godown.getGoWhid());
            comboBox4EasyUIS.add(comboBox4EasyUI);
        }
        return comboBox4EasyUIS;
    }

    @GetMapping("page")
    public String page(){
    	return "data/godown";
    }
    
    @PostMapping("/dataGrid")
    @ResponseBody
    public Object dataGrid(Godown godown, Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows);
        Map<String, Object> condition = new HashMap<String, Object>();

        pageInfo.setCondition(condition);
        godownService.selectDataGrid(pageInfo);
        return pageInfo;
    }
    
    @GetMapping("addPage")
    public String addpage(){
    	return "data/godownAdd";
    }
    
    @GetMapping("editPage")
    public String editpage(){
    	return "data/godownEdit";
    }

    @GetMapping("getUser")
    public @ResponseBody Object getUserName(){
    	System.out.println("11111111111111111111111111");
    	List<UserVo> userList = userService.selectByRole();
    	List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
    	if(userList.size() != 0){
    		for(UserVo lists : userList){
    			HashMap<String, String> map = new HashMap<String,String>();
    			map.put("userName", lists.getName());
    			map.put("userId", lists.getId().toString());
    			list.add(map);
    		}
    	}
    	JSONArray json = JSONArray.fromObject(list);
    	System.out.println(json.toString());
    	return json;
    }
}