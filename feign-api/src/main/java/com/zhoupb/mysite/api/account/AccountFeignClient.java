package com.zhoupb.mysite.api.account;

import com.zhoupb.mysite.common.JSONResponse;
import com.zhoupb.mysite.model.account.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-account")
public interface AccountFeignClient {

    @GetMapping("/v1/account/data")
    JSONResponse<Account> getAccountById(@RequestParam("id") long id);

}
