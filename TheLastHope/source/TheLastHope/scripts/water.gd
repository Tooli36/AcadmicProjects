extends Area2D

export var size = 1
onready var ssprite = $s
onready var msprite = $m
onready var bsprite = $b
func _ready():
	set_meta("type", "water")
	match (size):
		1:
			ssprite.show()
		2:
			msprite.show()
		3:
			bsprite.show()

func drag():
	pass

func _enter_tree():
	Globals.random.randomize()
	global_position.x = Globals.random.randi_range(50, 1220)
	global_position.y = Globals.random.randi_range(500, 930)

func apply_effect():
	match (size):
		1:
			Globals.water_points+=Globals.waterdropsmall
			Globals.growth_points+=Globals.waterdropsmall
		2:
			Globals.water_points+=Globals.waterdropmed
			Globals.growth_points+=Globals.waterdropmed
		3:
			Globals.water_points+=Globals.waterdropbig
			Globals.growth_points+=Globals.waterdropbig
	Globals.water_points=clamp(Globals.water_points,0,Globals.water_points_max)

