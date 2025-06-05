package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.constants.Modes;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatSetModePacket extends OpenBoatPacketBase {

    Modes modeId;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(8);
        stream.writeShort(modeId.getId());
    }
}
