package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

/**
 * Sets the slipperiness value that is used for any blocks that don't have a specified slipperiness value.
 * <a href="https://github.com/o7Moon/OpenBoatUtils/wiki/Packets#set-default-slipperiness">Specification.</a>
 */
@AllArgsConstructor
public class OpenBoatDefaultSlipPacket extends OpenBoatPacketBase {

    float defaultSlipperiness;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(2);
        stream.writeFloat(defaultSlipperiness);
    }
}
