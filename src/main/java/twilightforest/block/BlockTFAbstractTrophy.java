package twilightforest.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import twilightforest.TFSounds;
import twilightforest.client.particle.TFParticleType;
import twilightforest.enums.BossVariant;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFTrophy;

import java.util.Random;

//[VanillaCopy] of AbstractSkullBlock except uses Variants instead of ISkullType and adds Sounds when clicked or powered
public abstract class BlockTFAbstractTrophy extends ContainerBlock {

	private final BossVariant variant;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	
	protected BlockTFAbstractTrophy(BossVariant variant, AbstractBlock.Properties builder) {
		super(builder);
		this.variant = variant;
		setDefaultState(stateContainer.getBaseState().with(POWERED, Boolean.valueOf(false)));
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if(!worldIn.isRemote) {
			boolean flag = worldIn.isBlockPowered(pos);
			if (flag != state.get(POWERED)) {
				if (flag) {
					this.playSound(worldIn, pos);
				}
				worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(flag)));
			}
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
		playSound(worldIn, pos);
		createParticle(worldIn, pos);
		return ActionResultType.SUCCESS;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
	      return new TileEntityTFTrophy();
	   }

	public BossVariant getVariant() {
		return this.variant;
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

	public void playSound(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (!world.isRemote && te instanceof TileEntityTFTrophy) {
			SoundEvent sound = null;
			float volume = 1.0F;
			float pitch = 1.0F;
			switch (variant) {
				case NAGA:
					sound = TFSounds.NAGA_RATTLE;
					volume = 1.25F;
					pitch = 1.2F;
					break;
				case LICH:
					sound = TFSounds.LICH_AMBIENT;
					volume = 0.35F;
					pitch = 1.1F;
					break;
				case HYDRA:
					sound = TFSounds.HYDRA_GROWL;
					pitch = 1.2F;
					break;
				case UR_GHAST:
					sound = TFSounds.URGHAST_AMBIENT;
					pitch = 0.8F;
					break;
				case SNOW_QUEEN:
					sound = TFSounds.SNOW_QUEEN_AMBIENT;
					break;
				case KNIGHT_PHANTOM:
					sound = TFSounds.PHANTOM_AMBIENT;
					pitch = 1.1F;
					break;
				case MINOSHROOM:
					sound = TFSounds.MINOSHROOM_AMBIENT;
					volume = 0.75F;
					pitch = 0.7F;
					break;
				case QUEST_RAM:
					sound = TFSounds.QUEST_RAM_AMBIENT;
					pitch = 0.7F;
					break;
				default:
					break;
			}
			if (sound != null) {
				world.playSound((PlayerEntity)null, pos, sound, SoundCategory.BLOCKS, volume, pitch);
			}
		}
	}

	public void createParticle(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTFTrophy) {
			Random rand = world.getRandom();
			switch (variant) {
				case NAGA:
					for (int daze = 0; daze < 10; daze++) {
						world.addParticle(ParticleTypes.CRIT,
								((double)pos.getX() + rand.nextFloat() * 0.5 * 2.0F),
								(double)pos.getY() + 0.25,
								((double)pos.getZ() + rand.nextFloat() * 0.5 * 2.0F),
								rand.nextGaussian() * 0.02D,
								rand.nextGaussian() * 0.02D,
								rand.nextGaussian() * 0.02D);
					}
					break;
				case LICH:
					for (int a = 0; a < 5; a++) {
						world.addParticle(ParticleTypes.ANGRY_VILLAGER,
								(double)pos.getX() + rand.nextFloat() * 0.5 * 2.0F,
								(double)pos.getY() + 0.5 + rand.nextFloat() * 0.25,
								(double)pos.getZ() + rand.nextFloat() * 0.5 * 2.0F,
								rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D);
					}
					break;
				case MINOSHROOM:
					for (int g = 0; g < 10; g++) {
						world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, world.getBlockState(pos.down())),
								(double)pos.getX() + rand.nextFloat() * 10F - 5F,
								(double)pos.getY() + 0.1F + rand.nextFloat() * 0.3F,
								(double)pos.getZ() + rand.nextFloat() * 10F - 5F,
								0, 0, 0);
					}
					break;
				case KNIGHT_PHANTOM:
					for (int brek = 0; brek < 10; brek++) {
						world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(TFItems.knightmetal_sword.get())),
								pos.getX() + 0.5 + (rand.nextFloat() - 0.5),
								pos.getY() + rand.nextFloat() + 0.5,
								pos.getZ() + 0.5 + (rand.nextFloat() - 0.5),
								0, .25, 0);

					}
					break;
				case UR_GHAST:
					for (int red = 0; red < 10; red++) {
						world.addParticle(RedstoneParticleData.REDSTONE_DUST,
								(double)pos.getX() + (rand.nextDouble() * 1) - 0.25,
								(double)pos.getY() + rand.nextDouble() * 0.5 + 0.5,
								(double)pos.getZ() + (rand.nextDouble() * 1),
								0, 0, 0);
					}
						break;
				case SNOW_QUEEN:
					for (int b = 0; b < 20; b++) {
						world.addParticle(TFParticleType.SNOW_WARNING.get(),
								(double)pos.getX() - 1 + (rand.nextDouble() * 3.25),
								(double)pos.getY() + 5,
								(double)pos.getZ() - 1 + (rand.nextDouble() * 3.25),
								0, 1, 0);
					}
					break;
				case QUEST_RAM:
					for (int p = 0; p < 10; p++) {
						world.addParticle(ParticleTypes.ENTITY_EFFECT,
								(double)pos.getX() + 0.5 + (rand.nextDouble() - 0.5),
								(double)pos.getY() + (rand.nextDouble() - 0.5),
								(double)pos.getZ() + 0.5 + (rand.nextDouble() - 0.5),
								rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
					}
					break;
				default:
					break;
			}
		}
	}
}
