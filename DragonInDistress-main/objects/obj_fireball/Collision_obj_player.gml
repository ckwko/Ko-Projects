/// @description Insert description here
// You can write your code in this editor
if(other.invincible = false){
	other.hp -= 1;
	other.invincible = true;
	other.alarm[2] = room_speed * 2;
	audio_play_sound(snd_life_lost_01, 0, 0);
}
instance_destroy();