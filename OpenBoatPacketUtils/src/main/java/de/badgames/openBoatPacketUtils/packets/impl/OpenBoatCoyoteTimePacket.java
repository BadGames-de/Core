package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatCoyoteTimePacket extends OpenBoatPacketBase {

    int time;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(19);
        stream.writeInt(time);
    }
}
