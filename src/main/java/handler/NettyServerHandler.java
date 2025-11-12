package handler;

import com.alibaba.fastjson2.JSONObject;
import dto.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.bufferUtils;

public class NettyServerHandler extends ChannelHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    // 使用共享状态处理器
    private final SharedStateHandler sharedState = SharedStateHandler.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // 消息处理
            ByteBuf byteBuf = (ByteBuf) msg;
            String message = byteBuf.toString(CharsetUtil.UTF_8);

            Packet packet;
            try {
                packet = JSONObject.parseObject(message, Packet.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int cmd = packet.getCmd();

            switch (cmd) {
                case 0x10: // 绑定
                    sharedState.bind(packet.getFrom(), packet.getTo());
                    break;
                case 0x20: // 解绑
                    sharedState.unbind(packet.getFrom());
                    break;
                case 0x30: // 发送
                    // 目标服务端通道
                    Channel toChannel = sharedState.getChannel(sharedState.getBindTarget(packet.getFrom()));
                    toChannel.writeAndFlush(bufferUtils.copyObjectToBuffer(packet));
                    break;
                case 0x40: // 心跳检测
                    if (!sharedState.isOnline(packet.getFrom())) {
                        // 初次心跳
                        sharedState.addToOnline(packet.getFrom(), ctx.channel());
                    }
                    logger.info("服务端已检测到心跳: {}", message);
                    // 检查绑定关系
                    if (sharedState.getBindTarget(packet.getFrom()) == null) {
                        // 未绑定
                        ctx.writeAndFlush(bufferUtils.copyObjectToBuffer(new Packet(0x40, "unbind")));
                    } else if(sharedState.getChannel(sharedState.getBindTarget(packet.getFrom())) == null) {
                        // 目标通道不在线
                        ctx.writeAndFlush(bufferUtils.copyObjectToBuffer(new Packet(0x40, "bind offline")));
                    } else {
                        // 目标通道在线
                        ctx.writeAndFlush(bufferUtils.copyObjectToBuffer(new Packet(0x40, "bind online")));
                    }
                    break;
            }
        } finally {
            // 确保释放ByteBuf资源
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        // 清理 sharedState
        sharedState.removeChannel(ctx.channel());
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