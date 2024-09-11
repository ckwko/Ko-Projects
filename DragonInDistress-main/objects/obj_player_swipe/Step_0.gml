/// @description Follow player
if(!instance_exists(obj_player)){
	instance_destroy();
	exit;
}
x = obj_player.x + 60 * obj_player.image_xscale;
y = obj_player.y - 10;