// Swoop attack
function Boss_Swoop(){
	if(!audio_is_playing(snd_dragon_roar)){
		audio_play_sound(snd_dragon_roar, 0, 0);
	}
	swoop_grav_y = -.2;
	swoop_y_vel += swoop_grav_y;
	x += hdir * 13;
	y += swoop_y_vel;
	
	if((abs(obj_player.x - x) > overshoot) && (sign(obj_player.x - x) == -hdir)){
		state = state.idle;
		swoop_available = false;
		alarm[0] = room_speed * 5;
	}
}