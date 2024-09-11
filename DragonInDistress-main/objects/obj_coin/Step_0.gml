/// @description gravity
if(grounded){
	hspeed = 0;
	vspeed = 0;
	exit;
}

var coll_x = check_collision(hspeed, 0);
var coll_y = check_collision(0, vspeed);
if(coll_x){
	hspeed = 0;
}
if(coll_y){
	vspeed = 0;
}
else{
	vspeed += 1;
}