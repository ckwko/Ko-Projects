/// @description Insert description here
// You can write your code in this editor
if(!instance_exists(obj_player)){
	exit;
}
if(hp <=0 ){
	instance_destroy();
}
if(!firing){
	switch(state){
		case state.idle: 
			Boss_Idle(); 
			break;
		case state.swooping:
			Boss_Swoop(); 
			break;
	}
}


if(fireburst_available){
	audio_play_sound(snd_dragon_growl, 0, 0);
	sprite_index = dragon_fireball;
	image_index = 0;
	fireball_available = true;
	fireburst_available = false;
	vel_x = 0;
	vel_y = 0;
	speed = 0;
	firing = true;
}
else if(fireball_available && image_index >= 5){
	for(var i = 0; i < 3; i++){
		audio_play_sound(snd_dragon_fireball2, 0, 0);
		with instance_create_layer(x, y, "Instances", obj_fireball){
			direction = point_direction(x, y, obj_player.x, obj_player.y)+(i-1)*30;
			image_angle = direction;
			speed = 8
			};
	}
	fireball_available = false;
	fireballs--;
	if(fireballs == 0){
		fireballs = 3;
		firing = false;
		alarm[2] = room_speed * 3;
	}
	else{
		alarm[1] = room_speed * 1;
	}
}