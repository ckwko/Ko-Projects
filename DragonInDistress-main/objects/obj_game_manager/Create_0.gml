// This creates the HUD sequence, which shows all HUD elements (such as coins and hearts). It lasts throughout the level.
layer_sequence_create("Instances", 0, 0, seq_game_hud);

// paused: This will be true when the game is paused, and false when it's not
paused = false;
// pause_sequence: The ID of the Sequence element created for the pause menu, used to close it
// when the player hits Escape to resume
pause_sequence = -1;
// in_cave: Tells whether the player is in a cave (true) or not (false), used for ambient audio
in_cave = false;

// If the game is running on a browser, it creates the touch controls sequence
if (os_browser != browser_not_a_browser)
{
	layer_sequence_create("TouchControlsLayer", 0, 0, seq_touch_controls);
}
// If the game is running on Opera GX, Android, or iOS, it creates the touch controls sequence
else if (os_type == os_operagx || os_type == os_android || os_type == os_ios)
{
	layer_sequence_create("TouchControlsLayer", 0, 0, seq_touch_controls);
}

// If the "EffectLeaf" layer exists,
if (layer_exists("EffectLeaf"))
{
	// Get the leaf effect FX struct
	effect_leaf = layer_get_fx("EffectLeaf");

	// Get the number of leaves
	leaf_count = fx_get_parameter(effect_leaf, "param_num_particles");
}
in_cave = true;

// ORIGINAL CODE
if(room == rm_shop){
	exit;
}
shop_entrance.visible = false;
game_exit.visible = false;
if(global.game_done){
	game_exit.visible = true;
}
if(global.boss_level == 0){
	instance_create_layer(1280, 1280, "Instances", obj_boss0);
}
else if(global.boss_level == 1){
	instance_create_layer(1280, 1280, "Instances", obj_boss1);
}
else if(global.boss_level == 2 && !global.game_done){
	instance_create_layer(1280, 1280, "Instances", obj_boss2);
}