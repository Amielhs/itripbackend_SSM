package cn.itrip.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 酒店信息Controller
 *
 * 包括API接口：
 * 1、根据酒店id查询酒店拓展属性
 * 2、根据酒店id查询酒店介绍，酒店政策，酒店设施
 * 3、根据酒店id查询酒店特色属性列表
 * 4、根据type 和target id 查询酒店图片
 * 5、查询热门城市
 * 6、查询热门商圈列表（搜索-酒店列表）
 * 7、查询数据字典特色父级节点列表（搜索-酒店列表）
 *
 * Created by hanlu on 2017/5/9.
 */

@Controller
//@Api(value = "API", basePath = "/http://api.itrap.com/api")
@RequestMapping(value="/api/hotel")
public class HotelController {
    private Logger logger = Logger.getLogger(HotelController.class);
}
