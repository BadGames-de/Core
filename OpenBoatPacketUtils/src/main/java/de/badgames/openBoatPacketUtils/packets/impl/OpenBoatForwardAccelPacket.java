package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatForwardAccelPacket extends OpenBoatPacketBase {

    float forwardAcceleration;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(11);
        stream.writeFloat(forwardAcceleration);
    }
}
