/// @description Insert description here
// You can write your code in this editor


event_inherited();
defeated_object = obj_enemy_defeated;
move_speed = 5;
hp = 21;

player_offset_x = 600;
player_offset_y = 200;
offset_allowance = 200;

// Code for flying
enum state {idle, swooping};
swoop_player_dist = 8;
overshoot = 400;
swoop_range = 600;
state = state.idle;
swoop_available = true;

fireburst_available = false;
fireball_available = false;
fireballs = 3;
alarm[1] = room_speed * 3;
firing = false;