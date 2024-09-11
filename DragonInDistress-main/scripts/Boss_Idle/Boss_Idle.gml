// Script assets have changed for v2.3.0 see
// https://help.yoyogames.com/hc/en-us/articles/360005277377 for more information
function Boss_Idle(){
	player_dist_x = obj_player.x - x;
	player_dist_y = obj_player.y - y;
	if(point_distance(x, y, obj_player.x + player_offset_x, obj_player.y - player_offset_y) > offset_allowance){
		move_towards_point(obj_player.x + player_offset_x, obj_player.y - player_offset_y, 6);
	}
	
	if(distance_to_object(obj_player) < swoop_range && swoop_available){
		if(random_range(0,1) > 0.5){
			swoop_available = false;
			alarm[0] = room_speed * 3;
		}
		path_end();
		state = state.swooping;
		hdir = sign(obj_player.x - x);
		swoop_y_vel = 10;
	}
}