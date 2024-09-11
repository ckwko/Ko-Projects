/// @description Insert description here
// You can write your code in this editor
if(room != rm_shop){
	exit;
}
wizard_dialogue_line = 0;
player_dialogue_line = 0;
if((global.boss_level == 0 && !global.player_died && current_cutscene_index < array_length(player_dialogue)) &&current_cutscene_index != 2|| (current_cutscene_index == 2 && global.game_done)){
	if(current_cutscene_index == 0){
		obj_wizard.visible = false;
	}
	global.in_cutscene = true;
	var textHeight = room_height / 5;
	obj_dialogue_box.visible = true;
	if(cutscene_dialogue_order[current_cutscene_index][cutscene_order_index] == 0){
		obj_dialogue_box.color = c_fuchsia;
		obj_dialogue_box.text = player_dialogue[current_cutscene_index][player_dialogue_line++];
	}
	else{
		obj_dialogue_box.color = c_blue;
		obj_dialogue_box.text = wiz_dialogue[current_cutscene_index][wizard_dialogue_line++];
	}
}
else if(global.player_died){
	global.in_cutscene = true;
	var textHeight = room_height / 5;
	obj_dialogue_box.visible = true;
	obj_dialogue_box.color = c_blue;
	obj_dialogue_box.text = death_wiz_dialogue[wizard_dialogue_line++];
}
else{
	obj_dialogue_box.visible = false;
}