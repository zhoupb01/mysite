package com.zhoupb.mysite.serivce.account.controller.v1;

import com.zhoupb.mysite.common.JSONResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    @GetMapping("/{id}")
    public JSONResponse profile(@PathVariable long id, @RequestParam long accountId) {
        return JSONResponse.ok(HttpStatus.OK, accountId);
    }

}
