package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatJumpForcePacket extends OpenBoatPacketBase {

    float jumpForce;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(7);
        stream.writeFloat(jumpForce);
    }
}
