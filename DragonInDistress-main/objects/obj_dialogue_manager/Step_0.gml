/// @description Insert description here
// You can write your code in this editor
if(global.in_cutscene && keyboard_check_pressed(vk_enter)){
	if(global.player_died){
		if(wizard_dialogue_line == array_length(death_wiz_dialogue)){
			global.in_cutscene = false;
			wizard_dialogue_line = 0;
			global.player_died = false;
			obj_dialogue_box.visible = false;
		}
		obj_dialogue_box.text = death_wiz_dialogue[wizard_dialogue_line++];
		exit;
	}
	else if(cutscene_order_index+1 < array_length(cutscene_dialogue_order[current_cutscene_index])){
		cutscene_order_index++;
		// Update text to new line
		if(cutscene_dialogue_order[current_cutscene_index][cutscene_order_index] == 0){
			obj_dialogue_box.color = c_fuchsia;
			obj_dialogue_box.text = player_dialogue[current_cutscene_index][player_dialogue_line++];
		}
		else{
			if(wizard_dialogue_line == 0 && current_cutscene_index = 0){
				obj_wizard.visible = true;
				for(var i = 0; i < 5; i++){
					for(var n = 0; n < 5; n++){
						effect_create_above(ef_firework, obj_wizard.x+random_range(-obj_wizard.sprite_width/2, obj_wizard.sprite_width/2), obj_wizard.y+random_range(-obj_wizard.sprite_height/2, obj_wizard.sprite_height/2), 1000, c_aqua);
					}
				}
				audio_play_sound(ShopMusic, 0, 1);
			}
			obj_dialogue_box.color = c_blue;
			obj_dialogue_box.text = wiz_dialogue[current_cutscene_index][wizard_dialogue_line++];
		}
	}
	else{
		global.in_cutscene = false;
		obj_dialogue_box.visible = false;
		current_cutscene_index++;
		player_dialogue_line = 0;
		wizard_dialogue_line = 0;
		cutscene_order_index = 0;
	}
}