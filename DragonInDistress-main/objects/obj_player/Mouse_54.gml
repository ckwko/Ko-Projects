/// @description Shoot
// You can write your code in this editor

if(in_knockback || attacking || global.in_cutscene){
	exit;
}

if(global.magic_missile_u){
	audio_play_sound(snd_mm, 0, 0);
	sprite_index = spr_player_shoot;
	repeat(5){
		var mm = instance_create_layer(x, y, "Instances", obj_mm_u);
		mm.direction = point_direction(x, y, mouse_x, mouse_y) + random_range(-20, 20);
	}
	attacking = true;
	alarm[1] = room_speed * 1;
	
} else if(global.magic_missile_b and !global.magic_missile_u){
	audio_play_sound(snd_mm, 0, 0);
	sprite_index = spr_player_shoot;
	var mm = instance_create_layer(x, y, "Instances", obj_magic_missile);
	mm.direction = point_direction(x, y, mouse_x, mouse_y);
	attacking = true;
	alarm[1] = room_speed * 0.5;
}