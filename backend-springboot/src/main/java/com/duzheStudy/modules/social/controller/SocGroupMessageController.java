package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocGroupMessage;
import com.duzheStudy.modules.social.service.ISocGroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 圈信息Controller
 *
 * @author duzhe
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/social/group_message")
public class SocGroupMessageController extends BaseController
{
    @Autowired
    private ISocGroupMessageService socGroupMessageService;

    /**
     * 查询圈信息列表
     */
    @PreAuthorize("@ss.hasPermi('social:group_message:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocGroupMessage socGroupMessage)
    {
        startPage();
        List<SocGroupMessage> list = socGroupMessageService.selectSocGroupMessageList(socGroupMessage);
        return getDataTable(list);
    }

    /**
     * 导出圈信息列表
     */
    @PreAuthorize("@ss.hasPermi('social:group_message:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocGroupMessage socGroupMessage)
    {
        List<SocGroupMessage> list = socGroupMessageService.selectSocGroupMessageList(socGroupMessage);
        ExcelUtil<SocGroupMessage> util = new ExcelUtil<SocGroupMessage>(SocGroupMessage.class);
        util.exportExcel(response, list, "圈信息数据");
    }

    /**
     * 获取圈信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:group_message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socGroupMessageService.selectSocGroupMessageById(id));
    }

    /**
     * 新增圈信息
     */
    @PreAuthorize("@ss.hasPermi('social:group_message:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocGroupMessage socGroupMessage)
    {
        return toAjax(socGroupMessageService.insertSocGroupMessage(socGroupMessage));
    }

    /**
     * 修改圈信息
     */
    @PreAuthorize("@ss.hasPermi('social:group_message:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocGroupMessage socGroupMessage)
    {
        return toAjax(socGroupMessageService.updateSocGroupMessage(socGroupMessage));
    }

    /**
     * 删除圈信息
     */
    @PreAuthorize("@ss.hasPermi('social:group_message:remove')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socGroupMessageService.deleteSocGroupMessageByIds(ids));
    }
}
