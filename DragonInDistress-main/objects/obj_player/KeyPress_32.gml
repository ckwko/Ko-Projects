if (in_knockback || attacking||global.in_cutscene)
{
	// In that case we exit/stop the event here, so the player can't move.
	exit;
}
if(dashing){
	dashing = false;
	friction_power = 0.7;
	grav_speed = 1;
}

// This checks if the 'grounded' variable is true, meaning the player is standing on the ground, and can jump.
if (global.double_jump) {
	if (num_jumps > 0)
	{
		// This sets the Y velocity to negative jump_speed, making the player immediately jump upwards. It
		// will automatically be brought down by the gravity code in the parent's Begin Step event.
		vel_y = -jump_speed;

		// This changes the player's sprite to the jump sprite, and resets the frame to 0.
		sprite_index = spr_player_jump;
		image_index = 0;

		// This sets 'grounded' to false, so that any events after this know that the player is not supposed
		// to be on the ground anymore.
		grounded = false;

		// This creates an instance of obj_effect_jump at the bottom of the player's mask. This is the
		// jump VFX animation.
		instance_create_layer(x, bbox_bottom, "Instances", obj_effect_jump);
	
		// Play the jump sound with a random pitch
		var _sound = audio_play_sound(snd_jump, 0, 0);
		audio_sound_pitch(_sound, random_range(0.8, 1));
		
		num_jumps -= 1;
	}
} else {
	if (grounded || sprite_index == spr_player_wall_slide)
	{
		// This sets the Y velocity to negative jump_speed, making the player immediately jump upwards. It
		// will automatically be brought down by the gravity code in the parent's Begin Step event.
		vel_y = -jump_speed;
		if(sprite_index == spr_player_wall_slide){
			vel_x = -image_xscale * 10;
			//in_knockback = true;
			//alarm[1] = room_speed * .8;
		}

		// This changes the player's sprite to the jump sprite, and resets the frame to 0.
		sprite_index = spr_player_jump;
		image_index = 0;

		// This sets 'grounded' to false, so that any events after this know that the player is not supposed
		// to be on the ground anymore.
		grounded = false;

		// This creates an instance of obj_effect_jump at the bottom of the player's mask. This is the
		// jump VFX animation.
		instance_create_layer(x, bbox_bottom, "Instances", obj_effect_jump);
	
		// Play the jump sound with a random pitch
		var _sound = audio_play_sound(snd_jump, 0, 0);
		audio_sound_pitch(_sound, random_range(0.8, 1));
	}
}