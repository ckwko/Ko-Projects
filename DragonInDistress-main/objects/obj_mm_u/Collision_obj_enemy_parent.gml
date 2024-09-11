/// @description Insert description here
// You can write your code in this editor
if(irandom(4) == 1){
	with(other){
		event_perform(ev_other, ev_user0);
	}
}
other.hp -= 1;
instance_destroy();