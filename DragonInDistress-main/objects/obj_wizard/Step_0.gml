/// @description Insert description here
// You can write your code in this editor

if (!shopOpen && place_meeting(x, y+10, obj_player)){
	show = true;
	if(keyboard_check_pressed(ord("E"))){
		shopOpen = true;
		global.shop = true;
	}
} else if (shopOpen && (keyboard_check_pressed(ord("E"))||keyboard_check_pressed(vk_escape))) {
	shopOpen = false;
	global.shop = false;
} else {
	show = false;
}

if(!ds_list_empty(global.items)){
	if(shopOpen){
		selectedAnim = lerp(selectedAnim, selected, 0.1);
	
		if(keyboard_check_pressed(ord("S"))){
			selected++;
			if(selected == itemCount){
				selected = 0;
			}
		}
	
		if(keyboard_check_pressed(ord("W"))){
			selected--;
			if(selected < 0){
				selected = itemCount - 1;
			}
		}
	
		//Buy
		var arr = global.items[| selected];
		var item = arr[0];
		var price = arr[1];
	
		if(price != "SOLD" && (keyboard_check_pressed(vk_enter) && global.coins >= price)){
			// Add upgrades
			var boss_purchased = -1;
			if(item == "Magic Missile"){
				global.magic_missile_b = true;
			}
			if(item == "Magic Missile Shotgun"){
				global.magic_missile_u = true;
			}
			if(item == "Dash"){
				global.dash = true;
				obj_player.dash_available = true;
			}
			if(item == "Lazy Worm"){
				global.boss_level = 0;
				boss_purchased = 0;
			}
			if(item == "Wild Beast"){
				global.boss_level = 1;
				boss_purchased = 1;
			}
			if(item == "Flaming Demon"){
				global.boss_level = 2;
				boss_purchased = 2;
			}
			if(item == "Double Jump") {
				global.double_jump = true;
			}
			if(item == "Vigor!"){
				global.player_health++;
				obj_player.hp = global.player_health;
			}
			if(boss_purchased > 0 && global.boss_level_purchased < boss_purchased){
				audio_play_sound(snd_buy, 0, 0);
				global.boss_level_purchased = boss_purchased;
				global.coins -= price;
			}
			else if((item == "Vigor!" && global.player_health == 6) || (item != "Vigor!" && boss_purchased == -1)){
				// Set the upgrade to sold so it can't be bought twice
				global.coins -= price;
				replace = ds_list_find_index(global.items, arr);
				arr[1] = "SOLD";
				ds_list_replace(global.items, replace, arr)
			}
			if(boss_purchased < 0){
				audio_play_sound(snd_buy, 0, 0);
				array_push(global.inventory, item);
			}
			
		}
	} else {
		selected = 0;
		rmvCount = 0;
		to_rmv = ds_list_create();
		for(var i = 0; i < itemCount; i++){
			var arr = global.items[| i];
			var price = arr[1];
			if(price == "SOLD"){
				ds_list_add(to_rmv, arr);
				rmvCount++;
			}
		}
		for(var i = 0; i < rmvCount; i++){
			var arr = to_rmv[| i]
			ds_list_delete(global.items, ds_list_find_index(global.items, arr));
		}
		itemCount -= rmvCount;
		

		//Add sequential upgrade
		if(global.magic_missile_b){
			if(!global.magic_missile_u and !global.magicmissile_add){
				ds_list_add(global.items, ["Magic Missile Shotgun", 5, "A wizard's most basic spell, but in a spread!", "spr_mm_u"]);
				itemCount += 1;
				global.magicmissile_add = true;
			}
		}
		
	}
}
