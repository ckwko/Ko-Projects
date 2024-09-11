/// @description Insert description here
// You can write your code in this editor
if(!instance_exists(obj_player)){
	instance_destroy();
	exit;
}
speed = 15;
direction = point_direction(x, y, obj_player.x, obj_player.y);
image_angle = direction;