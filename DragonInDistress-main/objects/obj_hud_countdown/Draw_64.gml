/// @description Insert description here
// You can write your code in this editor


if(alarm[0] > 0){
	draw_text(window_get_width() / 2, window_get_height() / 2, alarm[0] / room_speed);
	draw_text(20, 20, "ESCAPE TO THE CAVE");
}
else if(blink){
	draw_text(window_get_width() / 2, window_get_height() / 2, 0);
	alarm[1] = room_speed * 0.7;
}