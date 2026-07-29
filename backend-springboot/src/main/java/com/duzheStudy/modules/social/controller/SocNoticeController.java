package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocNotice;
import com.duzheStudy.modules.social.service.ISocNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 通知Controller
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/social/notice")
public class SocNoticeController extends BaseController
{
    @Autowired
    private ISocNoticeService socNoticeService;

    /**
     * 查询通知列表
     */
    @PreAuthorize("@ss.hasPermi('social:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocNotice socNotice)
    {
        startPage();
        List<SocNotice> list = socNoticeService.selectSocNoticeList(socNotice);
        return getDataTable(list);
    }

    /**
     * 导出通知列表
     */
    @PreAuthorize("@ss.hasPermi('social:notice:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocNotice socNotice)
    {
        List<SocNotice> list = socNoticeService.selectSocNoticeList(socNotice);
        ExcelUtil<SocNotice> util = new ExcelUtil<SocNotice>(SocNotice.class);
        util.exportExcel(response, list, "通知数据");
    }

    /**
     * 获取通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:notice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socNoticeService.selectSocNoticeById(id));
    }

    /**
     * 新增通知
     */
    @PreAuthorize("@ss.hasPermi('social:notice:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocNotice socNotice)
    {
        return toAjax(socNoticeService.insertSocNotice(socNotice));
    }

    /**
     * 修改通知
     */
    @PreAuthorize("@ss.hasPermi('social:notice:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocNotice socNotice)
    {
        return toAjax(socNoticeService.updateSocNotice(socNotice));
    }

    /**
     * 删除通知
     */
    @PreAuthorize("@ss.hasPermi('social:notice:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socNoticeService.deleteSocNoticeByIds(ids));
    }
}
