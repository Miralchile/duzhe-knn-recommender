package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocGroup;
import com.duzheStudy.modules.social.service.ISocGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 读友圈Controller
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/social/group")
public class SocGroupController extends BaseController
{
    @Autowired
    private ISocGroupService socGroupService;

    /**
     * 查询读友圈列表
     */
    @PreAuthorize("@ss.hasPermi('social:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocGroup socGroup)
    {
        startPage();
        List<SocGroup> list = socGroupService.selectSocGroupList(socGroup);
        return getDataTable(list);
    }

    /**
     * 导出读友圈列表
     */
    @PreAuthorize("@ss.hasPermi('social:group:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocGroup socGroup)
    {
        List<SocGroup> list = socGroupService.selectSocGroupList(socGroup);
        ExcelUtil<SocGroup> util = new ExcelUtil<SocGroup>(SocGroup.class);
        util.exportExcel(response, list, "读友圈数据");
    }

    /**
     * 获取读友圈详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:group:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socGroupService.selectSocGroupById(id));
    }

    /**
     * 新增读友圈
     */
    @PreAuthorize("@ss.hasPermi('social:group:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocGroup socGroup)
    {
        return toAjax(socGroupService.insertSocGroup(socGroup));
    }

    /**
     * 修改读友圈
     */
    @PreAuthorize("@ss.hasPermi('social:group:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocGroup socGroup)
    {
        return toAjax(socGroupService.updateSocGroup(socGroup));
    }

    /**
     * 删除读友圈
     */
    @PreAuthorize("@ss.hasPermi('social:group:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socGroupService.deleteSocGroupByIds(ids));
    }
}
