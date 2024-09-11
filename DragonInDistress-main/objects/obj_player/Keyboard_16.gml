/// @description Dash
if (in_knockback || attacking ||global.in_cutscene||!dash_available)
{
	// In that case we exit/stop the event here, so the player can't move.
	exit;
}
dash_available = false;
dashing = true;
vel_x = image_xscale * 25;
vel_y = 0;
grav_speed = 0;
friction_power = 0.4;
sprite_index = spr_player_slide;
image_index = 0;

alarm[3] = room_speed * 0.5; // Dash cooldown
alarm[4] = room_speed * 0.05;