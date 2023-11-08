extends Area2D

enum trash_type {PLASTIC, CAN, OPENCAN, BAG}
export(trash_type) var type = trash_type.PLASTIC
onready var plastic_sprite = $shape/plastic
onready var can_sprite = $shape/can
onready var ocan_sprite = $shape/ocan
onready var bag_sprite = $shape/bag
	
func _ready():
	match (type):
		trash_type.PLASTIC:
			set_meta("type", "plastic")
			plastic_sprite.show()
			
		trash_type.CAN:
			set_meta("type", "can")
			can_sprite.show()
			
		trash_type.OPENCAN:
			set_meta("type", "ocan")
			ocan_sprite.show()
	
		trash_type.BAG:
			set_meta("type", "bag")
			bag_sprite.show()
	
func _enter_tree():
	Globals.random.randomize()
	global_position.x = Globals.random.randi_range(50, 1220)
	global_position.y = Globals.random.randi_range(390, 600)

func drag():
	pass


func apply_effect():
	match (type):
		trash_type.PLASTIC:
			Globals.health_points-=Globals.lose_plastic
			
		trash_type.CAN:
			Globals.health_points-=Globals.lose_can
			
		trash_type.OPENCAN:
			Globals.health_points-=Globals.lose_ocan
	
		trash_type.BAG:
			Globals.health_points-=Globals.lose_bag


