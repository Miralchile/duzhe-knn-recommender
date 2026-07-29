package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocCommentLike;
import com.duzheStudy.modules.social.service.ISocCommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 评论点赞Controller
 * 
 * @author duzhe
 * @date 2023-10-16
 */
@RestController
@RequestMapping("/social/comment_like")
public class SocCommentLikeController extends BaseController
{
    @Autowired
    private ISocCommentLikeService socCommentLikeService;

    /**
     * 查询评论点赞列表
     */
    @PreAuthorize("@ss.hasPermi('social:comment_like:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocCommentLike socCommentLike)
    {
        startPage();
        List<SocCommentLike> list = socCommentLikeService.selectSocCommentLikeList(socCommentLike);
        return getDataTable(list);
    }

    /**
     * 导出评论点赞列表
     */
    @PreAuthorize("@ss.hasPermi('social:comment_like:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocCommentLike socCommentLike)
    {
        List<SocCommentLike> list = socCommentLikeService.selectSocCommentLikeList(socCommentLike);
        ExcelUtil<SocCommentLike> util = new ExcelUtil<SocCommentLike>(SocCommentLike.class);
        util.exportExcel(response, list, "评论点赞数据");
    }

    /**
     * 获取评论点赞详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:comment_like:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socCommentLikeService.selectSocCommentLikeById(id));
    }

    /**
     * 新增评论点赞
     */
    @PreAuthorize("@ss.hasPermi('social:comment_like:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocCommentLike socCommentLike)
    {
        return toAjax(socCommentLikeService.insertSocCommentLike(socCommentLike));
    }

    /**
     * 修改评论点赞
     */
    @PreAuthorize("@ss.hasPermi('social:comment_like:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocCommentLike socCommentLike)
    {
        return toAjax(socCommentLikeService.updateSocCommentLike(socCommentLike));
    }

    /**
     * 删除评论点赞
     */
    @PreAuthorize("@ss.hasPermi('social:comment_like:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socCommentLikeService.deleteSocCommentLikeByIds(ids));
    }
}
