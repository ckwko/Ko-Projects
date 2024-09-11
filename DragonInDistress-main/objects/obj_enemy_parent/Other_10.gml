/// @description drop a coin
instance_create_layer(x, y - sprite_height / 2, "Instances", obj_coin);
with(obj_coin){
	vspeed = random_range(-3, -5);
	hspeed = random_range(-4, 4);
}