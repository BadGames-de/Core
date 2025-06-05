package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatTurningAccelPacket extends OpenBoatPacketBase {

    float turningAcceleration;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(13);
        stream.writeFloat(turningAcceleration);
    }
}
