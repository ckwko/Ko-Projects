/// @description Dash vfx
//effect_create_above(ef_explosion, x, y, 200, c_aqua);
//effect_create_below(ef_smoke, x, y, 200, c_yellow);
effect_create_above(ef_smoke, x, y+75, 200, c_white);
effect_create_above(ef_smoke, x, y+75, 125, c_dkgrey);
if(dashing){
	alarm[4]=0.05*room_speed;
}