extends Area2D

var ctime
var dtime=0
var dtime_offset = 250
var dis=23
var loop=true
var timetodie =false
var mp
var mark

func _ready():
	pass

func follow(mouse_pos, m):
	mp=mouse_pos
	mark=m
	ctime = Time.get_ticks_msec()
		
func _process(delta):
	if ctime+Globals.drag_speed<Time.get_ticks_msec() and loop:
		loop=false
		var list = get_overlapping_areas()
		for i in list.size():
			if list[i].name=="marker":
				killself()
				list[i].queue_free()
				Globals.marker_created=false
			if list[i].has_method("drag"):
				killself()
				list[i].global_position=get_parent().global_position
				Globals.marker.queue_free()
				Globals.marker_created=false
				
		var blist = get_overlapping_bodies()
		for i in blist.size():
			if blist[i].has_method("drag"):
				blist[i].drag()
				killself()
				blist[i].global_position=get_parent().global_position
				Globals.marker.queue_free()
				Globals.marker_created=false
				
		var d = duplicate()
		add_child(d)
		var dir = global_position.direction_to(mp)
		var new_pos = global_position+(dir*dis)
		d.global_position = new_pos
		d.rotation_degrees=0
		d.follow(mp,mark)
		
	if dtime+Globals.return_speed<Time.get_ticks_msec() and timetodie:
		if get_parent().has_method("killself"):
			get_parent().killself()
		else:
			Globals.root_isout=false
		queue_free()

func killself():
	timetodie=true
	dtime = Time.get_ticks_msec()
	var list = get_overlapping_areas()
	for i in list.size():
		if list[i].has_method("drag"):
			list[i].global_position=get_parent().global_position

	var blist = get_overlapping_bodies()
	for i in blist.size():
		if blist[i].has_method("drag"):
			blist[i].drag()
			blist[i].global_position=get_parent().global_position


