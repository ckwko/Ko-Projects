/// @description Insert description here
// You can write your code in this editor
depth = -1;
shopOpen = false;
global.shop = false;

selected = 0;
selectedAnim = 0

itemCount = ds_list_size(global.items);

guiWidth = display_get_gui_width();
guiHeight = display_get_gui_height();

menuWidth = guiWidth * 0.3;
menuMargin = guiWidth * 0.1;

previewWidth = (guiWidth - (menuWidth+menuMargin));

show = false;

