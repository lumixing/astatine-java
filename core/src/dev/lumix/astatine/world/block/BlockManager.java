package dev.lumix.astatine.world.block;

import dev.lumix.astatine.engine.drawables.ResourceManager;

import java.util.HashMap;

public class BlockManager {
    private static final HashMap<BlockType, Block> blocks = new HashMap<BlockType, Block>();
    private static final BlockType[] blockTypeArray = BlockType.values();

    static {
        blocks.put(BlockType.AIR,   new Block(BlockType.AIR,    null,                                               false));
        blocks.put(BlockType.GRASS, new Block(BlockType.GRASS,  ResourceManager.getInstance().getDrawable("grass"), true));
        blocks.put(BlockType.DIRT,  new Block(BlockType.DIRT,   ResourceManager.getInstance().getDrawable("dirt"),  true));
        blocks.put(BlockType.STONE, new Block(BlockType.STONE,  ResourceManager.getInstance().getDrawable("stone"), true));
        blocks.put(BlockType.ORE,   new Block(BlockType.ORE,    ResourceManager.getInstance().getDrawable("ore"),   true));
    }

    public static Block getBlock(BlockType type) {
        return blocks.get(type);
    }

    public static BlockType getBlockType(int id) {
        for (BlockType type : blockTypeArray) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
