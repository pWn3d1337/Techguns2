package techguns.keybind;

/**
 * contains the keybind id constants, also needed server side
 *
 */
public class TGKeybindsID {
	
	public static byte TOGGLE_NIGHTVISION=0;
	public static byte TOGGLE_SAFEMODE=1;
	
	/**
	 * Uses Jump default keybind, need id for packet;
	 */
	public static byte JETPACK_BOOST_START = 2;
	/**
	 * Uses Jump default keybind, need id for packet;
	 */
	public static byte JETPACK_BOOST_STOP = 3;
	
	/**
	 * used for jetpack accelaration, needs id for packet
	 */
	public static byte JETPACK_FORWARD_START=4;
	
	/**
	 * used for jetpack accelaration, needs id for packet
	 */
	public static byte JETPACK_FORWARD_STOP=5;
	
	public static byte FORCE_RELOAD=6;
	
	public static byte TOGGLE_JETPACK=7;
	
	public static byte TOGGLE_STEP_ASSIST=8;
	
	public static byte TOGGLE_HUD=9;
}
