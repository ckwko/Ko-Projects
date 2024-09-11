// Draw instance on the GUI layer; we're using
// Draw GUI Begin so it draws before the buttons.
// This object is placed in the seq_pause_menu Sequence.
draw_self();
//menu
draw_set_color(c_black);
	draw_set_alpha(0.8);
		draw_rectangle(0, 0, guiWidth/4, guiHeight, 0);

// inventory
draw_set_color(c_white);
	draw_set_alpha(1);
		draw_set_halign(fa_center);
			draw_set_valign(fa_middle);
				draw_text_transformed(guiWidth/8, 50, "Inventory", 1, 1, 0);
				
draw_set_halign(fa_left)
var _y = 150;
for(var i = 0; i < array_length(global.inventory); i++){
	var item = global.inventory[i];
	draw_text_transformed(50, _y, item, 0.75, 0.75, 0);
	_y += 75;
}

// controls
draw_set_color(c_black);
	draw_set_alpha(0.8);
		draw_rectangle(guiWidth/4 + 100, 0, guiWidth/2 + 100, guiHeight, 0);
draw_set_color(c_white);
	draw_set_alpha(1);
		draw_set_halign(fa_center);
			draw_set_valign(fa_middle);
				draw_text_transformed(guiWidth/2 - 150, 50, " Player Controls", 1, 1, 0);
draw_set_halign(fa_left)
draw_text_transformed(guiWidth/4 + 150, 150, "A - Move left", 0.75, 0.75, 0);
draw_text_transformed(guiWidth/4 + 150, 225, "D - Move right", 0.75, 0.75, 0);
draw_text_transformed(guiWidth/4 + 150, 300, "SPACE - Jump up", 0.75, 0.75, 0);
draw_text_transformed(guiWidth/4 + 150, 375, "LMB - Melee attack", 0.75, 0.75, 0);
draw_text_transformed(guiWidth/4 + 150, 450, "RMB - Ranged attack", 0.75, 0.75, 0);

draw_set_halign(fa_center);
	draw_set_valign(fa_middle);
		draw_text_transformed(guiWidth/2 - 150, 600, "Shop Controls", 1, 1, 0);

draw_set_halign(fa_left)
draw_text_transformed(guiWidth/4 + 150, 700, "E - Open/Close shop", 0.75, 0.75, 0);
draw_text_transformed(guiWidth/4 + 150, 775, "W - Scroll up", 0.75, 0.75, 0);
draw_text_transformed(guiWidth/4 + 150, 850, "S - Scroll down", 0.75, 0.75, 0);
draw_text_transformed(guiWidth/4 + 150, 925, "ENTER - Purchase item", 0.75, 0.75, 0);

