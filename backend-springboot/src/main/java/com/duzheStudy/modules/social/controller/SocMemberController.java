package com.duzheStudy.modules.social.controller;

import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.common.core.page.TableDataInfo;
import com.duzheStudy.modules.common.utils.poi.ExcelUtil;
import com.duzheStudy.modules.social.domain.SocMember;
import com.duzheStudy.modules.social.service.ISocMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 圈内成员Controller
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/social/member")
public class SocMemberController extends BaseController
{
    @Autowired
    private ISocMemberService socMemberService;

    /**
     * 查询圈内成员列表
     */
    @PreAuthorize("@ss.hasPermi('social:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(SocMember socMember)
    {
        startPage();
        List<SocMember> list = socMemberService.selectSocMemberList(socMember);
        return getDataTable(list);
    }

    /**
     * 导出圈内成员列表
     */
    @PreAuthorize("@ss.hasPermi('social:member:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocMember socMember)
    {
        List<SocMember> list = socMemberService.selectSocMemberList(socMember);
        ExcelUtil<SocMember> util = new ExcelUtil<SocMember>(SocMember.class);
        util.exportExcel(response, list, "圈内成员数据");
    }

    /**
     * 获取圈内成员详细信息
     */
    @PreAuthorize("@ss.hasPermi('social:member:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(socMemberService.selectSocMemberById(id));
    }

    /**
     * 新增圈内成员
     */
    @PreAuthorize("@ss.hasPermi('social:member:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SocMember socMember)
    {
        return toAjax(socMemberService.insertSocMember(socMember));
    }

    /**
     * 修改圈内成员
     */
    @PreAuthorize("@ss.hasPermi('social:member:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SocMember socMember)
    {
        return toAjax(socMemberService.updateSocMember(socMember));
    }

    /**
     * 删除圈内成员
     */
    @PreAuthorize("@ss.hasPermi('social:member:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(socMemberService.deleteSocMemberByIds(ids));
    }
}
