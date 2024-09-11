/// @description Insert description here
// You can write your code in this editor
if(instance_exists(obj_player) && obj_player.x < x){
	image_xscale = -1;
}
else{
	image_xscale = 1;
}