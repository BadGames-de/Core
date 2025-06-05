package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.constants.Modes;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatApplyExclusiveModePacket extends OpenBoatPacketBase {

    Modes[] modeIds;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(25);
        stream.writeShort(modeIds.length);
        for (Modes mode : modeIds) {
            stream.writeShort(mode.getId());
        }
    }
}
