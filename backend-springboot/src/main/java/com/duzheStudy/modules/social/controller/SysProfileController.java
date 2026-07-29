package com.duzheStudy.modules.social.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.common.core.controller.BaseController;
import com.duzheStudy.modules.common.core.domain.AjaxResult;
import com.duzheStudy.modules.user.domain.User;
import org.springframework.web.bind.annotation.*;

/**
 * 个人信息 业务处理
 * 
 * @author readerstation
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile()
    {
        User currentUser = UserUtils.getLoginUser();
        AjaxResult ajax = AjaxResult.success(currentUser);
        return ajax;
    }

}
