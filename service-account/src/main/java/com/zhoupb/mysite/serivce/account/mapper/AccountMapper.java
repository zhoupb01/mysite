package com.zhoupb.mysite.serivce.account.mapper;

import com.zhoupb.mysite.model.account.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper extends io.mybatis.mapper.Mapper<Account, Long> {

}
