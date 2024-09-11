/// @description Insert description here
// You can write your code in this editor
for(var i = 0; i < 3; i++){
	for(var n = 0; n < 3; n++){
		effect_create_below(ef_firework, x+random_range(-sprite_width/2, sprite_width/2), y+random_range(-sprite_height/2, sprite_height/2), 1000, c_red);
		effect_create_above(ef_explosion, x+random_range(-sprite_width/2, sprite_width/2), y+random_range(-sprite_height/2, sprite_height/2), 1000, c_orange);
	}
}
alarm[1] = room_speed * .5;
