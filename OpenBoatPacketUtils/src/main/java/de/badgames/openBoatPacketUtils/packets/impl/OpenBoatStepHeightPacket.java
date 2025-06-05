package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

/**
 * Sets the player's boat step height, in blocks.
 * <a href="https://github.com/o7Moon/OpenBoatUtils/wiki/Packets#set-step-height">Specification.</a>
 */
@AllArgsConstructor
public class OpenBoatStepHeightPacket extends OpenBoatPacketBase {

    float stepHeight;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(1);
        stream.writeFloat(stepHeight);
    }
}
