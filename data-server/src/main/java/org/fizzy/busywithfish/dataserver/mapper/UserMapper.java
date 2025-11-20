package org.fizzy.busywithfish.dataserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.fizzy.busywithfish.dataserver.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
}