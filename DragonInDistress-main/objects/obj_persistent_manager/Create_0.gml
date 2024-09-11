// Create a global particle system that is persistent, so it can be used throughout the game
global.part_system = part_system_create_layer("Instances", true);

// If the game is running on a browser, it changes the window and application surface
// size to fit the browser area. It uses the height to ensure the aspect ratio stays the same.
if (os_browser != browser_not_a_browser)
{
	var _aspect = 1920/1080;

	window_set_size(display_get_height() * _aspect, display_get_height());

	surface_resize(application_surface, display_get_height() * _aspect, display_get_height());

	display_set_gui_size(1920, 1080);
}

// Play music track with looping enabled
//audio_play_sound(snd_music_level, 0, true);

// Set the falloff model used for all audio emitters, like in obj_end_gate
audio_falloff_set_model(audio_falloff_linear_distance_clamped);

// Set listener orientation for proper 3D audio
audio_listener_orientation(0, 0, 1000, 0, -1, 0);

// This variable stores the number of coins the player has collected.
global.coins = 0;

// Shop stuff
global.items = ds_list_create();

ds_list_add(global.items, ["Vigor!", 3, "You feel powerful!", "spr_vigor"]);
ds_list_add(global.items, ["Double Jump", 4, "Lets you jump an additional time in midair", "spr_double_jump"]);
ds_list_add(global.items, ["Magic Missile", 5, "A wizard's most basic spell", "spr_mm_b"]);
ds_list_add(global.items, ["Dash", 5, "Lets you dash when on the ground", "spr_dash"]);
ds_list_add(global.items, ["Lazy Worm", 0, "Years of slumber have made this sloth weak", "spr_b0"]);
// Variables to check if upgrade was added to item shop list
global.magicmissile_add = false;

// Variables to keep track of currently purchased upgrades
global.inventory = [];
global.invcount = 0;
global.boss_level = 0;
global.magic_missile_b = false;
global.magic_missile_u = false;
global.double_jump = false;
global.dash = false;
global.player_health = 3;

global.boss_level_purchased = 0;
global.boss_level_defeated = -1;
global.boss_level_unlocked = 0;
global.in_cutscene = false;
global.game_done = false;
