package com.barry.service.impl;

import com.barry.enums.Sex;
import com.barry.mapper.UsersMapper;
import com.barry.pojo.Users;
import com.barry.pojo.bo.UserBo;
import com.barry.service.UserService;
import com.barry.utils.DateUtil;
import com.barry.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;


    public static final String  USERFACE = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&hs=2&pn=0&spn=0&di=7590&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=3363295869%2C2467511306&os=892371676%2C71334739&simid=4203536407%2C592943110&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=%E5%9B%BE%E7%89%87&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fa0.att.hudong.com%2F30%2F29%2F01300000201438121627296084016.jpg%26refer%3Dhttp%3A%2F%2Fa0.att.hudong.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1617876881%26t%3Dbc8eb82d38c2193ad3af45e955c74962&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bfhyvg8_z%26e3Bv54AzdH3F4AzdH3Fetjo_z%26e3Brir%3Fwt1%3Dmb9l9&gsm=1&islist=&querylist=";

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean queryUsernameIsExist(String name) {
        Example  userExample = new Example(Users.class);
        Example.Criteria  userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username",name);
        Users result = usersMapper.selectOneByExample(userExample);
        return result == null ? false : true;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users CreateUser(UserBo userBo) {
        String userId = sid.nextShort();
        Users user =new Users();
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setNickname(userBo.getUsername());
        user.setFace(USERFACE);
        user.setBirthday(DateUtil.stringToDate("1988-01-23"));
        user.setSex(Sex.secret.Type);
        user.setId(userId);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);
        return user;
    }
}
