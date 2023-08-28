package com.zhoupb.mysite.serivce.account.feign;

import com.zhoupb.mysite.api.account.AccountFeignClient;
import com.zhoupb.mysite.common.JSONResponse;
import com.zhoupb.mysite.common.exception.CustomException;
import com.zhoupb.mysite.model.account.entity.Account;
import com.zhoupb.mysite.serivce.account.mapper.AccountMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountFeignController implements AccountFeignClient {

    private final AccountMapper accountMapper;

    public AccountFeignController(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public JSONResponse<Account> getAccountById(long id) {
        return JSONResponse.ok(HttpStatus.OK, accountMapper.selectByPrimaryKey(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "用户不存在")));
    }

}
