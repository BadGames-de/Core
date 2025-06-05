package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatTenStepInterpolationPacket extends OpenBoatPacketBase {

    boolean changeStepInterpolation;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(29);
        stream.writeBoolean(changeStepInterpolation);
    }
}
