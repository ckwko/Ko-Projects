// If we're in the end room
if (room == rm_menu)
{
	// Play music track with looping enabled
	audio_play_sound(BossRoom, 0, 1);
}
else if(room == rm_level_1 && !global.game_done){
	//audio_play_sound(DragonTheme, 0, 1);
}
else if(room == rm_shop && obj_dialogue_manager.current_cutscene_index>0){
	audio_play_sound(ShopMusic, 0, 1);
}