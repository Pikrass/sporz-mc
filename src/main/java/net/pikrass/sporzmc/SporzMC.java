package net.pikrass.sporzmc;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;

@Mod(
	modid   = SporzMC.MODID,
	name    = SporzMC.MODNAME,
	version = SporzMC.MODVER,
	serverSideOnly = true,
	acceptableRemoteVersions = "*"
)

public class SporzMC
{
	public static final String MODID = "Sporz MC";
	public static final String MODNAME = "Sporz MC";
	public static final String MODVER = "0.0";

	@Instance(SporzMC.MODID)
	public static SporzMC instance;
}
