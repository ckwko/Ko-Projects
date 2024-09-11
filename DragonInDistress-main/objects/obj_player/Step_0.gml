//depth = -1;
if(room == rm_shop){
	if (global.shop){
		exit;
	}
}

event_inherited();

// Set the position of the default audio listener to the player's position, for positional audio
// with audio emitters (such as in obj_end_gate)
audio_listener_set_position(0, x, y, 0);

if(ds_list_find_index(global.inv, "Magic Missile") != -1){
	global.magic_missile_b = true;
}
if(ds_list_find_index(global.inv, "Magic Missile Shotgun") != -1){
	global.magic_missile_u = true;
}
if(ds_list_find_index(global.inv, "Boss Upgrade") != -1){
	global.boss_level = 1;
}

