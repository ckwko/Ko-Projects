/// @description Drop through platforms
if (in_knockback || attacking||global.in_cutscene || dashing)
{
	// In that case we exit/stop the event here, so the player can't move.
	exit;
}
if(place_meeting(x, y+1, obj_platform)){
	y += 1;
	grounded = false;
	sprite_index = spr_player_fall;
	image_index = 0;
}