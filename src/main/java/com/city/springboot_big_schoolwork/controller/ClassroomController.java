package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.Classroom;
import com.city.springboot_big_schoolwork.entity.ClassroomBorrow;
import com.city.springboot_big_schoolwork.service.ClassroomBorrowService;
import com.city.springboot_big_schoolwork.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ClassroomBorrowService classroomBorrowService;

    @GetMapping
    public String index() {
        return "classroom/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Classroom>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String roomNo,
            @RequestParam(required = false) String building,
            @RequestParam(required = false) String status) {
        LambdaQueryWrapper<Classroom> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(roomNo != null, Classroom::getRoomNo, roomNo)
                .like(building != null, Classroom::getBuilding, building)
                .eq(status != null, Classroom::getStatus, status)
                .orderByAsc(Classroom::getRoomNo);
        Page<Classroom> p = classroomService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @GetMapping("/all")
    @ResponseBody
    public Result<java.util.List<Classroom>> all() {
        return Result.success(classroomService.list());
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存教室信息")
    public Result<?> save(@RequestBody Classroom classroom) {
        classroomService.saveOrUpdate(classroom);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除教室信息")
    public Result<?> delete(@PathVariable Long id) {
        classroomService.removeById(id);
        return Result.success();
    }

    // 教室借用管理
    @GetMapping("/borrow")
    public String borrowIndex() {
        return "classroom/index";
    }

    @GetMapping("/borrow/list")
    @ResponseBody
    public Result<PageResult<ClassroomBorrow>> borrowList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Page<ClassroomBorrow> p = classroomBorrowService.page(new Page<>(page, size),
                new LambdaQueryWrapper<ClassroomBorrow>().orderByDesc(ClassroomBorrow::getCreatedAt));
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/borrow/save")
    @ResponseBody
    @OperationLog("提交教室借用申请")
    public Result<?> borrowSave(@RequestBody ClassroomBorrow borrow) {
        borrow.setApproveStatus("待审批");
        borrow.setStatus(1);
        classroomBorrowService.saveOrUpdate(borrow);
        return Result.success();
    }

    @PostMapping("/borrow/approve/{id}")
    @ResponseBody
    @OperationLog("审批教室借用")
    public Result<?> borrowApprove(@PathVariable Long id, @RequestParam String status,
                                   @RequestParam(required = false) String comment) {
        ClassroomBorrow borrow = classroomBorrowService.getById(id);
        if (borrow != null) {
            borrow.setApproveStatus(status);
            borrow.setApproveComment(comment);
            classroomBorrowService.updateById(borrow);
        }
        return Result.success();
    }
}
