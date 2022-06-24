package com.hmdp.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.service.IShopTypeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/shop-type")
public class ShopTypeController {
    @Resource
    private IShopTypeService typeService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("list")
    public Result queryTypeList() {
        if(StrUtil.isBlank(stringRedisTemplate.opsForValue().get("list"))){
            List<ShopType> typeList = typeService
                    .query().orderByAsc("sort").list();
            stringRedisTemplate.opsForValue().set("list",JSONUtil.toJsonStr(typeList));
            return Result.ok(typeList);
        }else {
            JSONArray list = JSONUtil.parseArray(stringRedisTemplate.opsForValue().get("list"));
            return Result.ok(list);
        }
    }
}
