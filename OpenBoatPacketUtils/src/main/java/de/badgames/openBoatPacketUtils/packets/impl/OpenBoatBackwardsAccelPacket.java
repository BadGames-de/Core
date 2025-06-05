package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatBackwardsAccelPacket extends OpenBoatPacketBase {

    float backwardsAcceleration;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(12);
        stream.writeFloat(backwardsAcceleration);
    }
}
