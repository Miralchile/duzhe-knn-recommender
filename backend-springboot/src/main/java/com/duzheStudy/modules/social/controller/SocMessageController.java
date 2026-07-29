package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocMessage;
import com.duzheStudy.modules.social.service.ISocMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 好友信息Controller
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/social/message")
public class SocMessageController extends BaseController
{
    @Autowired
    private ISocMessageService socMessageService;

    /**
     * 查询好友信息列表
     */
    @PreAuthorize("@ss.hasPermi('social:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocMessage socMessage)
    {
        startPage();
        List<SocMessage> list = socMessageService.selectSocMessageList(socMessage);
        return getDataTable(list);
    }

    /**
     * 导出好友信息列表
     */
    @PreAuthorize("@ss.hasPermi('social:message:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocMessage socMessage)
    {
        List<SocMessage> list = socMessageService.selectSocMessageList(socMessage);
        ExcelUtil<SocMessage> util = new ExcelUtil<SocMessage>(SocMessage.class);
        util.exportExcel(response, list, "好友信息数据");
    }

    /**
     * 获取好友信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socMessageService.selectSocMessageById(id));
    }

    /**
     * 新增好友信息
     */
    @PreAuthorize("@ss.hasPermi('social:message:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocMessage socMessage)
    {
        return toAjax(socMessageService.insertSocMessage(socMessage));
    }

    /**
     * 修改好友信息
     */
    @PreAuthorize("@ss.hasPermi('social:message:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocMessage socMessage)
    {
        return toAjax(socMessageService.updateSocMessage(socMessage));
    }

    /**
     * 删除好友信息
     */
    @PreAuthorize("@ss.hasPermi('social:message:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socMessageService.deleteSocMessageByIds(ids));
    }
}
