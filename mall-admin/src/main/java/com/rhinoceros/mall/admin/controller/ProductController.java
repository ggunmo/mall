package com.rhinoceros.mall.admin.controller;

import com.rhinoceros.mall.core.po.Product;
import com.rhinoceros.mall.core.query.PageQuery;
import com.rhinoceros.mall.core.vo.InputStreamWithFileName;
import com.rhinoceros.mall.core.vo.ProductsWithCountVo;
import com.rhinoceros.mall.manager.impl.exception.FileUplodException;
import com.rhinoceros.mall.service.service.ProductService;
import com.rhinoceros.mall.web.support.web.annotation.PageDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 创建商品控制器
 */
@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    //定义要调用的逻辑业务对象
    @Autowired
    private ProductService productService;

    /**
     * 定义方法找到商品并转换为json格式返回
     * ?page=1&size=10&sort=saleNum,DESC/ASC&sort=price,DESC
     *
     * @param pageQuery
     * @param categoryId
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ProductsWithCountVo productList(@PageDefault(required = false) PageQuery pageQuery, @RequestParam(value = "categoryId") Long categoryId) {
        List<Product> productList = productService.findDeepByCategoryId(categoryId, pageQuery);
        Long count = productService.countDeepByCategoryId(categoryId);
        ProductsWithCountVo productsWithCountVo = new ProductsWithCountVo();
        productsWithCountVo.setProducts(productList);
        productsWithCountVo.setCount(count);
        return productsWithCountVo;
    }

    /**
     * 显示商品
     *
     * @return
     */
    @RequestMapping
    public String showProduct() {
        return "include/product";
    }

//    @RequestMapping("/edict")
//    public Product updateSelectionProduct() {
//        return ProductManager.updateSelectionById();
//    }

    /**
     * 删除商品
     *
     * @param product
     */
    @ResponseBody
    @RequestMapping("/delete")
    public void delete(Product product) {
        productService.deleteById(product.getId());
    }

    /**
     * 修改商品
     *
     * @param product
     */
    @ResponseBody
    @RequestMapping(value = "/update",produces = "text/plain;charset=UTF-8")
    public String update(@RequestPart("files") MultipartFile[] multipartFiles, Product product) {
        List<InputStreamWithFileName> list = new LinkedList<>();
        uploadFile(multipartFiles, list);
        try {
            productService.updateSelectionById(product, list);
        }catch (FileUplodException e){
            e.printStackTrace();
            return "图片格式不正确";
        }
        return "修改成功";
    }

    /**
     * 增加商品
     *
     * @param product
     */
    @ResponseBody
    @RequestMapping(value = "/add", produces = "text/plain;charset=UTF-8")
    public String add(@RequestPart("files") MultipartFile[] multipartFiles, Product product) {
        List<InputStreamWithFileName> list = new LinkedList<>();
        uploadFile(multipartFiles, list);
        if (product.getName() == null || product.getName().trim().equals("")) {
            return "商品名不能为空！";
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return "商品价格不能为空！";
        }
        if (product.getDiscount() == null || product.getDiscount().compareTo(BigDecimal.ZERO) <= 0) {
            return "商品优惠价格不能为空！";
        }
        if (product.getStoreNum() == null || product.getStoreNum() <= 0) {
            return "商品库存不能为空！";
        }
        if (product.getCategoryId() == null) {
            return "商品分类不能为空！";
        }
        try {
            productService.addSelectionById(product, list);
        }catch (FileUplodException e){
            e.printStackTrace();
            return "图片格式不正确";
        }
        return "新增成功！";
    }

    private void uploadFile(@RequestPart("files") MultipartFile[] multipartFiles, List<InputStreamWithFileName> list) {

        try {
            for (MultipartFile file : multipartFiles) {
                InputStreamWithFileName inputStreamWithFileName = new InputStreamWithFileName();
                String fileName = file.getOriginalFilename();
                InputStream is = file.getInputStream();
                inputStreamWithFileName.setFileName(fileName);
                inputStreamWithFileName.setIs(is);
                list.add(inputStreamWithFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}