package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatAllowAccelPacket extends OpenBoatPacketBase {

    boolean allowAcceleration;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(14);
        stream.writeBoolean(allowAcceleration);
    }
}
