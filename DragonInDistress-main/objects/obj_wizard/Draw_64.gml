/// @description Insert description here
// You can write your code in this editor


if(!shopOpen){
	if(show){
		draw_set_color(c_white);
		draw_text_transformed(x+100, y+150, "[E]", 1, 1, 0);
	}
	exit;
}


if(!ds_list_empty(global.items)){
	draw_set_color(c_black);
		draw_set_alpha(0.8);
			draw_rectangle(0, 0, guiWidth, guiHeight, 0);
		draw_set_alpha(0.5);
			draw_rectangle(menuMargin, 0, menuMargin+menuWidth, guiHeight, 0);
		draw_set_alpha(1);
	draw_set_color(c_white);
	
	for(var i = 0; i < itemCount; i++){
		var arr = global.items[| i];
		var item = arr[0];
	
		var _x = menuMargin + menuWidth/2;
		var _y = (guiHeight/2) + (i-selectedAnim) * 128;
	
		var s = 1;
		if(i == selected){
			s = 1.25;
			item = "> " + item + " <";
		}
	
		draw_set_halign(fa_center);
			draw_set_valign(fa_middle);
				draw_text_transformed(_x, _y, item, s, s, 0);
			draw_set_valign(fa_top);
		draw_set_halign(fa_left);
	}

	var arr = global.items[| selected];
	var item = arr[0];
	var price = arr[1];
	var desc = arr[2];
	var sprID = arr[3];

	var _x = (menuMargin + menuWidth) + previewWidth/2;
	var _y = (guiHeight/2);

	var spr = asset_get_index(sprID);

	if(sprite_exists(spr)){
		var s = 2;
	
		draw_sprite_ext(spr, 0, _x, _y - 50, s, s, 0, -1, 1);
	}

	draw_set_halign(fa_center);
		draw_set_valign(fa_middle);
			draw_text(_x, _y + 250, desc);
		//draw_set_valign(fa_left);
	draw_set_halign(fa_left);

	draw_text(10, 45, "Soul Essence: " + string(global.coins));

	draw_set_valign(fa_bottom);
		draw_text(menuMargin + menuWidth + 5, guiHeight - 5, "Price: " + string(price) + " Soul Essence");
	
		draw_set_halign(fa_right);
			draw_text(guiWidth - 5, guiHeight - 5, "Hit ENTER to buy");
		draw_set_halign(fa_left);
	draw_set_valign(fa_top);
} else {
	draw_set_color(c_black);
		draw_set_alpha(0.8);
			draw_rectangle(0, 0, guiWidth, guiHeight, 0);
	draw_set_color(c_white);
		draw_set_halign(fa_center);
			draw_set_valign(fa_middle);
				draw_text(guiWidth/2, guiHeight/2, "All Upgrades Purchased!");
	draw_set_alpha(1);
}