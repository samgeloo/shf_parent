package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;

import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.controller
 * @Description :
 * @date : 2022-06-10 15:54
 **/
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {
    @Reference
    private HouseImageService houseImageService;

    //上传图片的页面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String goUploadPage(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, Map map) {
        //将房源id和图片的类型放到request域中
        map.put("houseId", houseId);
        map.put("type", type);

        return "house/upload";
    }



    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type,
                         Map map, @RequestParam("file") MultipartFile[] files) {
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                try {
                    //获取字节数组
                    byte[] bytes = file.getBytes();
                    //获取图片名字
                    String originalFilename = file.getOriginalFilename();
                    //通过UUid随机生成一个字符串作为上传到七牛云的图片的名字
                    String fileName = UUID.randomUUID().toString()+originalFilename;
                    //通过QiniuUtil工具类上传图片到七牛云
                    QiniuUtil.upload2Qiniu(bytes, fileName);
                    //创建HouseImage对象
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(fileName);
                    //设置图片的路径，路径的格式：http://七牛云的域名/随机生成图片的名字
                    houseImage.setImageUrl("http://rd8s21plq.hn-bkt.clouddn.com/"+fileName);
                    //调用houseImageService中保存的方法
                    houseImageService.insert(houseImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Result.ok();
    }
    //上传房产图片


    //删除房源或房产的图片
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        //調用houseImageService中的方法
        houseImageService.delete(id);
        return "redirect:/house/" + houseId;
    }

}
