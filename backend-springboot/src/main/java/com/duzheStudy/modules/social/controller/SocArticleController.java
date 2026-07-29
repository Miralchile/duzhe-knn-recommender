package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocArticle;
import com.duzheStudy.modules.social.service.ISocArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 动态Controller
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/social/article")
public class SocArticleController extends BaseController
{
    @Autowired
    private ISocArticleService socArticleService;

    /**
     * 查询动态列表
     */
    @PreAuthorize("@ss.hasPermi('social:article:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocArticle socArticle)
    {
        startPage();
        List<SocArticle> list = socArticleService.selectSocArticleList(socArticle);
        return getDataTable(list);
    }

    /**
     * 导出动态列表
     */
    @PreAuthorize("@ss.hasPermi('social:article:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocArticle socArticle)
    {
        List<SocArticle> list = socArticleService.selectSocArticleList(socArticle);
        ExcelUtil<SocArticle> util = new ExcelUtil<SocArticle>(SocArticle.class);
        util.exportExcel(response, list, "动态数据");
    }

    /**
     * 获取动态详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:article:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socArticleService.selectSocArticleById(id));
    }

    /**
     * 新增动态
     */
    @PreAuthorize("@ss.hasPermi('social:article:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocArticle socArticle)
    {
        return toAjax(socArticleService.insertSocArticle(socArticle));
    }

    /**
     * 修改动态
     */
    @PreAuthorize("@ss.hasPermi('social:article:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocArticle socArticle)
    {
        return toAjax(socArticleService.updateSocArticle(socArticle));
    }

    /**
     * 删除动态
     */
    @PreAuthorize("@ss.hasPermi('social:article:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socArticleService.deleteSocArticleByIds(ids));
    }
}
