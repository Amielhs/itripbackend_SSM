package cn.itrip.controller;
import cn.itrip.beans.dtos.Dto;
import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.beans.vo.ItripAreaDicVO;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.beans.vo.hotel.*;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.service.itripAreaDic.ItripAreaDicService;
import cn.itrip.service.itripHotel.ItripHotelService;
import cn.itrip.service.itripLabelDic.ItripLabelDicService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 注：错误码（100201 ——100300）
 *
 * Created by hanlu on 2017/5/9.
 */

@Controller
//@Api(value = "API", basePath = "/http://api.itrap.com/api")
@RequestMapping(value = "/api/hotel")
public class HotelController {

    private Logger logger = Logger.getLogger(HotelController.class);

    @Resource
    private ItripAreaDicService itripAreaDicService;

    @Resource
    private ItripLabelDicService itripLabelDicService;

    @Resource
    private ItripHotelService itripHotelService;

    /****
     * 查询热门城市的接口
     *
     * @param type
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询热门城市", httpMethod = "POST",
            protocols = "HTTP",produces = "application/json",
            response = Dto.class,notes = "查询国内、国外的热门城市(1:国内 2:国外)"+
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>"+
            "<p>10201 : hotelId不能为空 </p>"+
            "<p>10202 : 系统异常,获取失败</p>")
    @RequestMapping(value = "/queryhotcity/{type}/", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<ItripAreaDic> queryHotCity(@PathVariable Integer type) {
        List<ItripAreaDic> itripAreaDics = null;
        List<ItripAreaDicVO> itripAreaDicVOs = null;
        try {
            if(EmptyUtils.isNotEmpty(type)){
                Map param = new HashMap();
                param.put("isHot", 1);
                param.put("isChina", type);
                itripAreaDics = itripAreaDicService.getItripAreaDicListByMap(param);
                if(EmptyUtils.isNotEmpty(itripAreaDics)){
                    itripAreaDicVOs=new ArrayList();
                    for (ItripAreaDic dic:itripAreaDics){
                        ItripAreaDicVO vo=new ItripAreaDicVO();
                        BeanUtils.copyProperties(dic, vo);
                        itripAreaDicVOs.add(vo);
                    }
                }

            }else{
                DtoUtil.returnFail("type不能为空","10201");
            }
        } catch (Exception e) {
            DtoUtil.returnFail("系统异常","10202");
            e.printStackTrace();
        }
        return DtoUtil.returnDataSuccess(itripAreaDicVOs);
    }

    /***
     * 查询商圈的接口
     *
     * @param cityId 根据城市查询商圈
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询商圈", httpMethod = "POST",
            protocols = "HTTP",produces = "application/json",
            response = Dto.class,notes = "根据城市查询商圈"+
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>"+
            "<p>10203 : cityId不能为空 </p>"+
            "<p>10204 : 系统异常,获取失败</p>")
    @RequestMapping(value = "/querytradearea/{cityId}", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<ItripAreaDic> queryTradeArea(@PathVariable Long cityId) {
        List<ItripAreaDic> itripAreaDics = null;
        List<ItripAreaDicVO> itripAreaDicVOs = null;
        try {
            if(EmptyUtils.isNotEmpty(cityId)){
                Map param = new HashMap();
                param.put("isTradingArea", 1);
                param.put("parent", cityId);
                itripAreaDics = itripAreaDicService.getItripAreaDicListByMap(param);
                if(EmptyUtils.isNotEmpty(itripAreaDics)){
                    itripAreaDicVOs=new ArrayList();
                    for (ItripAreaDic dic:itripAreaDics){
                        ItripAreaDicVO vo=new ItripAreaDicVO();
                        BeanUtils.copyProperties(dic, vo);
                        itripAreaDicVOs.add(vo);
                    }
                }

            }else{
                DtoUtil.returnFail("cityId不能为空","10203");
            }
        } catch (Exception e) {
            DtoUtil.returnFail("系统异常","10204");
            e.printStackTrace();
        }
        return DtoUtil.returnDataSuccess(itripAreaDics);
    }
    /***
     * 查询酒店特色列表
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询酒店特色列表", httpMethod = "POST",
            protocols = "HTTP",produces = "application/json",
            response = Dto.class,notes = "获取酒店特色(用于查询页列表)"+
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>"+
            "<p>10205: 系统异常,获取失败</p>")
    @RequestMapping(value = "/queryhotelfeature", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<ItripLabelDic> queryHotelFeature() {
        List<ItripLabelDic> itripLabelDics = null;
        List<ItripLabelDicVO> itripAreaDicVOs = null;
        try {
            Map param = new HashMap();
            param.put("parentId", 16);
            itripLabelDics = itripLabelDicService.getItripLabelDicListByMap(param);
            if(EmptyUtils.isNotEmpty(itripLabelDics)){
                itripAreaDicVOs=new ArrayList();
                for (ItripLabelDic dic:itripLabelDics){
                    ItripLabelDicVO vo=new ItripLabelDicVO();
                    BeanUtils.copyProperties(dic, vo);
                    itripAreaDicVOs.add(vo);
                }
            }

        } catch (Exception e) {
            DtoUtil.returnFail("系统异常","10205");
            e.printStackTrace();
        }
        return DtoUtil.returnDataSuccess(itripLabelDics);
    }

    /***
     * 根据酒店id查询酒店设施 -add by donghai
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "根据酒店id查询酒店设施", httpMethod = "POST",
            protocols = "HTTP",produces = "application/json",
            response = Dto.class,notes = "根据酒店id查询酒店设施"+
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>10206: 酒店id不能为空</p>"+
            "<p>10207: 系统异常,获取失败</p>")
    @RequestMapping(value = "/queryhotelfacilities/{id}", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<ItripSearchFacilitiesHotelVO> queryHotelFacilities(
            @ApiParam(required = true, name = "id", value = "酒店ID")
            @PathVariable Long id) {
        ItripSearchFacilitiesHotelVO itripSearchFacilitiesHotelVO = null;
        try {
            if(EmptyUtils.isNotEmpty(id)){
                itripSearchFacilitiesHotelVO = itripHotelService.getItripHotelFacilitiesById(id);
                return DtoUtil.returnDataSuccess(itripSearchFacilitiesHotelVO.getFacilities());
            }else{
                return DtoUtil.returnFail("酒店id不能为空","10206");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败","10207");
        }
    }

    /***
     * 根据酒店id查询酒店政策 -add by donghai
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "根据酒店id查询酒店政策", httpMethod = "POST",
            protocols = "HTTP",produces = "application/json",
            response = Dto.class,notes = "根据酒店id查询酒店政策"+
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>10208: 酒店id不能为空</p>"+
            "<p>10209: 系统异常,获取失败</p>")
    @RequestMapping(value = "/queryhotelpolicy/{id}", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<ItripSearchFacilitiesHotelVO> queryHotelPolicy(
            @ApiParam(required = true, name = "id", value = "酒店ID")
            @PathVariable Long id) {
        ItripSearchPolicyHotelVO itripSearchPolicyHotelVO = null;
        try {
            if(EmptyUtils.isNotEmpty(id)){
                itripSearchPolicyHotelVO = itripHotelService.queryHotelPolicy(id);
                return DtoUtil.returnDataSuccess(itripSearchPolicyHotelVO.getHotelPolicy());
            }else{
                return DtoUtil.returnFail("酒店id不能为空","10208");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败","10209");
        }
    }

    /***
     * 根据酒店id查询酒店政策 -add by donghai
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "根据酒店id查询酒店特色和介绍", httpMethod = "POST",
            protocols = "HTTP",produces = "application/json",
            response = Dto.class,notes = "根据酒店id查询酒店特色和介绍"+
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>10210: 酒店id不能为空</p>"+
            "<p>10211: 系统异常,获取失败</p>")
    @RequestMapping(value = "/queryhoteldetails/{id}", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<ItripSearchFacilitiesHotelVO> queryHotelDetails(
            @ApiParam(required = true, name = "id", value = "酒店ID")
            @PathVariable Long id) {
        List<ItripSearchDetailsHotelVO> itripSearchDetailsHotelVOList = null;
        try {
            if(EmptyUtils.isNotEmpty(id)){
                itripSearchDetailsHotelVOList = itripHotelService.queryHotelDetails(id);
                return DtoUtil.returnDataSuccess(itripSearchDetailsHotelVOList);
            }else{
                return DtoUtil.returnFail("酒店id不能为空","10210");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败","10211");
        }
    }
}
