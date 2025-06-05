package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatYawAccelPacket extends OpenBoatPacketBase {

    float yamAcceleration;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(10);
        stream.writeFloat(yamAcceleration);
    }
}
