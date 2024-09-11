/// @description Insert description here
// You can write your code in this editor
event_inherited();

if(fireburst_available){
	audio_play_sound(snd_dragon_growl, 0, 0);
	sprite_index = dragon_fireball;
	fireburst_available = false;
	firing = true;
}
else if(firing && fireball_available && image_index >= 5){
	audio_play_sound(snd_dragon_fireball1, 0, 0);
	instance_create_layer(x, y, "Instances", obj_fireball);
	fireballs--;
	fireball_available = false;
	if(fireballs == 0){
		fireballs = 3;
		fireburst_available = false;
		firing = false;
		alarm[1] = room_speed * 3;
	}
	else{
		alarm[2] = room_speed * 1;
	}
}