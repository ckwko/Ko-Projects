/// @description Attack
// Spawn damage obj
if(in_knockback || attacking||global.in_cutscene){
	exit;
}

audio_play_sound(snd_swing, 0 ,0);
sprite_index = spr_player_swipe;
attacking = true;
instance_create_layer(x + 60 * image_xscale, y-10, "Instances", obj_player_swipe);