package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatGravityPacket extends OpenBoatPacketBase {

    double gravityForce;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(9);
        stream.writeDouble(gravityForce);
    }
}
