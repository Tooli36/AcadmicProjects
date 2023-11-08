extends KinematicBody2D

var speed = 2
var dir = Vector2(1, 0)
var start_pos
onready var bug_sprite = $shape/bug
onready var worm_sprite = $shape/worm
var dargging=false
export var isworm=true

func _ready():
	if isworm:
		worm_sprite.show()
	else:
		bug_sprite.show()
	


func _enter_tree():
	Globals.random.randomize()
	global_position.x = Globals.random.randi_range(50, 1220)
	global_position.y = Globals.random.randi_range(390, 500)
	start_pos = global_position
	speed = Globals.random.randf_range(.5, 3)

func apply_effect():
	if isworm:
		Globals.health_points-=Globals.lose_worm
	else:
		Globals.health_points-=Globals.lose_bug	

func _physics_process(delta):
	if global_position.x > 1230:
		dir.x*= -1
		bug_sprite.flip_h=false
		worm_sprite.flip_h=false
	elif global_position.x < start_pos.x:
		dir.x*= -1
		bug_sprite.flip_h=true
		worm_sprite.flip_h=true
	if not dargging:
		move_and_collide(dir * speed, false, false)

func drag():
	dargging=true
