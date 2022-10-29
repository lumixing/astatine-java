package dev.lumix.astatine.world.block;

public enum BlockType {
    AIR(0),
    GRASS(1),
    DIRT(2),
    STONE(3),
    ORE(4);

    private static final BlockType[] blockTypeArray = values();
    private final int id;

    BlockType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BlockType getBlockType(int id) {
        for (BlockType type : blockTypeArray) {
            if (type.getId() == id) {
                return type;
            }
        }

        return AIR;
    }
}
