package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

/**
 * If true, the boat will move upward in water streams until it reaches the top.
 * <a href="https://github.com/o7Moon/OpenBoatUtils/wiki/Packets#set-boat-water-elevation">Specification.</a>
 */
@AllArgsConstructor
public class OpenBoatWaterElevationPacket extends OpenBoatPacketBase {

    boolean waterElevation;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(5);
        stream.writeBoolean(waterElevation);
    }
}
