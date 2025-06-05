package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatWaterJumpingPacket extends OpenBoatPacketBase {

    boolean waterJumping;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(20);
        stream.writeBoolean(waterJumping);
    }
}
