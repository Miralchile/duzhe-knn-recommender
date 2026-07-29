package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocComment;
import com.duzheStudy.modules.social.service.ISocCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 评论Controller
 * 
 * @author duzhe
 * @date 2023-10-16
 */
@RestController
@RequestMapping("/socail/comment")
public class SocCommentController extends BaseController
{
    @Autowired
    private ISocCommentService socCommentService;

    /**
     * 查询评论列表
     */
    @PreAuthorize("@ss.hasPermi('socail:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocComment socComment)
    {
        startPage();
        List<SocComment> list = socCommentService.selectSocCommentList(socComment);
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @PreAuthorize("@ss.hasPermi('socail:comment:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocComment socComment)
    {
        List<SocComment> list = socCommentService.selectSocCommentList(socComment);
        ExcelUtil<SocComment> util = new ExcelUtil<SocComment>(SocComment.class);
        util.exportExcel(response, list, "评论数据");
    }

    /**
     * 获取评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('socail:comment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socCommentService.selectSocCommentById(id));
    }

    /**
     * 新增评论
     */
    @PreAuthorize("@ss.hasPermi('socail:comment:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocComment socComment)
    {
        return toAjax(socCommentService.insertSocComment(socComment));
    }

    /**
     * 修改评论
     */
    @PreAuthorize("@ss.hasPermi('socail:comment:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocComment socComment)
    {
        return toAjax(socCommentService.updateSocComment(socComment));
    }

    /**
     * 删除评论
     */
    @PreAuthorize("@ss.hasPermi('socail:comment:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socCommentService.deleteSocCommentByIds(ids));
    }
}
