extends Area2D


onready var segment = preload("res://objects/segment.tscn")
onready var marker = preload("res://objects/marker.tscn")
onready var water = preload("res://objects/water.tscn")
onready var trash = preload("res://objects/trash.tscn")
onready var bugsworms = preload("res://objects/wormnbug.tscn")
onready var lv1tree = $shape/Plant
onready var lv2tree = $shape/Tree1
onready var lv3tree = $shape/Tree2
onready var lv4tree = $shape/Tree3
onready var lv5tree = $shape/Tree4
onready var lv6tree = $shape/Tree5
onready var staticroots = $StaticRoots
onready var animation = $"../AnimationPlayer"

func _ready():
	Globals.health_points=Globals.health_points_max
	Globals.water_points=Globals.water_points_max
	Globals.growth_points = 0
	Globals.marker_created=false
	Globals.root_isout=false
	
	match (Globals.current_level):
		1:
			lv1tree.show()
			staticroots.hide()
		2:
			lv2tree.show()
		3:
			lv3tree.show()
		4:
			lv4tree.show()
		5:
			lv5tree.show()
		6:
			lv6tree.show()


func _physics_process(delta):
	if Globals.health_points<=0:
		animation.play("gameover")
	
	var clist = get_children()
	if Input.is_action_just_pressed("click") and not Globals.marker_created and not Globals.root_isout and get_viewport().get_mouse_position().y>380:
		Globals.root_isout=true
		var mouse_pos = get_viewport().get_mouse_position()
		var m = marker.instance()
		add_child(m)
		Globals.marker_created=true
		Globals.marker=m
		m.global_position = mouse_pos
		
		var s = segment.instance()
		add_child(s)
		var angle = s.global_position.angle_to_point(mouse_pos)
		s.rotation=angle
		s.follow(mouse_pos, m)
	
	#if its area
	var list = get_overlapping_areas()
	for i in list.size():
		if list[i].has_method("drag"):
			list[i].get_meta("type")
			list[i].apply_effect()
			list[i].queue_free()
	# if its body
	var blist = get_overlapping_bodies()
	for i in blist.size():
		if blist[i].has_method("drag"):
			blist[i].apply_effect()
			blist[i].queue_free()
			
	match (Globals.current_level):
		1:
			
			$water_timer.wait_time=Globals.watertimer_lv1
			$trash_timer.wait_time = Globals.water_plastic_lv1
			$trash_timer_can.wait_time = Globals.can_lv1
			$trash_timer_bag.wait_time = Globals.bag_lv1
			$trash_timer_ocan.wait_time = Globals.ocan_lv1
			$"bugs and worms".wait_time = Globals.bug_worm_lv1
			if Globals.water_points>0:
				Globals.water_points-=Globals.lose_water_lv1
			else:
				Globals.health_points-=Globals.lose_dry_lv1
			
			if Globals.growth_points>=Globals.min_lv1: ## go to next level
				var gamescene = load("res://scenes/GameWorld.tscn")
				Globals.current_level=2
				get_tree().change_scene_to(gamescene)
		2:
			$water_timer.wait_time=Globals.watertimer_lv2
			$trash_timer.wait_time = Globals.water_plastic_lv2
			$trash_timer_can.wait_time = Globals.can_lv2
			$trash_timer_bag.wait_time = Globals.bag_lv2
			$trash_timer_ocan.wait_time = Globals.ocan_lv2
			$"bugs and worms".wait_time = Globals.bug_worm_lv2
			if Globals.water_points>0:
				Globals.water_points-=Globals.lose_water_lv2
			else:
				Globals.health_points-=Globals.lose_dry_lv2
			
			if Globals.growth_points>=Globals.min_lv2: ## go to next level
				var gamescene = load("res://scenes/GameWorld.tscn")
				Globals.current_level=3
				get_tree().change_scene_to(gamescene)
		3:
			pass
		4:
			pass
		5:
			pass
		6:
			pass
			



func _on_water_timer_timeout():
	var w = water.instance()
	var r = Globals.random.randi_range(0,10)
	if r>=5:
		w.size=2
	elif r==0:
		w.size=3
	add_child(w)


func _on_trash_timer_timeout():
	var t = trash.instance()
	t.type=t.trash_type.PLASTIC
	add_child(t)


func _on_trash_timer_can_timeout():
	var t = trash.instance()
	t.type = t.trash_type.CAN
	add_child(t)


func _on_trash_timer_ocan_timeout():
	var t = trash.instance()
	t.type = t.trash_type.OPENCAN
	add_child(t)


func _on_trash_timer_bag_timeout():
	var t = trash.instance()
	t.type = t.trash_type.BAG
	add_child(t)
	
func toMenu():
	var menuscene = load("res://scenes/start.tscn")
	get_tree().change_scene_to(menuscene)


func _on_bugs_and_worms_timeout():
	var b = bugsworms.instance()
	var r = Globals.random.randi_range(0,5)
	if r==0:
		b.isworm=false
	else:
		b.isworm=true
	add_child(b)
