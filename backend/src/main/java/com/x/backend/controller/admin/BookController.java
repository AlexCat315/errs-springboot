package com.x.backend.controller.admin;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.pojo.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RoleSecurity(value = {"admin"})
@RestController("adminBookController")
@RequestMapping("/api/admin/book")
public class BookController {



    @RequestMapping("/insert/score")
    public ResultEntity<String> insertScore() {
        return ResultEntity.success("insertScore");
    }

    @RequestMapping("/test")
    public ResultEntity<String> test() {
        return ResultEntity.success("test");
    }
}
