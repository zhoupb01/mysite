package com.zhoupb.mysite.serivce.account.controller.v1;

import com.zhoupb.mysite.common.JSONResponse;
import com.zhoupb.mysite.common.util.JWTUtil;
import com.zhoupb.mysite.model.account.dto.SignInDTO;
import com.zhoupb.mysite.model.account.entity.Account;
import com.zhoupb.mysite.serivce.account.mapper.AccountMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/authorization")
public record AuthorizationController(AccountMapper accountMapper) {

    @PostMapping("/signin")
    public JSONResponse<String> signin(@RequestBody SignInDTO dto) {
        Optional<Account> accountOptional = accountMapper.wrapper().eq(Account::getUsername, dto.username()).first();
        if ( accountOptional.isEmpty() )
            //                                             防止有心人试账号
            return JSONResponse.fail(HttpStatus.FORBIDDEN, "账号或密码错误");
        Account dbAccount = accountOptional.get();
        // 验证密码是否正确
        if ( !BCrypt.checkpw(dto.password(), dbAccount.getPassword()) )
            return JSONResponse.fail(HttpStatus.FORBIDDEN, "账号或密码错误");
        if ( !dbAccount.getActive() )
            return JSONResponse.fail(HttpStatus.FORBIDDEN, "账号还未激活");
        return JSONResponse.ok(HttpStatus.OK, JWTUtil.generateToken(dbAccount.getId()), "登录成功");
    }

}
