package techguns.keybind;

/**
 * contains the keybind id constants, also needed server side
 *
 */
public class TGKeybindsID {
	
	public static final byte TOGGLE_NIGHTVISION=0;
	public static final byte TOGGLE_SAFEMODE=1;
	
	/**
	 * Uses Jump default keybind, need id for packet;
	 */
	public static final byte JETPACK_BOOST_START = 2;
	/**
	 * Uses Jump default keybind, need id for packet;
	 */
	public static final byte JETPACK_BOOST_STOP = 3;
	
	/**
	 * used for jetpack accelaration, needs id for packet
	 */
	public static final byte JETPACK_FORWARD_START=4;
	
	/**
	 * used for jetpack accelaration, needs id for packet
	 */
	public static final byte JETPACK_FORWARD_STOP=5;
	
	public static final byte FORCE_RELOAD=6;
	
	public static final byte TOGGLE_JETPACK=7;
	
	public static final byte TOGGLE_STEP_ASSIST=8;
	
	public static final byte TOGGLE_HUD=9;
}
