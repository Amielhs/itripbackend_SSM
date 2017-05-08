package cn.itrip.service.itripUser;
import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItripUserServiceImpl implements ItripUserService {

    @Resource
    private ItripUserMapper itripUserMapper;

    public ItripUser getItripUserById(Long id)throws Exception{
        return itripUserMapper.getItripUserById(id);
    }

    public List<ItripUser>	getItripUserListByMap(Map<String,Object> param)throws Exception{
        return itripUserMapper.getItripUserListByMap(param);
    }

    public Integer getItripUserCountByMap(Map<String,Object> param)throws Exception{
        return itripUserMapper.getItripUserCountByMap(param);
    }

    public Integer itriptxAddItripUser(ItripUser itripUser)throws Exception{
            itripUser.setCreationDate(new Date());
            return itripUserMapper.insertItripUser(itripUser);
    }

    public Integer itriptxModifyItripUser(ItripUser itripUser)throws Exception{
        itripUser.setModifyDate(new Date());
        return itripUserMapper.updateItripUser(itripUser);
    }

    public Integer itriptxDeleteItripUserById(Long id)throws Exception{
        return itripUserMapper.deleteItripUserById(id);
    }

}
