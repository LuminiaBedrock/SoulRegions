package me.nazarxexe.region.region;

public enum Flags {


    BREAKABLE(0x001),
    BUILDABLE(0x002),
    CONTAINER_OPENABLE(0x003),
    EXPLODEABLE(0x004),
    PVPABLE(0x005);



    int id;

    public int getId() {
        return this.id;
    }

    Flags(int id) {
        this.id = id;
    }

}
