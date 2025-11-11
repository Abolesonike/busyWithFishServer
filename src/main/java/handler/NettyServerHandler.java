package handler;

import com.alibaba.fastjson2.JSONObject;
import dto.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.bufferUtils;

import java.util.concurrent.ConcurrentHashMap;

public class NettyServerHandler extends ChannelHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    // 在线的客户端
    private final ConcurrentHashMap<String, Channel> online = new ConcurrentHashMap<>();

    // 相互绑定的两个客户端
    private final ConcurrentHashMap<String, String> bindMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 消息处理
        ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(CharsetUtil.UTF_8);

        Packet packet;
        try {
            packet = JSONObject.parseObject(message, Packet.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String cmd = packet.getCmd();

        switch (cmd) {
            case "bind": // 绑定
                bindMap.put(packet.getFrom(), packet.getTo());
                break;
            case "unbind": // 解绑
                bindMap.remove(packet.getFrom());
                break;
            case "send": // 发送
                // 目标服务端通道
                Channel toChannel = online.get(bindMap.get(packet.getFrom()));
                toChannel.writeAndFlush(packet);
                break;
            case "heartBeat": // 心跳检测
                if (!online.containsKey(packet.getFrom())) {
                    // 初次心跳
                    online.put(packet.getFrom(), ctx.channel());
                }
                logger.info("服务端已检测到心跳: {}", message);
                // 通知客户端，服务端已检测到心跳
                ctx.writeAndFlush(bufferUtils.copyObjectToBuffer(
                        new Packet("heartBeat", packet.getSeq() + 1, "server has received this heart beat")));
                break;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        // 清理 online + bindMap
        online.entrySet().removeIf(e->e.getValue()==ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //发送消息给客户端
        // ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        ctx.close();
    }
}
