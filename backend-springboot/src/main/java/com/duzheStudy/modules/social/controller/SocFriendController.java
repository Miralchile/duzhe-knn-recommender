package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocFriend;
import com.duzheStudy.modules.social.service.ISocFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 好友Controller
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/social/friend")
public class SocFriendController extends BaseController
{
    @Autowired
    private ISocFriendService socFriendService;

    /**
     * 查询好友列表
     */
    @PreAuthorize("@ss.hasPermi('social:friend:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocFriend socFriend)
    {
        startPage();
        List<SocFriend> list = socFriendService.selectSocFriendList(socFriend);
        return getDataTable(list);
    }

    /**
     * 导出好友列表
     */
    @PreAuthorize("@ss.hasPermi('social:friend:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocFriend socFriend)
    {
        List<SocFriend> list = socFriendService.selectSocFriendList(socFriend);
        ExcelUtil<SocFriend> util = new ExcelUtil<SocFriend>(SocFriend.class);
        util.exportExcel(response, list, "好友数据");
    }

    /**
     * 获取好友详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:friend:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socFriendService.selectSocFriendById(id));
    }

    /**
     * 新增好友
     */
    @PreAuthorize("@ss.hasPermi('social:friend:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocFriend socFriend)
    {
        return toAjax(socFriendService.insertSocFriend(socFriend));
    }

    /**
     * 修改好友
     */
    @PreAuthorize("@ss.hasPermi('social:friend:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocFriend socFriend)
    {
        return toAjax(socFriendService.updateSocFriend(socFriend));
    }

    /**
     * 删除好友
     */
    @PreAuthorize("@ss.hasPermi('social:friend:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socFriendService.deleteSocFriendByIds(ids));
    }
}
