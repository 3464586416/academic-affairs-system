package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.CourseTextbook;
import com.city.springboot_big_schoolwork.entity.Textbook;
import com.city.springboot_big_schoolwork.entity.TextbookDistribute;
import com.city.springboot_big_schoolwork.service.CourseTextbookService;
import com.city.springboot_big_schoolwork.service.TextbookDistributeService;
import com.city.springboot_big_schoolwork.service.TextbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/textbook")
public class TextbookController {

    @Autowired
    private TextbookService textbookService;

    @Autowired
    private CourseTextbookService courseTextbookService;

    @Autowired
    private TextbookDistributeService textbookDistributeService;

    @GetMapping
    public String index() {
        return "textbook/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Textbook>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title) {
        LambdaQueryWrapper<Textbook> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(isbn != null, Textbook::getIsbn, isbn)
                .like(title != null, Textbook::getTitle, title)
                .eq(Textbook::getStatus, 1)
                .orderByAsc(Textbook::getTitle);
        Page<Textbook> p = textbookService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @GetMapping("/all")
    @ResponseBody
    public Result<java.util.List<Textbook>> all() {
        return Result.success(textbookService.list(new LambdaQueryWrapper<Textbook>().eq(Textbook::getStatus, 1)));
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存教材信息")
    public Result<?> save(@RequestBody Textbook textbook) {
        textbook.setStatus(1);
        textbookService.saveOrUpdate(textbook);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除教材")
    public Result<?> delete(@PathVariable Long id) {
        Textbook textbook = textbookService.getById(id);
        if (textbook != null) {
            textbook.setStatus(0);
            textbookService.updateById(textbook);
        }
        return Result.success();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result<Textbook> get(@PathVariable Long id) {
        return Result.success(textbookService.getById(id));
    }

    // 教材征订管理
    @GetMapping("/order")
    public String orderIndex() {
        return "textbook/index";
    }

    @GetMapping("/order/list")
    @ResponseBody
    public Result<PageResult<CourseTextbook>> orderList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Page<CourseTextbook> p = courseTextbookService.page(new Page<>(page, size),
                new LambdaQueryWrapper<CourseTextbook>().orderByDesc(CourseTextbook::getCreatedAt));
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/order/save")
    @ResponseBody
    public Result<?> orderSave(@RequestBody CourseTextbook courseTextbook) {
        courseTextbookService.saveOrUpdate(courseTextbook);
        return Result.success();
    }

    // 教材发放管理
    @GetMapping("/distribute")
    public String distributeIndex() {
        return "textbook/index";
    }

    @GetMapping("/distribute/list")
    @ResponseBody
    public Result<PageResult<TextbookDistribute>> distributeList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Page<TextbookDistribute> p = textbookDistributeService.page(new Page<>(page, size),
                new LambdaQueryWrapper<TextbookDistribute>().orderByDesc(TextbookDistribute::getDistributeDate));
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/distribute/save")
    @ResponseBody
    public Result<?> distributeSave(@RequestBody TextbookDistribute distribute) {
        // 计算总金额
        if (distribute.getUnitPrice() != null && distribute.getQuantity() != null) {
            distribute.setTotalAmount(distribute.getUnitPrice().multiply(new java.math.BigDecimal(distribute.getQuantity())));
        }
        textbookDistributeService.save(distribute);

        // 减少库存
        Textbook textbook = textbookService.getById(distribute.getTextbookId());
        if (textbook != null && textbook.getStock() != null) {
            textbook.setStock(Math.max(0, textbook.getStock() - distribute.getQuantity()));
            textbookService.updateById(textbook);
        }
        return Result.success();
    }
}
