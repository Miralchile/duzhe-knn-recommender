package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocArticleLike;
import com.duzheStudy.modules.social.service.ISocArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 动态点赞Controller
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/social/article_like")
public class SocArticleLikeController extends BaseController
{
    @Autowired
    private ISocArticleLikeService socArticleLikeService;

    /**
     * 查询动态点赞列表
     */
    @PreAuthorize("@ss.hasPermi('social:article_like:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocArticleLike socArticleLike)
    {
        startPage();
        List<SocArticleLike> list = socArticleLikeService.selectSocArticleLikeList(socArticleLike);
        return getDataTable(list);
    }

    /**
     * 导出动态点赞列表
     */
    @PreAuthorize("@ss.hasPermi('social:article_like:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocArticleLike socArticleLike)
    {
        List<SocArticleLike> list = socArticleLikeService.selectSocArticleLikeList(socArticleLike);
        ExcelUtil<SocArticleLike> util = new ExcelUtil<SocArticleLike>(SocArticleLike.class);
        util.exportExcel(response, list, "动态点赞数据");
    }

    /**
     * 获取动态点赞详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:article_like:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socArticleLikeService.selectSocArticleLikeById(id));
    }

    /**
     * 新增动态点赞
     */
    @PreAuthorize("@ss.hasPermi('social:article_like:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocArticleLike socArticleLike)
    {
        return toAjax(socArticleLikeService.insertSocArticleLike(socArticleLike));
    }

    /**
     * 修改动态点赞
     */
    @PreAuthorize("@ss.hasPermi('social:article_like:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocArticleLike socArticleLike)
    {
        return toAjax(socArticleLikeService.updateSocArticleLike(socArticleLike));
    }

    /**
     * 删除动态点赞
     */
    @PreAuthorize("@ss.hasPermi('social:article_like:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socArticleLikeService.deleteSocArticleLikeByIds(ids));
    }
}
