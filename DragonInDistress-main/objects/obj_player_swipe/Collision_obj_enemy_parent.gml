/// @description hit enemy
if(hit_enemy){
	exit;
}

other.hp -= 3;
hit_enemy = true;

with(other){
	event_perform(ev_other, ev_user0);
}