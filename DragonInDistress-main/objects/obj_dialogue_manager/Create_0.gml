/// @description manage opening cutscene
player_dialogue_line = 0;
wizard_dialogue_line = 0;
cutscene_order_index = 0;//Tracks current line in cutscene
current_cutscene_index = 0;//Tracks current cutscene
global.player_died = false;

first_player_dialogue[0] = "It's been months...";
first_player_dialogue[1] = "The dragon guards my\nprison, and a massive\nsteel door guards the\ndragon";
first_player_dialogue[2] = "My rescue is beyond\nhope";
first_wiz_dialogue[0] = "Well golly!\nAren't you a sight for\nsore eyes??";
first_player_dialogue[3] = "Are you here to save me?";
first_wiz_dialogue[1] = "Not so fast!\nI may be a powerful\nwizard, but I am also an\naspiring businessman!";
first_wiz_dialogue[2] = "Saving princesses is a\nfulfilling venture, but it\ndoesn't pay the bills!";
first_wiz_dialogue[3] = "If you wish to purchase\nyour freedom, I expect\nyou to pony up some\nsoul coins!";
first_player_dialogue[4] = "Soul coins?";
first_wiz_dialogue[4] = "Bah! No soul coins?\nTake this magic sword\nand get some from that\nfat worm outside";
first_wiz_dialogue[5] = "I'll be locking the door\nbehind you, so don't come\nback unless you're ready\nto pay for services like\neveryone else.";
first_cutscene_order = [0,0,0,1,0,1,1,1,0,1,1]; //0 is player, 1 is wizard

second_wiz_dialogue[0] = "Back already?\nSeems your talents are\nwasted on whining in\nthis pitiful cave.";
second_player_dialogue[0] = "The dragon exploded!";
second_wiz_dialogue[1] = "Yes... they tend to do that";
second_player_dialogue[1] = "But the steel door is\nstill intact. How am I\nsupposed to escape?";
second_wiz_dialogue[2] = "The power released in\ndeath is no more than\nthe power held in life.";
second_wiz_dialogue[3] = "In other words, the\ndragon needs to be\nstronger before you\nkill it.";
second_wiz_dialogue[4] = "The dragon can be\nrevived with magic, but\nit will not break the door\nin its' current state";
second_wiz_dialogue[5] = "I will gladly strengthen\nyour foe... for a hefty fee\nof course!";
second_wiz_dialogue[6] = "Rest assured that you\nmay purchase your own\nstrength from me as well.\nHappy hunting!";
second_cutscene_order = [1,0,1,0,1,1,1,1,1];

final_wiz_dialogue[0] = "I sense the beast is\nslain.\nand the door opened";
final_player_dialogue[0] = "I guess this is\ngoodbye...";
final_wiz_dialogue[1] = "Indeed it is.\nYou've been a great\ncustomer!";
final_wiz_dialogue[2] = "Farewell damsel!";
final_cutscene_order = [1,0,1,1];

cutscene_dialogue_order = [first_cutscene_order, second_cutscene_order, final_cutscene_order];
player_dialogue = [first_player_dialogue, second_player_dialogue, final_player_dialogue];
wiz_dialogue = [first_wiz_dialogue, second_wiz_dialogue, final_wiz_dialogue];

death_wiz_dialogue[0] = "Ah. It appears you died out\nthere. You get this revive\nas a freebie, but your\nsoul coins were destroyed.";
death_wiz_dialogue[1] = "I have no time to speak\nwith broke princesses!\nGo out there and get\nsome more coins!";

