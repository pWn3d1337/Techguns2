package techguns.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class EntityUtil {

	/**
	 * Works with negative values, based on 1.13 code, does not remove score
	 * @param ply
	 * @param xp
	 */
	public static void playerAddExperiencePoints(EntityPlayer ply, int xp) {
		  //ply.addScore(xp);
	      ply.experience += (float)xp / (float)ply.xpBarCap();
	      ply.experienceTotal = MathHelper.clamp(ply.experienceTotal + xp, 0, Integer.MAX_VALUE);

	      while(ply.experience < 0.0F) {
	         float f = ply.experience * (float)ply.xpBarCap();
	         if (ply.experienceLevel > 0) {
	            ply.addExperienceLevel(-1);
	            ply.experience = 1.0F + f / (float)ply.xpBarCap();
	         } else {
	            ply.addExperienceLevel(-1);
	            ply.experience = 0.0F;
	         }
	      }

	      while(ply.experience >= 1.0F) {
	         ply.experience = (ply.experience - 1.0F) * (float)ply.xpBarCap();
	         ply.addExperienceLevel(1);
	         ply.experience /= (float)ply.xpBarCap();
	      }

   }
}
