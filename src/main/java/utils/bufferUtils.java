package utils;

import com.alibaba.fastjson2.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class bufferUtils {

    public static ByteBuf copyObjectToBuffer(Object object) {
        String jsonString = JSONObject.toJSONString(object);
        return Unpooled.copiedBuffer(jsonString, CharsetUtil.UTF_8);
    }
}
