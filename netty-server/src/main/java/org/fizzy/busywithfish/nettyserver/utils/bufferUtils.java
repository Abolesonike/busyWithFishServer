package org.fizzy.busywithfish.nettyserver.utils;

import com.alibaba.fastjson2.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class bufferUtils {

    public static ByteBuf copyObjectToBuffer(Object object) {
        String jsonString = JSONObject.toJSONString(object);
        // 添加换行符以符合帧分隔符的要求
        return Unpooled.copiedBuffer(jsonString + "\n", CharsetUtil.UTF_8);
    }
}