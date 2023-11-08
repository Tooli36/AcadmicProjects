extends Control


onready var hpbar = $MarginContainer2/healthbar
onready var gbar = $MarginContainer/growthbar
onready var wbar = $MarginContainer3/waterbar


func _ready():
	pass # Replace with function body.


func _physics_process(delta):
	match (Globals.current_level):
		1:
			gbar.max_value=Globals.min_lv1
		2:
			gbar.max_value=Globals.min_lv2
		3:
			gbar.max_value=Globals.min_lv3
		4:
			gbar.max_value=Globals.min_lv4
		5:
			gbar.max_value=Globals.min_lv5
	
	
	hpbar.value=Globals.health_points
	hpbar.max_value=Globals.health_points_max
	gbar.value=Globals.growth_points
	wbar.value=Globals.water_points
	wbar.max_value=Globals.water_points_max
