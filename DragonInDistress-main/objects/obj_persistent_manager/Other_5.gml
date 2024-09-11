// Stop the main music track
// If you have other music tracks that may play, stop them here
if(global.boss_level_defeated == 0 && global.boss_level_unlocked == 0){
	ds_list_add(global.items, ["Wild Beast", 10, "Flame hath awoken in this mobile beast.", "spr_b1"]);
	global.boss_level_unlocked = 1;
}
else if(global.boss_level_defeated == 1 && global.boss_level_unlocked == 1){
	ds_list_add(global.items, ["Flaming Demon", 20, "Overflowing fire and speed.", "spr_b2"]);
	global.boss_level_unlocked = 2;
}
audio_stop_all();