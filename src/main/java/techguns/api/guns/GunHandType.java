package techguns.api.guns;

import com.mojang.realmsclient.gui.ChatFormatting;

import techguns.util.TextUtil;

public enum GunHandType {
	TWO_HANDED,
	ONE_HANDED,
	ONE_POINT_FIVE_HANDED;

	@Override
	public String toString() {
		String s="";
		switch(this) {
		case ONE_HANDED:
			s+=ChatFormatting.GREEN;
			break;
		case ONE_POINT_FIVE_HANDED:
			s+=ChatFormatting.YELLOW;
			break;
		case TWO_HANDED:
			s+=ChatFormatting.GOLD;
			break;
		default:
			break;
		}
		return s+TextUtil.trans("techguns.gunhandtype."+super.toString().toLowerCase());
	}
}
