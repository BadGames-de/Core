package de.badgames.openBoatPacketUtils.constants;

import lombok.Getter;

@Getter
public enum Modes {

    BROKEN_SLIME_RALLY((short)0),
    BROKEN_SLIME_RALLY_BLUE((short)1),
    BROKEN_SLIME_BA_NOFD((short) 2),
    BROKEN_SLIME_PARKOUR((short) 3),
    BROKEN_SLIME_BA_BLUE_NOFD((short) 4),
    BROKEN_SLIME_PARKOUR_BLUE((short) 5),
    BROKEN_SLIME_BA((short)6),
    BROKEN_SLIME_BA_BLUE((short)7),
    RALLY((short) 8),
    RALLY_BLUE((short) 9),
    BA_NOFD((short) 10),
    PARKOUR((short) 11),
    BA_BLUE_NOFD((short) 12),
    PARKOUR_BLUE((short) 13),
    BA((short)14),
    BA_BLUE((short)15);

    private short Id;

    Modes(short Id) {
        this.Id = Id;
    }
}
