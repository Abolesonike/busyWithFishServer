package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Packet {
    private int cmd;     // 命令字 bind，unbind，send，heart beat，...
    private int seq;      // 流水号
    private String body;  // JSON 字符串
    private String from;
    private String to;

    public Packet(int cmd, String body) {
        this.cmd = cmd;
        this.body = body;
    }

    public Packet(int cmd, int seq, String body) {
        this.cmd = cmd;
        this.seq = seq;
        this.body = body;
    }
}
