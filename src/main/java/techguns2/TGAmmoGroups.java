package techguns2;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import techguns2.ammo.AmmoGroup;

public final class TGAmmoGroups implements TGInitializer
{
    protected TGAmmoGroups()
    { }

    @SuppressWarnings("deprecation")
    @Override
    public final void setup(IEventBus eventBus)
    {
        REGISTER.makeRegistry(() -> {
            RegistryBuilder<AmmoGroup> builder = RegistryBuilder.of();
            builder.onBake(AmmoGroup::bakeRegistry);
            return builder;
        });
        REGISTER.register(eventBus);
    }

    private static final DeferredRegister<AmmoGroup> REGISTER = DeferredRegister.create(TGCustomRegistries.AMMO_GROUPS, Techguns.MODID);

    public static final RegistryObject<AmmoGroup> STONE_BULLETS = REGISTER.register("stone_bullets", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.STONE_BULLETS);
        return group;
    });
    public static final RegistryObject<AmmoGroup> PISTOL_ROUNDS = REGISTER.register("pistol_rounds", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.PISTOL_ROUNDS);
        group.addAmmo(TGAmmo.INCENDIARY_PISTOL_ROUNDS);
        return group;
    });
    public static final RegistryObject<AmmoGroup> SHOTGUN_SHELLS = REGISTER.register("shotgun_shells", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.SHOTGUN_SHELLS);
        group.addAmmo(TGAmmo.INCENDIARY_SHOTGUN_SHELLS);
        return group;
    });
    public static final RegistryObject<AmmoGroup> RIFLE_ROUNDS = REGISTER.register("rifle_rounds", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.RIFLE_ROUNDS);
        group.addAmmo(TGAmmo.INCENDIARY_RIFLE_ROUNDS);
        return group;
    });
    public static final RegistryObject<AmmoGroup> RIFLE_ROUND_CLIP = REGISTER.register("rifle_round_clip", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.RIFLE_ROUND_CLIP);
        group.addAmmo(TGAmmo.INCENDIARY_RIFLE_ROUND_CLIP);
        return group;
    });
    public static final RegistryObject<AmmoGroup> SNIPER_ROUNDS = REGISTER.register("sniper_rounds", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.SNIPER_ROUNDS);
        group.addAmmo(TGAmmo.INCENDIARY_SNIPER_ROUNDS);
        group.addAmmo(TGAmmo.EXPLOSIVE_SNIPER_ROUNDS);
        return group;
    });
    public static final RegistryObject<AmmoGroup> ROCKETS = REGISTER.register("rockets", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.ROCKET);
        group.addAmmo(TGAmmo.HIGH_VELOCITY_ROCKET);
        group.addAmmo(TGAmmo.NUCLEAR_ROCKET);
        return group;
    });
    public static final RegistryObject<AmmoGroup> GRENADES_40MM = REGISTER.register("grenades_40mm", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.GRENADE_40MM);
        return group;
    });
    public static final RegistryObject<AmmoGroup> GRENADES = REGISTER.register("grenades", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addSubGroup(TGAmmoGroups.GRENADES_40MM);
        return group;
    });
    public static final RegistryObject<AmmoGroup> ADVANCED_ROUNDS = REGISTER.register("advanced_rounds", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.ADVANCED_ROUNDS);
        return group;
    });
    public static final RegistryObject<AmmoGroup> PISTOL_ROUND_MAGAZINE = REGISTER.register("pistol_round_magazine", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.PISTOL_ROUND_MAGAZINE);
        group.addAmmo(TGAmmo.INCENDIARY_PISTOL_ROUND_MAGAZINE);
        return group;
    });
    public static final RegistryObject<AmmoGroup> SMG_ROUND_MAGAZINE = REGISTER.register("smg_round_magazine", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.SMG_ROUND_MAGAZINE);
        group.addAmmo(TGAmmo.INCENDIARY_SMG_ROUND_MAGAZINE);
        return group;
    });
    public static final RegistryObject<AmmoGroup> ASSAULT_RIFLE_ROUND_MAGAZINE = REGISTER.register("assault_rifle_round_magazine", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.ASSAULT_RIFLE_ROUND_MAGAZINE);
        group.addAmmo(TGAmmo.INCENDIARY_ASSAULT_RIFLE_ROUND_MAGAZINE);
        return group;
    });
    public static final RegistryObject<AmmoGroup> LMG_ROUND_MAGAZINE = REGISTER.register("lmg_round_magazine", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.LMG_ROUND_MAGAZINE);
        group.addAmmo(TGAmmo.INCENDIARY_LMG_ROUND_MAGAZINE);
        return group;
    });
    public static final RegistryObject<AmmoGroup> MINIGUN_ROUND_MAGAZINE = REGISTER.register("minigun_round_magazine", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.MINIGUN_ROUND_MAGAZINE);
        group.addAmmo(TGAmmo.INCENDIARY_MINIGUN_ROUND_MAGAZINE);
        return group;
    });
    public static final RegistryObject<AmmoGroup> AS50_ROUND_MAGAZINE = REGISTER.register("as50_round_magazine", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.AS50_ROUND_MAGAZINE);
        group.addAmmo(TGAmmo.INCENDIARY_AS50_ROUND_MAGAZINE);
        group.addAmmo(TGAmmo.EXPLOSIVE_AS50_ROUND_MAGAZINE);
        return group;
    });
    public static final RegistryObject<AmmoGroup> ADVANCED_ROUND_MAGAZINE = REGISTER.register("advanced_round_magazine", () -> {
        AmmoGroup group = new AmmoGroup();
        group.addAmmo(TGAmmo.ADVANCED_ROUND_MAGAZINE);
        return group;
    });
}
