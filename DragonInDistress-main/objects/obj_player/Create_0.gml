// This runs the Create event of the parent, ensuring the player gets all variables from the character parent.
event_inherited();

hit_by_attack = ds_list_create();

// This variable tells whether the player is currently in knockback (from being hit by an enemy). It will be true if it is, and false if not.
in_knockback = false;
attacking = false;
shooting = false;

// This is the object that replaces the player once it is defeated.
defeated_object = obj_player_defeated;

hp = global.player_health;

// Keep track of player's inventory/upgrades
global.inv = ds_list_create();

invincible = false;
// Upgrades
dash_speed = 15;
dashing = false;
dash_available = global.dash;