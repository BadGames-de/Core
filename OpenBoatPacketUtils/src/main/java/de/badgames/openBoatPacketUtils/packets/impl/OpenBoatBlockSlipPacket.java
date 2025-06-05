package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import de.badgames.openBoatPacketUtils.util.PacketHelper;
import lombok.AllArgsConstructor;
import org.bukkit.Material;

import java.util.List;

/**
 * Sets the slipperiness value that is used the specified block(s).
 * <a href="https://github.com/o7Moon/OpenBoatUtils/wiki/Packets#set-blocks-slipperiness">Specification.</a>
 */
@AllArgsConstructor
public class OpenBoatBlockSlipPacket extends OpenBoatPacketBase {

    float slipperiness;
    List<Material> materials;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(3);
        stream.writeFloat(slipperiness);
        List<String> allMaterialNames = materials.stream().filter(Material::isBlock).map(x -> String.valueOf(x.getId())).toList();
        PacketHelper.writeString(stream, String.join(",", allMaterialNames));
    }
}
