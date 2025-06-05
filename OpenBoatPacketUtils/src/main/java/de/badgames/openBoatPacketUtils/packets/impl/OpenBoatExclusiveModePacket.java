package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.constants.Modes;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatExclusiveModePacket extends OpenBoatPacketBase {

    Modes modeId;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(18);
        stream.writeShort(modeId.getId());
    }
}
